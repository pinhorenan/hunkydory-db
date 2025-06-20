# Projeto Banco de Dados – Loja Virtual "Hunky Dory"

Este projeto é um sistema de gerenciamento de uma loja virtual fictícia chamado **"Hunky Dory"** desenvolvido como trabalho prático da disciplina de Projeto de Banco de Dados. O sistema é construído com JavaFX, PostgreSQL e JDBC para gerenciar clientes, produtos, fornecedores, vendas, estoque e operações administrativas.

---

## Sobre o projeto

A aplicação permite:

- Cadastro e gerenciamento de clientes e endereços.
- Controle de produtos, categorias e fornecedores.
- Realização e controle de pedidos de compra, incluindo itens de compra.
- Gerenciamento do estoque dos produtos automaticamente com base nas vendas.
- Avaliações de clientes, trocas e devoluções.
- Consultas SQL avançadas diretamente na interface gráfica.

---

## Tecnologias utilizadas

- Java 17+
- JavaFX 20+
- PostgreSQL 15+
- JDBC

---

## Estrutura do projeto

```
📦hunkydory
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┗ 📂ui
 ┃ ┃ ┣ 📂resources
 ┃ ┗ 📂test
 ┗ build.gradle
```

---

## Como compilar e executar

**Pré-requisitos:**
- [Java JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Gradle](https://gradle.org/)
- [PostgreSQL 15+](https://www.postgresql.org/download/)

**Passos:**

1. Clone este repositório:

```bash
git clone https://github.com/pinhorenan/hunkydory-db.git
cd hunkydory-db
```

2. Crie e configure seu banco PostgreSQL:

```bash
psql -U postgres
CREATE DATABASE hunkydory;
```

- Execute o script SQL disponível em `scripts/` para criar a estrutura do banco e popular os dados iniciais.

3. Configure a conexão com o banco em:
```plaintext
src/main/java/hunkydory/infrastructure/ConnectionFactory.java
```

```java
private static final String URL = "jdbc:postgresql://localhost/hunkydory";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

4. Compile o projeto com Gradle:

```bash
gradle build
```

5. Execute o projeto:

```bash
gradle run
```
---

## Contribuição

Este projeto é um trabalho acadêmico e, portanto, não está aberto para contribuições externas.

---

## Autores

- Renan Pinho, Gabriel Moura

---

## Licença

Este projeto é somente para fins educacionais e acadêmicos.
