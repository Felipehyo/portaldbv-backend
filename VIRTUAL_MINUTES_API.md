cre# API de Ata Virtual - Documentação Atualizada

## Visão Geral

A funcionalidade de **Ata Virtual** permite o gerenciamento de atas de **Secretaria** e **Capelania** para as unidades dos clubes de desbravadores. A implementação foi adaptada para atender aos seguintes requisitos:

- **Atas de Secretaria**: Podem ter até 3 fotos salvas no S3
- **Atas de Capelania**: Sem opção de fotos
- **Vinculação**: Cada ata é vinculada a uma **Unidade** (não ao clube)
- **Unicidade**: Só pode haver 1 ata de secretaria e 1 ata de capelania por unidade por dia
- **Auditoria**: Registra usuário criador e data/hora de criação

## Estrutura de Dados

### VirtualMinutes (Domain Entity)

```java
{
    "id": Long,
    "type": MinutesTypeEnum, // SECRETARIA ou CAPELANIA
    "date": LocalDate,
    "description": String,
    "imageLinks": List<String>, // URLs das imagens no S3 (máx 3 para secretaria)
    "unit": Unit,
    "createdBy": User,
    "createdAt": LocalDateTime,
    "updatedAt": LocalDateTime,
    "active": Boolean
}
```

### Tabela do Banco de Dados: VIRTUAL_MINUTES

```sql
CREATE TABLE VIRTUAL_MINUTES (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20) NOT NULL, -- 'SECRETARIA' ou 'CAPELANIA'
    date DATE NOT NULL,
    description TEXT,
    image_links VARCHAR(1500), -- URLs separadas por vírgula
    unit_id BIGINT,
    created_by_user_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    active BOOLEAN,
    FOREIGN KEY (unit_id) REFERENCES UNIT(id),
    FOREIGN KEY (created_by_user_id) REFERENCES USER(id),
    UNIQUE (unit_id, date, type) -- Garante unicidade por unidade/data/tipo
);
```

## Endpoints da API

Base URL: `/pathfinders/v1/virtual-minutes`

### 1. **Cadastrar Ata de Secretaria (com fotos)**
```http
POST /virtual-minutes/secretaria
Content-Type: multipart/form-data
```

**Form Data Parameters:**
- `minutesRequest` (obrigatório): JSON string com os dados da ata
- `unitId` (obrigatório): ID da unidade
- `userId` (obrigatório): ID do usuário criador
- `image1` (opcional): Primeira foto (MultipartFile)
- `image2` (opcional): Segunda foto (MultipartFile)
- `image3` (opcional): Terceira foto (MultipartFile)

**minutesRequest JSON:**
```json
{
    "date": "2026-02-11",
    "description": "Descrição da ata de secretaria com informações sobre presença, uniforme, etc."
}
```

