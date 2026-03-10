# 🗣️ ForumHub API

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.11-brightgreen?style=flat&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat&logo=postgresql)
![Gradle](https://img.shields.io/badge/Gradle-8-02303A?style=flat&logo=gradle)
![JWT](https://img.shields.io/badge/JWT-Auth0-black?style=flat&logo=jsonwebtokens)
![Status](https://img.shields.io/badge/status-concluído-brightgreen)

> Desafio Backend do programa **Oracle Next Education (ONE)** em parceria com a **Alura**.
> API REST para gerenciamento de tópicos de um fórum, com autenticação JWT.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Como Executar](#como-executar)
- [Endpoints](#endpoints)
- [Autenticação](#autenticação)
- [Autor](#autor)

---

## 📖 Sobre o Projeto

O **ForumHub** é uma API REST que replica o funcionamento de um fórum de dúvidas, permitindo que usuários autenticados criem, listem, detalhem, atualizem e removam tópicos de discussão.

---

## ✅ Funcionalidades

- [x] Cadastro de tópico com validação de duplicatas
- [x] Listagem de tópicos com paginação e ordenação
- [x] Filtro de tópicos por curso e ano
- [x] Detalhamento de tópico por ID
- [x] Atualização parcial de tópico
- [x] Exclusão de tópico
- [x] Autenticação com JWT (login/senha)
- [x] Proteção de endpoints — apenas usuários autenticados

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.5.11 |
| Spring Security | 6.x |
| Spring Data JPA | 3.x |
| Flyway Migration | 10.x |
| PostgreSQL | 16 |
| Gradle | 8.x |
| Auth0 Java JWT | 4.4.0 |
| Lombok | 1.18.x |

---

## 📁 Estrutura do Projeto

```
src/main/java/br/com/victorforumhub/forumhub
├── config/
│   └── SecurityConfigurations.java
├── controller/
│   ├── AutenticacaoController.java
│   └── TopicoController.java
├── entity/
│   ├── Topico.java
│   └── Usuario.java
├── filter/
│   └── SecurityFilter.java
├── model/
│   ├── DadosAtualizacaoTopico.java
│   ├── DadosAutenticacao.java
│   ├── DadosCadastroTopico.java
│   ├── DadosDetalhamentoTopico.java
│   ├── DadosListagemTopico.java
│   └── DadosTokenJWT.java
├── repository/
│   ├── TopicoRepository.java
│   └── UsuarioRepository.java
├── service/
│   ├── AutenticacaoService.java
│   └── TokenService.java
└── ForumhubApplication.java

src/main/resources
├── db/migration/
│   ├── V1__create-table-topicos.sql
│   └── V2__create-table-usuarios.sql
├── application.properties.example
└── application.properties        ← não versionado
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- PostgreSQL 16+
- Gradle 8+

### Configuração

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/forumhub.git
cd forumhub
```

2. Crie o banco de dados no PostgreSQL:
```sql
CREATE DATABASE forumhub;
```

3. Copie o arquivo de propriedades:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

4. Preencha o `application.properties` com suas credenciais:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/forumhub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
api.security.token.secret=sua-chave-secreta
```

5. Insira um usuário no banco com senha BCrypt:
```sql
INSERT INTO usuarios (login, senha)
VALUES ('seu_login', '$2a$10$hash_gerado_pelo_bcrypt');
```

6. Execute a aplicação:
```bash
./gradlew bootRun
```

A API estará disponível em `http://localhost:8080`.

---

## 📡 Endpoints

### 🔓 Público

| Método | URI | Descrição |
|---|---|---|
| `POST` | `/login` | Autenticar e obter token JWT |

### 🔒 Autenticado (requer Bearer Token)

| Método | URI | Descrição | Status |
|---|---|---|---|
| `POST` | `/topicos` | Cadastrar novo tópico | `201 Created` |
| `GET` | `/topicos` | Listar tópicos (paginado) | `200 OK` |
| `GET` | `/topicos?curso=Java&ano=2026` | Filtrar por curso e ano | `200 OK` |
| `GET` | `/topicos/{id}` | Detalhar tópico | `200 OK` |
| `PUT` | `/topicos/{id}` | Atualizar tópico | `200 OK` |
| `DELETE` | `/topicos/{id}` | Deletar tópico | `204 No Content` |

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação. O token expira em **2 horas**.

### 1. Login

```http
POST /login
Content-Type: application/json

{
  "login": "seu_login",
  "senha": "sua_senha"
}
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 2. Usando o token

Inclua o token no header de todas as requisições:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Exemplo — Cadastrar tópico

```http
POST /topicos
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "titulo": "Dúvida sobre Spring Security",
  "mensagem": "Como configurar o JWT corretamente?",
  "autor": "Victor",
  "curso": "Java"
}
```

---

## 👨‍💻 Autor

**Victor Eduardo Meireles**  
Desenvolvido como parte do programa Oracle Next Education (ONE) — Alura · 2026
