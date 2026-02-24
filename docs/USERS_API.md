# API de Usuários — Guia para frontend

Este documento descreve como usar a API de usuários do backend, como alterar usuário e senha, e a regra de unicidade do username (usuário deve ser único). Use este arquivo como prompt para implementar as alterações no frontend.

Sumário
- Visão geral
- Endpoints
  - Criar usuário — POST /api/users
  - Atualizar usuário — PUT /api/users/{id}
  - Alterar senha — PATCH /api/users/{id}/password
  - Buscar usuários — GET /api/users
  - Buscar usuário por id — GET /api/users/{id}
- Regras de validação
- Erros e códigos de resposta
- Regras de unicidade do username (servidor)
- Recomendações e fluxo para o frontend
- Exemplos de requisições/respostas


## Visão geral
A API de usuários permite criar, atualizar e alterar a senha de usuários. O campo `username` (login) deve ser único em toda a base. O backend valida isso e retornará um erro claro caso já exista outro usuário com o mesmo username.

As operações mais comuns:
- Criar: cadastrar novo usuário.
- Atualizar: alterar dados do usuário (nome, email, unidade, etc). Se for alterado o `username`, a aplicação valida se o novo valor já está em uso.
- Alterar senha: endpoint específico que exige a senha atual para segurança.
- Buscar: listar usuários e recuperar por id.


## Endpoints
Os endpoints documentados abaixo assumem prefixo `/api`. Ajuste se o seu projeto usa um prefixo diferente.

1) Criar usuário
- Endpoint: POST /api/users
- Conteúdo: application/json
- Payload exemplo:
  {
    "username": "joao.silva",
    "password": "SenhaSegura123",
    "firstName": "João",
    "lastName": "Silva",
    "email": "joao@exemplo.com",
    "unitId": 123   // opcional, id da unidade
  }
- Resposta: 201 Created + body com o usuário criado (sem senha)

2) Atualizar usuário
- Endpoint: PUT /api/users/{id}
- Conteúdo: application/json
- Observação: `id` é o identificador do usuário a ser alterado.
- Regra principal: se o `username` enviado for diferente do atual, o backend verifica se já existe outro usuário com esse username. Se existir, retorna 409 Conflict.
- Payload (exemplo):
  {
    "username": "novo.username",    // opcional (se não quiser alterar, enviar o mesmo ou omitir)
    "firstName": "João Atualizado",
    "lastName": "Silva",
    "email": "joao.novo@exemplo.com",
    "unitId": 456
  }
- Resposta ao sucesso: 200 OK + body com o usuário atualizado

3) Alterar senha
- Endpoint: PATCH /api/users/{id}/password
- Conteúdo: application/json
- Regras: obrigar envio da senha atual (`currentPassword`) para validar autenticidade antes de alterar.
- Payload:
  {
    "currentPassword": "SenhaAntiga123",
    "newPassword": "NovaSenhaSegura456"
  }
- Validações sugeridas (backend):
  - `currentPassword` deve bater com a senha atual armazenada (hash comparado)
  - `newPassword` deve atender política mínima (ex.: >= 8 caracteres)
- Respostas:
  - 200 OK — senha alterada com sucesso
  - 400 Bad Request — nova senha não atende a política
  - 401 Unauthorized — `currentPassword` incorreta

4) Buscar usuários (listar)
- Endpoint: GET /api/users
- Parâmetros de query sugeridos:
  - page, size, sort (padrão pageable)
  - unitId (opcional) — se informado, filtra por unidade; se omitido, retorna todos os usuários incluindo aqueles com unitId = null
  - search (opcional) — texto para pesquisa por nome/email
- Resposta: 200 OK + lista pageable de usuários (sem senha)

5) Buscar usuário por id
- Endpoint: GET /api/users/{id}
- Resposta: 200 OK + usuário


## Regras de validação (sugestão)
- username:
  - Obrigatório
  - Tamanho entre 3 e 50 caracteres
  - Caracteres permitidos: letras, dígitos, ponto, underline e hífen (ex.: /^[A-Za-z0-9._-]{3,50}$/)
- password (na criação e troca):
  - Obrigatório na criação
  - Nova senha: mínimo 8 caracteres, recomendado incluir letras maiúsculas, minúsculas e números
- email: formato válido (se presente)
- firstName / lastName: não obrigatório, mas recomendado

Obs: Ajuste essas regras conforme as políticas do projeto. O importante é que o backend garanta a unicidade do `username` e valide o `currentPassword` ao alterar a senha.


## Erros e códigos de resposta importantes
- 400 Bad Request — payload inválido ou validação falhou
- 401 Unauthorized — ação requer autenticação ou senha atual incorreta
- 403 Forbidden — usuário não tem permissão
- 404 Not Found — id informado não existe
- 409 Conflict — tentativa de criar/atualizar `username` que já existe

