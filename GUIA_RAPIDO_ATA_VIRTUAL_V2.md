# Guia Rápido - API Ata Virtual (Atualizado)

## 🚀 Início Rápido

### Compilar e Executar
```bash
cd "C:\My Projects\Portal Pathfinder\portaldbv\portaldbv-backend"
mvn clean compile
mvn spring-boot:run
```

### Swagger UI
```
http://localhost:8080/pathfinders/v1/swagger-ui
```
Procure pela tag: **"Ata Virtual"**

## 📋 Conceitos Importantes

### Tipos de Ata
- **SECRETARIA**: Registra presença, uniformes, etc. Pode ter até 3 fotos.
- **CAPELANIA**: Registra meditação, cânticos, etc. Não aceita fotos.

### Regras
- ✅ Só pode haver **1 ata de secretaria** por unidade por dia
- ✅ Só pode haver **1 ata de capelania** por unidade por dia
- ✅ Pode ter ambas no mesmo dia para a mesma unidade
- ✅ Atas são vinculadas à **Unidade** (não ao clube)
- ✅ Imagens são salvas no **S3**

## 📝 Exemplos Práticos

### 1. Criar Ata de Secretaria COM 3 Fotos

**Usando cURL:**
```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -H "Content-Type: multipart/form-data" \
  -F 'minutesRequest={"date":"2026-02-11","description":"Presença: 15 desbravadores. Uniformes: 12 completos."}' \
  -F "image1=@C:/fotos/reuniao1.jpg" \
  -F "image2=@C:/fotos/reuniao2.jpg" \
  -F "image3=@C:/fotos/reuniao3.jpg"
```

**Usando Postman:**
1. Método: `POST`
2. URL: `http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5`
3. Body → form-data:
   - Key: `minutesRequest`, Value: `{"date":"2026-02-11","description":"..."}`
   - Key: `image1`, Type: File, Value: selecione a imagem
   - Key: `image2`, Type: File, Value: selecione a imagem
   - Key: `image3`, Type: File, Value: selecione a imagem

### 2. Criar Ata de Secretaria SEM Fotos

```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -H "Content-Type: multipart/form-data" \
  -F 'minutesRequest={"date":"2026-02-11","description":"Reunião administrativa sem fotos."}'
```

### 3. Criar Ata de Capelania (Sem Fotos)

```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/capelania?unitId=1&userId=5" \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2026-02-11",
    "description": "Tema: Fé que move montanhas. Cântico: Hino 123. Participantes: 14."
  }'
```

### 4. Buscar Atas de um Dia Específico (Único Endpoint)

```bash
curl -X GET "http://localhost:8080/pathfinders/v1/virtual-minutes/by-date?unitId=1&date=2026-02-11"
```

**Resposta:**
```json
[
  {
    "id": 1,
    "type": "SECRETARIA",
    "date": "2026-02-11",
    "description": "Presença: 15 desbravadores...",
    "imageLinks": [
      "https://bucket.s3.amazonaws.com/virtual-minutes/uuid1.png",
      "https://bucket.s3.amazonaws.com/virtual-minutes/uuid2.png"
    ],
    "unitId": 1,
    "unitName": "Amigos da Natureza",
    "createdByUserId": 5,
    "createdByUserName": "João Silva",
    "createdAt": "2026-02-11T10:30:00",
    "active": true
  },
  {
    "id": 2,
    "type": "CAPELANIA",
    "date": "2026-02-11",
    "description": "Tema: Fé que move montanhas...",
    "imageLinks": null,
    "unitId": 1,
    "unitName": "Amigos da Natureza",
    "createdByUserId": 5,
    "createdByUserName": "João Silva",
    "createdAt": "2026-02-11T11:00:00",
    "active": true
  }
]
```

### 5. Listar Todas as Atas de uma Unidade

```bash
curl -X GET "http://localhost:8080/pathfinders/v1/virtual-minutes?unitId=1&onlyActives=true"
```

### 6. Buscar Atas por Período

```bash
curl -X GET "http://localhost:8080/pathfinders/v1/virtual-minutes/by-period?unitId=1&initialDate=2026-01-01&finalDate=2026-12-31"
```

### 7. Buscar Ata por ID

```bash
curl -X GET "http://localhost:8080/pathfinders/v1/virtual-minutes/1"
```

### 8. Inativar Ata

```bash
curl -X PATCH "http://localhost:8080/pathfinders/v1/virtual-minutes/1/status?active=false"
```

### 9. Deletar Ata (Remove Imagens do S3)

```bash
curl -X DELETE "http://localhost:8080/pathfinders/v1/virtual-minutes/1"
```

## 🗂️ Estrutura da Resposta

```json
{
  "id": 1,
  "type": "SECRETARIA",  // ou "CAPELANIA"
  "date": "2026-02-11",
  "description": "Descrição da ata...",
  "imageLinks": [  // null para capelania
    "https://bucket.s3.region.amazonaws.com/virtual-minutes/uuid1.png",
    "https://bucket.s3.region.amazonaws.com/virtual-minutes/uuid2.png",
    "https://bucket.s3.region.amazonaws.com/virtual-minutes/uuid3.png"
  ],
  "unitId": 1,
  "unitName": "Amigos da Natureza",
  "createdByUserId": 5,
  "createdByUserName": "João Silva",
  "createdAt": "2026-02-11T10:30:00",
  "updatedAt": "2026-02-11T10:30:00",
  "active": true
}
```

## ✅ Campos Obrigatórios

### Para Cadastrar Ata de Secretaria:
- **date**: Data da ata (formato: YYYY-MM-DD)
- **description**: Descrição da ata
- **unitId**: ID da unidade (query parameter)
- **userId**: ID do usuário criador (query parameter)
- **files**: Array de MultipartFile (opcional, máximo 3)
  - No cURL: Repetir `-F "files=@caminho"` para cada arquivo
  - No Postman: Adicionar múltiplos campos com o mesmo nome `files`

### Para Cadastrar Ata de Capelania:
- **date**: Data da ata (formato: YYYY-MM-DD)
- **description**: Descrição da ata
- **unitId**: ID da unidade (query parameter)
- **userId**: ID do usuário criador (query parameter)

## 🎯 Status HTTP

- **200 OK**: Sucesso na busca
- **201 Created**: Ata criada com sucesso
- **204 No Content**: Ata deletada/status alterado
- **400 Bad Request**: Dados inválidos ou ata duplicada
- **404 Not Found**: Ata não encontrada

## ⚠️ Erros Comuns

| Erro | Causa | Solução |
|------|-------|---------|
| Já existe uma ata deste tipo... | Tentando criar 2ª ata do mesmo tipo no mesmo dia | Verificar se já existe ata |
| Máximo de 3 imagens... | Enviando mais de 3 fotos | Enviar no máximo 3 imagens |
| Unidade informada é inválida | unitId não existe | Verificar ID da unidade |

## 📖 Documentação Completa

Para mais detalhes, consulte:
- `VIRTUAL_MINUTES_API.md` - Documentação completa da API
- `RESUMO_IMPLEMENTACAO_ATA_VIRTUAL.md` - Resumo técnico da implementação

## 🔄 Fluxo Típico de Uso

**Dia de Reunião:**

1. Secretário tira 3 fotos durante a reunião
2. Após a reunião:
   - Secretário cria ata de secretaria com as fotos
   - Capelão cria ata de capelania (sem fotos)
3. Diretoria consulta ambas as atas do dia usando endpoint único

---

**Base URL**: `/pathfinders/v1/virtual-minutes`  
**Versão**: 2.0.0  
**Última Atualização**: Fevereiro 2026