**Response:** `201 Created`
```json
{
    "id": 1,
    "type": "SECRETARIA",
    "date": "2026-02-11",
    "description": "Descrição da ata de secretaria...",
    "imageLinks": [
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

**Error Responses:**
- `400 Bad Request`: "Já existe uma ata deste tipo para esta unidade nesta data"
- `400 Bad Request`: "Máximo de 3 imagens permitidas por ata de secretaria"

### 2. **Cadastrar Ata de Capelania (sem fotos)**
```http
POST /virtual-minutes/capelania?unitId={unitId}&userId={userId}
Content-Type: application/json
```

**Query Parameters:**
- `unitId` (obrigatório): ID da unidade
- `userId` (obrigatório): ID do usuário criador

**Request Body:**
```json
{
    "date": "2026-02-11",
    "description": "Descrição da ata de capelania com tema da meditação, cânticos, etc."
}
```

**Response:** `201 Created`
```json
{
    "id": 2,
    "type": "CAPELANIA",
    "date": "2026-02-11",
    "description": "Descrição da ata de capelania...",
    "imageLinks": null,
    "unitId": 1,
    "unitName": "Amigos da Natureza",
    "createdByUserId": 5,
    "createdByUserName": "João Silva",
    "createdAt": "2026-02-11T11:00:00",
    "updatedAt": "2026-02-11T11:00:00",
    "active": true
}
```

**Error Responses:**
- `400 Bad Request`: "Já existe uma ata deste tipo para esta unidade nesta data"

### 3. **Buscar Atas por Data Específica (Endpoint Único)**
```http
GET /virtual-minutes/by-date?unitId={unitId}&date={date}
```

**Query Parameters:**
- `unitId` (obrigatório): ID da unidade
- `date` (obrigatório): Data no formato ISO (YYYY-MM-DD)

**Response:** `200 OK`
```json
[
    {
        "id": 1,
        "type": "SECRETARIA",
        "date": "2026-02-11",
        "description": "Descrição da ata de secretaria...",
        "imageLinks": [
            "https://bucket.s3.region.amazonaws.com/virtual-minutes/uuid1.png",
            "https://bucket.s3.region.amazonaws.com/virtual-minutes/uuid2.png"
        ],
        "unitId": 1,
        "unitName": "Amigos da Natureza",
        "createdByUserId": 5,
        "createdByUserName": "João Silva",
        "createdAt": "2026-02-11T10:30:00",
        "updatedAt": "2026-02-11T10:30:00",
        "active": true
    },
    {
        "id": 2,
        "type": "CAPELANIA",
        "date": "2026-02-11",
        "description": "Descrição da ata de capelania...",
        "imageLinks": null,
        "unitId": 1,
        "unitName": "Amigos da Natureza",
        "createdByUserId": 5,
        "createdByUserName": "João Silva",
        "createdAt": "2026-02-11T11:00:00",
        "updatedAt": "2026-02-11T11:00:00",
        "active": true
    }
]
```

### 4. **Listar Todas as Atas de uma Unidade**
```http
GET /virtual-minutes?unitId={unitId}&onlyActives={true|false}
```

**Query Parameters:**
- `unitId` (obrigatório): ID da unidade
- `onlyActives` (opcional): Filtrar apenas atas ativas (default: false)

**Response:** `200 OK` - Array de VirtualMinutesResponseDTO

### 5. **Buscar Atas por Período**
```http
GET /virtual-minutes/by-period?unitId={unitId}&initialDate={date}&finalDate={date}
```

**Query Parameters:**
- `unitId` (obrigatório): ID da unidade
- `initialDate` (obrigatório): Data inicial (YYYY-MM-DD)
- `finalDate` (obrigatório): Data final (YYYY-MM-DD)

**Response:** `200 OK` - Array de VirtualMinutesResponseDTO

### 6. **Buscar Ata por ID**
```http
GET /virtual-minutes/{id}
```

**Path Parameters:**
- `id`: ID da ata

**Response:** `200 OK` - VirtualMinutesResponseDTO

**Error Response:** `404 Not Found`

### 7. **Deletar Ata**
```http
DELETE /virtual-minutes/{id}
```

**Path Parameters:**
- `id`: ID da ata

**Observação**: Remove também todas as imagens do S3 associadas à ata.

**Response:** `204 No Content`

**Error Response:** `404 Not Found`

### 8. **Ativar/Inativar Ata**
```http
PATCH /virtual-minutes/{id}/status?active={true|false}
```

**Path Parameters:**
- `id`: ID da ata

**Query Parameters:**
- `active` (obrigatório): true para ativar, false para inativar

**Response:** `204 No Content`

**Error Response:** `404 Not Found`

## Exemplos de Uso com cURL

### Exemplo 1: Criar ata de secretaria com 3 fotos

```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -H "Content-Type: multipart/form-data" \
  -F 'minutesRequest={"date":"2026-02-11","description":"Presença: 15 desbravadores. Uniformes completos: 12."}' \
  -F "image1=@/path/to/foto1.jpg" \
  -F "image2=@/path/to/foto2.jpg" \
  -F "image3=@/path/to/foto3.jpg"
```

### Exemplo 2: Criar ata de capelania sem fotos

```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/capelania?unitId=1&userId=5" \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2026-02-11",
    "description": "Tema: A fé que move montanhas. Cântico: Hino 123. Participação: 14 desbravadores."
  }'