Formato de erro sugerido (JSON):
{
  "timestamp": "2026-02-20T12:00:00Z",
  "status": 409,
  "error": "Conflict",
  "message": "USERNAME_ALREADY_EXISTS",
  "path": "/api/users"
}


## Regras de unicidade do username (back-end)
1) Validação na camada de serviço antes de salvar/atualizar:
   - Ao criar: verificar se já existe usuário com `username`. Se sim, retornar 409 com `USERNAME_ALREADY_EXISTS`.
   - Ao atualizar: se o `username` enviado for diferente do atual, verificar existência em outros registros. Se existir, retornar 409.
2) Garantia na base de dados: ter índice/constraint UNIQUE sobre a coluna `username` para evitar condições de corrida.
3) Tratamento de exceção: caso o DB lance erro de constraint (ex.: DuplicateKeyException), capturar e traduzir para 409 + mensagem `USERNAME_ALREADY_EXISTS`.

Observação sobre condição de corrida: Mesmo com verificação na aplicação, podem ocorrer race-conditions; por isso a constraint no DB é obrigatória.


## Fluxo recomendado para o frontend
1) Validação cliente-side:
   - Validar formato do username e tamanho mínimo/ máximo antes de enviar.
   - Validar nova senha e confirma senha.
2) Ao submeter alteração (PUT /api/users/{id}):
   - Mostrar loading/disable no botão.
   - Se o servidor responder 409, exibir mensagem clara: "Nome de usuário já em uso. Escolha outro." e destacar o campo username.
   - Em caso de sucesso, atualizar a view com os dados retornados.
3) Ao alterar senha (PATCH /api/users/{id}/password):
   - Exigir `currentPassword`, `newPassword`, `confirmNewPassword` no formulário.
   - Não logar a senha em logs do front-end.
   - Exibir erros específicos: 401 para senha atual inválida; 400 para política não atendida.
4) Boas práticas UX:
   - Se quiser melhorar a experiência, fazer validação de disponibilidade de username com debounce (ex.: chamada GET /api/users/exists?username=...) — mas ainda assim confiar apenas na resposta final do PUT/POST, pois a verificação é apenas auxiliar.


## Nomes de erro e mensagens (sugeridas)
- USERNAME_ALREADY_EXISTS — usado quando username já existe
- INVALID_CURRENT_PASSWORD — usado quando currentPassword está incorreta
- INVALID_DATA — usado para erros de validação

O front-end pode mapear esses códigos para mensagens amigáveis ao usuário.


## Exemplo de requisições e respostas
1) Criar usuário (sucesso)
Request:
POST /api/users
Content-Type: application/json

{
  "username": "maria.souza",
  "password": "MinhaSenha@123",
  "firstName": "Maria",
  "lastName": "Souza",
  "email": "maria@exemplo.com"
}

Response: 201 Created
{
  "id": 42,
  "username": "maria.souza",
  "firstName": "Maria",
  "lastName": "Souza",
  "email": "maria@exemplo.com",
  "unitId": null
}


2) Atualizar usuário — username já existe (erro)
Request:
PUT /api/users/42
Content-Type: application/json

{
  "username": "joao.silva",  // já usado por outro usuário
  "firstName": "Maria Atualizada"
}

Response: 409 Conflict
{
  "timestamp": "2026-02-20T12:05:00Z",
  "status": 409,
  "error": "Conflict",
  "message": "USERNAME_ALREADY_EXISTS",
  "path": "/api/users/42"
}


3) Alterar senha (sucesso)
Request:
PATCH /api/users/42/password
Content-Type: application/json

{
  "currentPassword": "SenhaAntiga123",
  "newPassword": "NovaSenha@456"
}

Response: 200 OK
{
  "message": "PASSWORD_UPDATED"
}


## Observações técnicas (backend)
- Persistência: o `username` deve ter constraint UNIQUE na tabela `users`.
- Hash de senha: usar bcrypt (ou padrão do projeto) para armazenar a senha.
- Ao atualizar o username, aplicar validação atômica:
  - Preferível: dentro de transação, verificar existência e salvar; garantir captura da exceção de duplicidade do DB.
- Logs: registrar eventos de atualização (sem logar senhas).


## Como usar este MD como prompt para o frontend
- Use os exemplos de payload e mensagens de erro para construir formulários e mapear mensagens de erro.
- Garanta que, ao editar usuário, se o servidor responder 409, o campo `username` mostre o erro e permita ao usuário corrigir.
- Para alteração de senha, implemente o fluxo com `currentPassword` + `newPassword` + `confirmNewPassword`.


---
Se desejar, eu também posso:
- Gerar exemplos de chamadas em JS/Fetch ou Axios para cada endpoint.
- Criar componentes React/Vue com validações conforme descrito.
- Gerar alterações backend (controller/service/repository) para garantir a checagem e tratamento da duplicidade se você quiser que eu altere o código do repositório.

Fim do documento.

