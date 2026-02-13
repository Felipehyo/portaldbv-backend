# ✅ ALTERAÇÃO FINAL - Array de MultipartFile

## 🎯 Mudança Implementada

A aplicação foi ajustada para receber as fotos como **array de MultipartFile** (até 3 itens), seguindo o mesmo padrão usado no cadastro de especialidades.

---

## 📝 O que foi Alterado

### Antes (3 parâmetros separados):
```java
@PostMapping("/secretaria")
ResponseEntity<Object> registerSecretaria(
    @RequestParam("image1") MultipartFile image1,
    @RequestParam("image2") MultipartFile image2,
    @RequestParam("image3") MultipartFile image3,
    ...
)
```

### Agora (Array de MultipartFile):
```java
@PostMapping("/secretaria")
ResponseEntity<Object> registerSecretaria(
    @RequestParam(value = "files", required = false) MultipartFile[] files,
    @RequestParam("minutesRequest") String minutesRequestJson,
    @RequestParam("unitId") Long unitId,
    @RequestParam("userId") Long userId
)
```

---

## 🔄 Como Funciona

### 1. Controller
```java
// Converte array para lista, filtrando arquivos nulos/vazios
List<MultipartFile> images = new ArrayList<>();
if (files != null) {
    for (MultipartFile file : files) {
        if (file != null && !file.isEmpty()) {
            images.add(file);
        }
    }
}
```

### 2. Use Case
```java
// Valida máximo de 3 imagens
if (images.size() > 3) {
    throw new DomainException(VirtualMinutesErrorEnum.MAX_IMAGES_EXCEEDED);
}

// Upload no S3 para cada imagem
for (MultipartFile image : images) {
    String imageUrl = awsS3UseCases.saveFile(image, AwsConstants.S3_PATH_VIRTUAL_MINUTES, s3BucketName);
    imageLinks.add(imageUrl);
}
```

### 3. Armazenamento no Banco
```java
// Converte lista de URLs para string separada por vírgula
entity.setImageLinks(String.join(",", domain.getImageLinks()));

// Ao recuperar, converte de volta para lista
domain.setImageLinks(Arrays.asList(entity.getImageLinks().split(",")));
```

---

## 🚀 Como Usar

### cURL - Enviar 3 Fotos
```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -H "Content-Type: multipart/form-data" \
  -F 'minutesRequest={"date":"2026-02-11","description":"Presença: 15."}' \
  -F "files=@foto1.jpg" \
  -F "files=@foto2.jpg" \
  -F "files=@foto3.jpg"
```

**Importante**: O parâmetro `files` é repetido para cada arquivo, formando o array automaticamente.

### cURL - Enviar 1 Foto
```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -H "Content-Type: multipart/form-data" \
  -F 'minutesRequest={"date":"2026-02-11","description":"Presença: 15."}' \
  -F "files=@foto1.jpg"
```

### cURL - Sem Fotos
```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -H "Content-Type: multipart/form-data" \
  -F 'minutesRequest={"date":"2026-02-11","description":"Reunião sem fotos."}'
```

### Postman
1. **Method**: POST
2. **URL**: `http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5`
3. **Body** → form-data:
   - Key: `minutesRequest`, Value: `{"date":"2026-02-11","description":"..."}`
   - Key: `files`, Type: **File**, Value: Selecione primeira imagem
   - Key: `files`, Type: **File**, Value: Selecione segunda imagem
   - Key: `files`, Type: **File**, Value: Selecione terceira imagem

**Dica**: No Postman, use o mesmo nome `files` para todos os arquivos (será convertido em array).

---

## ✅ Validações Implementadas

1. ✅ **Array aceita até 3 arquivos**
   - Se enviar mais de 3, retorna erro 400

2. ✅ **Arquivos nulos/vazios são ignorados**
   - Filtrados automaticamente no controller

3. ✅ **Upload no S3 apenas dos arquivos válidos**
   - Cada arquivo gera uma URL única

4. ✅ **URLs armazenadas no banco**
   - Formato: `url1,url2,url3` (separadas por vírgula)

---

## 📊 Fluxo Completo