```

### Exemplo 3: Buscar atas de um dia específico (ambas secretaria e capelania)

```bash
curl -X GET "http://localhost:8080/pathfinders/v1/virtual-minutes/by-date?unitId=1&date=2026-02-11"
```

### Exemplo 4: Criar ata de secretaria sem nenhuma foto

```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -H "Content-Type: multipart/form-data" \
  -F 'minutesRequest={"date":"2026-02-11","description":"Reunião com poucos membros presentes."}'
```

**Observação**: Quando não há fotos, simplesmente não envie o parâmetro `files`.

### Exemplo 5: Buscar todas as atas de uma unidade

```bash
curl -X GET "http://localhost:8080/pathfinders/v1/virtual-minutes?unitId=1&onlyActives=true"
```

## Regras de Negócio

### Validações de Unicidade
- ✅ Só pode existir **1 ata de SECRETARIA** por unidade por dia
- ✅ Só pode existir **1 ata de CAPELANIA** por unidade por dia
- ✅ É possível ter ambas (secretaria E capelania) para a mesma unidade no mesmo dia

### Validações de Imagens
- ✅ **Secretaria**: Pode ter de 0 a 3 fotos
- ✅ **Capelania**: Não aceita fotos
- ✅ Imagens são salvas no S3 em: `s3://bucket/virtual-minutes/`
- ✅ Ao deletar uma ata, as imagens são removidas do S3

### Validações de Request (DTO)
- **date**: Campo obrigatório, não pode ser nulo
- **description**: Campo obrigatório, não pode ser nulo

### Validações de Negócio (Use Cases)
- Unidade deve existir e estar ativa
- Usuário criador deve existir e estar ativo
- Não pode haver duplicidade de tipo de ata por unidade/dia

## Códigos de Erro

| Código | Mensagem | Descrição |
|--------|----------|-----------|
| 400 | Já existe uma ata deste tipo para esta unidade nesta data | Tentativa de criar ata duplicada |
| 400 | Máximo de 3 imagens permitidas por ata de secretaria | Mais de 3 imagens enviadas |
| 400 | Unidade informada é inválida | Unidade não existe |
| 400 | Usuário informado é inválido | Usuário criador inválido |
| 404 | Ata virtual não encontrada | Nenhuma ata encontrada |
| 404 | Ata virtual com id informado não encontrada | ID específico não existe |

## Swagger/OpenAPI

A documentação interativa está disponível em:
```
http://localhost:8080/pathfinders/v1/swagger-ui
```

Tag: **Ata Virtual**

## Considerações Técnicas

### Armazenamento de Imagens
- As imagens são armazenadas no **Amazon S3**
- Path no S3: `virtual-minutes/{uuid}.png`
- URLs são retornados no formato: `https://bucket.s3.region.amazonaws.com/virtual-minutes/uuid.png`
- Máximo de 3 imagens por ata de secretaria
- Imagens são deletadas do S3 quando a ata é excluída

### MapStruct
O mapper utiliza o MapStruct com mapeamentos customizados:
- `unit.id` → `unitId`
- `unit.name` → `unitName`
- `createdBy.id` → `createdByUserId`
- `createdBy.name` → `createdByUserName`
- `imageLinks` são tratados separadamente (conversão de String para List e vice-versa)

### Auditoria Automática
A entidade possui hooks JPA para auditoria:
- `@PrePersist`: Define `createdAt`, `updatedAt` e `active` na criação
- `@PreUpdate`: Atualiza `updatedAt` em cada modificação

### Ordenação
Por padrão, as consultas retornam as atas ordenadas por:
1. `date DESC` (data mais recente primeiro)
2. `type` (Capelania antes de Secretaria)

## Fluxo de Trabalho Típico

### Dia de Reunião da Unidade

1. **Manhã**: Durante a reunião, tire fotos dos desbravadores
2. **Durante/Após**: Secretário registra a ata de secretaria com as 3 melhores fotos
   ```bash
   POST /virtual-minutes/secretaria
   ```
3. **Durante/Após**: Capelão registra a ata de capelania (sem fotos)
   ```bash
   POST /virtual-minutes/capelania
   ```
