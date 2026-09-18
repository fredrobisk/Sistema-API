# API Clínica Veterinária

API REST para gestão de uma clínica veterinária, desenvolvida com Spring Boot como trabalho da disciplina **Programação Orientada a Objetos para Web II**.

Permite o cadastro de clientes, pets, veterinários e consultas, com relacionamentos entre as entidades, validações, tratamento de erros e documentação interativa via Swagger.

## Tecnologias

- Java 21
- Spring Boot 3.3.4
- Spring Web
- Spring Data JPA
- Bean Validation
- Flyway (versionamento de banco de dados)
- PostgreSQL
- springdoc-openapi (Swagger UI)
- Lombok
- Maven

## Estrutura do projeto

```
src/main/java/com/vet/api
├── controller      # Endpoints REST
├── domain          # Entidades JPA e DTOs, organizados por agregado
│   ├── cliente
│   ├── pet
│   ├── veterinario
│   └── consulta
├── infra
│   ├── exception    # Tratamento global de exceções (@RestControllerAdvice)
│   └── springdoc     # Configuração do Swagger/OpenAPI
└── ApiApplication.java

src/main/resources
├── application.properties
└── db/migration      # Scripts de versionamento Flyway
```

## Modelo de domínio

- **Cliente** possui vários **Pets** (`@OneToMany`, cascade e orphan removal)
- **Pet** pertence a um **Cliente** (`@ManyToOne`)
- **Consulta** relaciona um **Pet** e um **Veterinário**, e possui um status (`AGENDADA`, `REALIZADA`, `CANCELADA`)

## Pré-requisitos

- JDK 21
- PostgreSQL em execução
- Maven (ou use o wrapper `./mvnw` incluído no projeto)

## Configuração

Crie o banco de dados:

```sql
CREATE DATABASE vetdb;
```

Ajuste as credenciais em `src/main/resources/application.properties` se necessário:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vetdb
spring.datasource.username=postgres
spring.datasource.password=0137
```

> Recomenda-se substituir a senha fixa por uma variável de ambiente, por exemplo:
> `spring.datasource.password=${DB_PASSWORD:0137}`

As tabelas são criadas automaticamente na primeira execução, através das migrations do Flyway localizadas em `src/main/resources/db/migration`.

## Como executar

```bash
# clonar o repositório
git clone <url-do-repositorio>
cd api

# executar a aplicação
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

## Documentação da API (Swagger)

Com a aplicação em execução, a documentação interativa fica disponível em:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Endpoints

| Recurso | Método | Endpoint | Descrição |
|---|---|---|---|
| Clientes | GET | `/clientes` | Lista todos os clientes |
| | GET | `/clientes/{uuid}` | Detalha um cliente |
| | POST | `/clientes` | Cadastra um cliente |
| | PUT | `/clientes/{uuid}` | Atualiza um cliente |
| | DELETE | `/clientes/{uuid}` | Remove um cliente |
| Pets | GET | `/pets` | Lista todos os pets |
| | GET | `/pets/{uuid}` | Detalha um pet |
| | POST | `/pets` | Cadastra um pet |
| | PUT | `/pets/{uuid}` | Atualiza um pet |
| | DELETE | `/pets/{uuid}` | Remove um pet |
| Veterinários | GET | `/veterinarios` | Lista todos os veterinários |
| | GET | `/veterinarios/{uuid}` | Detalha um veterinário |
| | POST | `/veterinarios` | Cadastra um veterinário |
| | PUT | `/veterinarios/{uuid}` | Atualiza um veterinário |
| | DELETE | `/veterinarios/{uuid}` | Remove um veterinário |
| Consultas | GET | `/consultas` | Lista todas as consultas |
| | GET | `/consultas/{uuid}` | Detalha uma consulta |
| | POST | `/consultas` | Agenda uma consulta |
| | PUT | `/consultas/{uuid}` | Atualiza uma consulta |
| | DELETE | `/consultas/{uuid}` | Cancela/remove uma consulta |

Todos os cadastros (`POST`) retornam `201 Created` com o header `Location` apontando para o recurso criado; as remoções (`DELETE`) retornam `204 No Content`.

## Tratamento de erros

Erros de validação, recursos não encontrados e violações de integridade são tratados de forma centralizada em `infra/exception`, retornando respostas padronizadas com o código HTTP adequado (`400`, `404`, `409`).

## Autor

Trabalho desenvolvido para a disciplina DPADP0346 - Programação Orientada a Objetos para Web II.
