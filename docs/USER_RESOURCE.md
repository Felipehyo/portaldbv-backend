# Recursos da API — `UserResource` (implementação atual)

Este documento descreve, fielmente ao que está implementado na interface `br.com.portaldbv.infra.resource.UserResource`, os endpoints, assinaturas, parâmetros e contratos atualmente declarados.

Base: `/user`

> Observação: os tipos referenciados (por exemplo `UserRequestDTO`, `LoginRequestDTO`, `AmountRequestDTO`, `PasswordChangeRequestDTO`, `LoginResponseDTO` e `UserTypeEnum`) são definidos no código do projeto. Aqui documentamos as rotas e contratos conforme a interface atual.

---

## 1) Buscar todos por clube
- Método: GET
- Rota: `/user`
- Produces: `application/json`
- Assinatura Java: `ResponseEntity<Object> getAllByClubId(Long clubId, Long unitId, Boolean onlyActives, Boolean onlyUsersWithCashValue, List<UserTypeEnum> userTypeList)`
- Parâmetros de query:
  - `clubId` (Long) — obrigatório
  - `unitId` (Long) — opcional
  - `onlyActives` (Boolean) — opcional
  - `onlyUsersWithCashValue` (Boolean) — opcional
  - `type` (List<UserTypeEnum>) — opcional (foi nomeado `userTypeList` no método)
- Descrição: consulta usuários filtrados por clube e outros filtros opcionais.
- Response: `200 OK` com o corpo contendo a lista (tipo genérico `Object` na assinatura da interface).

---

## 2) Buscar por id
- Método: GET
- Rota: `/user/{id}`
- Produces: `application/json`
- Assinatura Java: `ResponseEntity<Object> getById(UUID id)`
- Parâmetros:
  - `id` (UUID) — PathVariable
- Descrição: busca usuário por identificador.
- Responses documentados na interface:
  - `200 OK` — usuário encontrado
  - `404` — conforme `Errors.INVALID_CREDENTIALS` (anotação presente na interface)

---

## 3) Fazer login
- Método: POST
- Rota: `/user/login`
- Produces: `application/json`
- Assinatura Java: `ResponseEntity<LoginResponseDTO> doLogin(LoginRequestDTO userRequest)`
- Body: `LoginRequestDTO` (espera `email` e `password` conforme DTO do projeto)
- Descrição: autentica o usuário e retorna `LoginResponseDTO` (implementação atual do controller retorna id, tipo, clubId e unitId).
- Responses documentados na interface:
  - `200 OK` — login bem-sucedido
  - `400` — conforme `Errors.USER_ID_NOT_FOUND` (anotação presente na interface)

---

## 4) Cadastrar usuário
- Método: POST
- Rota: `/user` (mesma base)
- Consumes: `application/json`
- Produces: `application/json`
- Assinatura Java: `ResponseEntity<Object> register(Long clubId, UserRequestDTO userRequest) throws JsonProcessingException`
- Parâmetros:
  - `clubId` (Long) — enviado como `@RequestParam` obrigatório
  - Body: `UserRequestDTO` — dados do usuário
- Descrição: cadastra um usuário para o clube informado.
- Responses documentados:
  - `201 Created` — usuário cadastrado com sucesso
  - `400` — conforme `Errors.USER_ALREADY_REGISTERED`

---

## 5) Alterar usuário
- Método: PATCH
- Rota: `/user/{id}`
- Consumes: `application/json`
- Produces: `application/json`
- Assinatura Java: `ResponseEntity<Object> update(UUID id, UserRequestDTO userRequest) throws JsonProcessingException`
- Parâmetros:
  - `id` (UUID) — PathVariable
  - Body: `UserRequestDTO` — campos a atualizar
- Descrição: altera dados do usuário identificado por `id`.
- Responses documentados:
  - `200 OK` — usuário alterado com sucesso
  - `404` — conforme `Errors.UNIT_ID_NOT_FOUND` (usado na anotação da interface)

---

## 6) Depositar valor em caixa
- Método: PATCH
- Rota: `/user/{id}/deposit`
- Consumes: `application/json`
- Produces: `application/json`
- Assinatura Java: `ResponseEntity<Object> depositAmount(UUID id, AmountRequestDTO amountRequest)`
- Parâmetros:
  - `id` (UUID) — PathVariable
  - Body: `AmountRequestDTO` — deve conter `amount` (BigDecimal) conforme DTO do projeto
- Descrição: efetua depósito no saldo/caixa do usuário.
- Responses documentados:
  - `200 OK` — valor depositado
  - `404` — conforme `Errors.USER_ID_NOT_FOUND`

---

## 7) Sacar valor em caixa
- Método: PATCH
- Rota: `/user/{id}/withdraw`
- Consumes: `application/json`
- Produces: `application/json`
- Assinatura Java: `ResponseEntity<Object> withdrawAmount(UUID id, AmountRequestDTO amountRequest)`
- Parâmetros:
  - `id` (UUID) — PathVariable
  - Body: `AmountRequestDTO`
- Descrição: realiza saque do saldo/caixa do usuário.
- Responses documentados:
  - `200 OK` — valor sacado
  - `404` — conforme `Errors.USER_ID_NOT_FOUND`

---

## 8) Deletar usuário
- Método: DELETE
- Rota: `/user/{id}`
- Assinatura Java: `ResponseEntity<Object> delete(UUID id)`
- Parâmetros:
  - `id` (UUID) — PathVariable
- Descrição: remove usuário.
- Responses documentados:
  - `204 No Content` — usuário deletado com sucesso
  - `404` — conforme `Errors.USER_ID_NOT_FOUND`

---

## 9) Alterar senha do usuário (exige senha atual)
- Método: PATCH
- Rota: `/user/{id}/password`
- Consumes: `application/json`
- Produces: `application/json`
- Assinatura Java: `ResponseEntity<Object> changePassword(UUID id, PasswordChangeRequestDTO request)`
- Parâmetros:
  - `id` (UUID) — PathVariable
  - Body: `PasswordChangeRequestDTO` — campos `currentPassword` e `newPassword`
- Descrição: altera a senha do usuário somente se a senha atual for válida.
- Responses documentados:
  - `200 OK` — senha alterada com sucesso
  - `401` — senha atual inválida (conforme anotação da interface)

---

Observações finais
- Este MD reflete exatamente a interface atual de `UserResource` (anotações, rotas e assinaturas). Caso deseje, posso também:
  - Gerar exemplos de payload JSON para cada DTO usado.
  - Gerar uma versão mais orientada ao frontend com exemplos de validação e mensagens de erro esperadas.
  - Incluir mapeamento de códigos HTTP para cada `UserErrorEnum` utilizado no projeto.

Arquivo criado: `docs/USER_RESOURCE.md`