4. **Consulta**: Visualizar ambas as atas do dia
   ```bash
   GET /virtual-minutes/by-date?unitId=1&date=2026-02-11
   ```

## Próximas Melhorias Sugeridas

1. **Compressão de Imagens**: Comprimir automaticamente antes de enviar ao S3
2. **Thumbnails**: Gerar versões reduzidas para listagens
3. **Comentários**: Permitir adicionar comentários às atas
4. **Assinaturas**: Implementar assinatura digital do secretário/capelão
5. **Relatórios**: Gerar relatórios consolidados mensais/anuais
6. **Notificações**: Notificar diretoria quando atas são criadas
7. **Templates**: Criar templates pré-formatados de atas

---

**Versão**: 2.0.0  
**Data de Atualização**: Fevereiro 2026  
**Última Modificação**: Adaptação para Secretaria/Capelania com fotos no S3


## Arquitetura

A implementação segue a Clean Architecture com as seguintes camadas:

### 1. **Domain Layer** (`br.com.portaldbv.domain`)
- **Entity**: `VirtualMinutes.java` - Entidade de domínio
- **Error Enum**: `VirtualMinutesErrorEnum.java` - Enumeração de erros específicos

### 2. **Application Layer** (`br.com.portaldbv.application`)
- **Gateway Interface**: `VirtualMinutesRepositoryGateway.java`
- **Use Cases**: `VirtualMinutesUseCases.java` - Regras de negócio

### 3. **Infrastructure Layer** (`br.com.portaldbv.infra`)
- **Persistence Entity**: `VirtualMinutesEntity.java`
- **Repository**: `VirtualMinutesRepository.java`
- **Gateway Implementation**: `VirtualMinutesRepositoryGatewayImpl.java`
- **Mapper**: `VirtualMinutesMapper.java`
- **DTOs**: 
  - `VirtualMinutesRequestDTO.java`
  - `VirtualMinutesResponseDTO.java`
- **Resource**: `VirtualMinutesResource.java` - Interface da API REST
- **Controller**: `VirtualMinutesController.java` - Implementação do Resource

### 4. **Configuration** (`br.com.portaldbv.config`)
- **Config**: `VirtualMinutesConfiguration.java` - Configuração de beans Spring

## Estrutura de Dados

### VirtualMinutes (Domain Entity)

```java
{
    "id": Long,
    "title": String,
    "content": String,
    "meetingDate": LocalDateTime,
    "participants": String,
    "location": String,
    "decisions": String,
    "observations": String,
    "club": Club,
    "createdBy": User,
    "createdAt": LocalDateTime,
    "updatedAt": LocalDateTime,
    "active": Boolean
}
```

### Tabela do Banco de Dados: VIRTUAL_MINUTES

```sql
CREATE TABLE VIRTUAL_MINUTES (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    meeting_date TIMESTAMP NOT NULL,
    participants TEXT,
    location VARCHAR(255),
    decisions TEXT,
    observations TEXT,
    club_id BIGINT,
    created_by_user_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    active BOOLEAN,
    FOREIGN KEY (club_id) REFERENCES CLUB(id),
    FOREIGN KEY (created_by_user_id) REFERENCES USER(id)
);
```

## Endpoints da API

Base URL: `/pathfinders/v1/virtual-minutes`

### 1. **Listar Atas por Clube**
```http
GET /virtual-minutes?clubId={clubId}&onlyActives={true|false}
```

**Query Parameters:**
- `clubId` (obrigatório): ID do clube
- `onlyActives` (opcional): Filtrar apenas atas ativas (default: false)

**Response:** `200 OK`
```json
[
    {
        "id": 1,
        "title": "Reunião de Planejamento 2024",
        "content": "Conteúdo da ata...",
        "meetingDate": "2024-01-15T14:00:00",
        "participants": "João, Maria, Pedro",
        "location": "Sede do Clube",
        "decisions": "Decisões tomadas...",
        "observations": "Observações gerais...",
        "clubId": 1,
        "clubName": "Clube Aventureiros",
        "createdByUserId": 5,
        "createdByUserName": "João Silva",
        "createdAt": "2024-01-15T15:30:00",
        "updatedAt": "2024-01-15T15:30:00",
        "active": true
    }
]
```