```
┌─────────────────────────────────────────┐
│  Frontend envia array de MultipartFile │
│  files[] = [foto1, foto2, foto3]       │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│  Controller converte para List          │
│  Filtra arquivos nulos/vazios           │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│  Use Case valida máximo de 3            │
│  Faz upload de cada arquivo no S3       │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│  Gateway converte List<String> → String │
│  Salva: "url1,url2,url3"                │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│  PostgreSQL armazena na coluna          │
│  image_links VARCHAR(1500)              │
└─────────────────────────────────────────┘
```

---

## 📁 Arquivos Alterados

### 1. Resource
- ✅ `VirtualMinutesResource.java`
  - Parâmetro alterado de `image1, image2, image3` para `files[]`

### 2. Controller  
- ✅ `VirtualMinutesController.java`
  - Lógica de conversão de array para lista
  - Filtragem de arquivos nulos/vazios

### 3. Documentação
- ✅ `VIRTUAL_MINUTES_API.md`
  - Exemplos atualizados com array
  - Instruções de uso do parâmetro `files`

- ✅ `GUIA_RAPIDO_ATA_VIRTUAL_V2.md`
  - Exemplos práticos com cURL e Postman
  - Dicas de uso do array

---

## 🧪 Testes Recomendados

### Teste 1: 3 Fotos
```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -F 'minutesRequest={"date":"2026-02-11","description":"Teste 3 fotos"}' \
  -F "files=@foto1.jpg" \
  -F "files=@foto2.jpg" \
  -F "files=@foto3.jpg"
```

**Resultado Esperado**: 
- Status 201
- 3 URLs no `imageLinks`

### Teste 2: 1 Foto
```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -F 'minutesRequest={"date":"2026-02-12","description":"Teste 1 foto"}' \
  -F "files=@foto1.jpg"
```

**Resultado Esperado**:
- Status 201
- 1 URL no `imageLinks`

### Teste 3: Sem Fotos
```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -F 'minutesRequest={"date":"2026-02-13","description":"Teste sem fotos"}'
```

**Resultado Esperado**:
- Status 201
- `imageLinks` vazio ou null

### Teste 4: Mais de 3 Fotos (Erro)
```bash
curl -X POST "http://localhost:8080/pathfinders/v1/virtual-minutes/secretaria?unitId=1&userId=5" \
  -F 'minutesRequest={"date":"2026-02-14","description":"Teste erro"}' \
  -F "files=@foto1.jpg" \
  -F "files=@foto2.jpg" \
  -F "files=@foto3.jpg" \
  -F "files=@foto4.jpg"
```

**Resultado Esperado**:
- Status 400
- Mensagem: "Máximo de 3 imagens permitidas por ata de secretaria"

---

## 🎯 Comparação: Antes vs Agora

### Antes (Parâmetros Separados)
❌ 3 parâmetros individuais (`image1`, `image2`, `image3`)  
❌ Frontend precisa nomear cada arquivo diferente  
❌ Limitação de nomes fixos  

### Agora (Array)
✅ 1 parâmetro array (`files[]`)  
✅ Frontend pode enviar de 0 a 3 arquivos facilmente  
✅ Mesmo padrão usado em especialidades  
✅ Mais flexível e escalável  
✅ Código mais limpo no controller  

---

## 🎊 Status Final

| Item | Status |
|------|--------|
| Resource atualizado | ✅ |
| Controller atualizado | ✅ |
| Lógica de conversão | ✅ |
| Validação de máximo 3 | ✅ |
| Upload no S3 | ✅ |
| Armazenamento no banco | ✅ |
| Documentação API | ✅ |
| Guia rápido | ✅ |
| Exemplos cURL | ✅ |
| Exemplos Postman | ✅ |

---

## ✅ Pronto para Usar!

A aplicação agora recebe as fotos como **array de MultipartFile**, exatamente como solicitado, seguindo o mesmo padrão do cadastro de especialidades.

### Próximo Passo:
```bash
mvn clean compile
mvn spring-boot:run
```

Testar no Swagger ou Postman! 🚀

---

**Versão**: 2.1.0  
**Data**: 11 de Fevereiro de 2026  
**Status**: ✅ Implementado e Documentado

