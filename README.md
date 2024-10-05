Portal DBV - Gerenciamento de Clubes de Desbravadores
-
O Sistema tem como intuito oferecer um gerenciamento de algumas áreas de um clube de desbravadores, como a parte financeira, registro de usuários, presença, sistema de unidades, eventos, especialidades e classes.

## Event Storming do Projeto
```url
https://miro.com/app/board/uXjVKZvkHXE=/?share_link_id=538318725651
```

## Para simular ambiente localmente

1 - Clonar o projeto
```bash
$ git clone https://github.com/Felipehyo/portaldbv-backend.git
```
2 - Rodar comando no diretório raiz do projeto para subir container com instâncias do banco Postgres para que permitir o funcionamento do ambiente local.
```bash
$ docker-compose up --build
```

## Para acessar o swagger e realizar os testes
Rota para acessar Swagger
```url
http://localhost:8080/pathfinders/v1/swagger-ui
```
Rota para acessar Swagger.yml
```url
http://localhost:8080/pathfinders/v1/api-docs
```

[//]: # (Dentro do Projeto no diretório "postman" há um arquivo com uma collection postman com todas as rotas mapeadas para teste)

[//]: # (```)

[//]: # (./postman/Pos_Tech-Arquitetura_Hexagonal-Lanchonete)

[//]: # (```)

## Idealizador do Projeto
- Felipe Pinheiro Dantas - (11) 95992-5142