### 2. **Listar Atas por Período**
```http
GET /virtual-minutes/by-date?clubId={clubId}&initialDate={date}&finalDate={date}
```

**Query Parameters:**
- `clubId` (obrigatório): ID do clube
- `initialDate` (obrigatório): Data inicial (formato ISO 8601: `2024-01-01T00:00:00`)
- `finalDate` (obrigatório): Data final (formato ISO 8601: `2024-12-31T23:59:59`)

**Response:** `200 OK` - Array de VirtualMinutesResponseDTO

### 3. **Listar Atas Criadas por Usuário**
```http
GET /virtual-minutes/by-user?userId={userId}
```

**Query Parameters:**
- `userId` (obrigatório): ID do usuário criador

**Response:** `200 OK` - Array de VirtualMinutesResponseDTO

### 4. **Buscar Ata por ID**
```http
GET /virtual-minutes/{id}
```

**Path Parameters:**
- `id`: ID da ata

**Response:** `200 OK` - VirtualMinutesResponseDTO

**Error Response:** `404 Not Found`
```json
{
    "httpStatus": 404,
    "message": "Ata virtual com id informado não encontrada"
}
```

### 5. **Criar Nova Ata**
```http
POST /virtual-minutes?clubId={clubId}&userId={userId}
Content-Type: application/json
```

**Query Parameters:**
- `clubId` (obrigatório): ID do clube
- `userId` (obrigatório): ID do usuário criador

**Request Body:**
```json
{
    "title": "Reunião de Planejamento 2024",
    "content": "Detalhes completos da reunião...",
    "meetingDate": "2024-01-15T14:00:00",
    "participants": "João Silva, Maria Santos, Pedro Costa",
    "location": "Sede do Clube - Sala de Reuniões",
    "decisions": "1. Aprovar calendário anual\n2. Definir eventos principais",
    "observations": "Próxima reunião em 15 dias",
    "active": true
}
```

**Campos Obrigatórios:**
- `title`: Título da ata
- `meetingDate`: Data e hora da reunião

**Campos Opcionais:**
- `content`: Conteúdo detalhado da ata
- `participants`: Lista de participantes
- `location`: Local da reunião
- `decisions`: Decisões tomadas
- `observations`: Observações gerais
- `active`: Status (default: true)

**Response:** `201 Created` - VirtualMinutesResponseDTO

### 6. **Atualizar Ata**
```http
PATCH /virtual-minutes/{id}
Content-Type: application/json
```

**Path Parameters:**
- `id`: ID da ata

**Request Body:** (mesma estrutura do POST, todos os campos são opcionais)
```json
{
    "title": "Reunião de Planejamento 2024 - Atualizado",
    "content": "Conteúdo atualizado...",
    "meetingDate": "2024-01-15T14:00:00",
    "participants": "João Silva, Maria Santos, Pedro Costa, Ana Lima",
    "location": "Sede do Clube - Sala Principal",
    "decisions": "Decisões atualizadas...",
    "observations": "Observações atualizadas...",
    "active": true
}
```

**Response:** `200 OK` - VirtualMinutesResponseDTO

**Error Response:** `404 Not Found`

### 7. **Deletar Ata**
```http
DELETE /virtual-minutes/{id}
```

**Path Parameters:**
- `id`: ID da ata

**Response:** `204 No Content`

**Error Response:** `404 Not Found`

### 8. **Ativar/Inativar Ata**
```http
PATCH /virtual-minutes/{id}/status?active={true|false}
```

**Path Parameters:**
- `id`: ID da ata

**Query Parameters:**
- `active` (obrigatório): true para ativar, false para inativar

**Response:** `204 No Content`

**Error Response:** `404 Not Found`

## Exemplos de Uso

### Exemplo 1: Criar uma nova ata de reunião

```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes?clubId=1&userId=5" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Reunião Mensal - Janeiro 2024",
    "content": "Discussão sobre as atividades do mês e planejamento de acampamento.",
    "meetingDate": "2024-01-15T19:00:00",
    "participants": "Diretoria completa e conselheiros",
    "location": "Igreja Central",
    "decisions": "Aprovar data do acampamento para 20/02/2024",
    "observations": "Todos os presentes assinaram a lista de presença"
  }'
```

### Exemplo 2: Buscar todas as atas de um clube (apenas ativas)

```bash
curl -X GET "http://localhost:8080/pathfinders/v1/virtual-minutes?clubId=1&onlyActives=true"
```

### Exemplo 3: Buscar atas por período

```bash
curl -X GET "http://localhost:8080/pathfinders/v1/virtual-minutes/by-date?clubId=1&initialDate=2024-01-01T00:00:00&finalDate=2024-12-31T23:59:59"
```

### Exemplo 4: Atualizar uma ata existente

```bash
curl -X PATCH "http://localhost:8080/pathfinders/v1/virtual-minutes/1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Reunião Mensal - Janeiro 2024 (Revisada)",
    "observations": "Ata revisada e aprovada por todos os membros"
  }'
```

### Exemplo 5: Inativar uma ata

```bash
curl -X PATCH "http://localhost:8080/pathfinders/v1/virtual-minutes/1/status?active=false"
```

## Validações

### Validações de Request (DTO)
- **title**: Campo obrigatório, não pode ser nulo
- **meetingDate**: Campo obrigatório, não pode ser nulo

### Validações de Negócio (Use Cases)
- Clube deve existir e estar ativo
- Usuário criador deve existir e estar ativo
- ID da ata deve existir para operações de atualização/exclusão

## Códigos de Erro

| Código | Mensagem | Descrição |
|--------|----------|-----------|
| 400 | Ata virtual com o título informado já se encontra cadastrada | Tentativa de criar ata duplicada |
| 400 | Ata virtual não pertence ao clube informado | Ata não associada ao clube |
| 400 | Usuário informado é inválido | Usuário criador inválido |
| 404 | Ata virtual não encontrada | Nenhuma ata encontrada |
| 404 | Ata virtual com id informado não encontrada | ID específico não existe |

## Swagger/OpenAPI

A documentação interativa está disponível em:
```
http://localhost:8080/pathfinders/v1/swagger-ui
```

Tag: **Ata Virtual**

## Considerações Técnicas

### MapStruct
O mapper utiliza o MapStruct para conversão automática entre entities, domain objects e DTOs. Os mapeamentos customizados incluem:
- Mapeamento de `club.id` para `clubId`
- Mapeamento de `club.name` para `clubName`
- Mapeamento de `createdBy.id` para `createdByUserId`
- Mapeamento de `createdBy.name` para `createdByUserName`

### Auditoria Automática
A entidade possui hooks JPA para auditoria:
- `@PrePersist`: Define `createdAt`, `updatedAt` e `active` na criação
- `@PreUpdate`: Atualiza `updatedAt` em cada modificação

### Ordenação
Por padrão, as consultas retornam as atas ordenadas por `meetingDate DESC` (data mais recente primeiro).

## Dependências

Esta feature depende de:
- **ClubUseCases**: Para validação de clube
- **UserUseCases**: Para validação de usuário criador
- **Spring Data JPA**: Para persistência
- **MapStruct**: Para mapeamento de objetos
- **Lombok**: Para redução de boilerplate
- **Spring Boot Validation**: Para validação de DTOs

## Próximas Melhorias Sugeridas

1. **Versionamento de Atas**: Manter histórico de alterações
2. **Anexos**: Permitir upload de arquivos anexos à ata
3. **Assinaturas Digitais**: Implementar assinatura eletrônica dos participantes
4. **Notificações**: Enviar notificações quando uma nova ata é criada
5. **Exportação**: Gerar PDF da ata formatada
6. **Busca Full-Text**: Implementar busca textual no conteúdo das atas
7. **Templates**: Criar templates de ata pré-formatados
8. **Aprovação**: Fluxo de aprovação de atas antes de finalizar

## Contato e Suporte

Para dúvidas ou problemas relacionados à API de Ata Virtual, consulte a documentação do Swagger ou entre em contato com a equipe de desenvolvimento.

---

**Versão**: 1.0.0  
**Data de Criação**: Janeiro 2024  
**Última Atualização**: Janeiro 2024

