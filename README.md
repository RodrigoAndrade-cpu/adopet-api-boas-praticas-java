# 🐾 Adopet API

API REST desenvolvida em Java para gerenciamento de um sistema de adoção de pets.

Este projeto foi desenvolvido com foco principalmente em **boas práticas de programação, organização de código, regras de negócio e testes automatizados**, aplicando conceitos estudados durante minha formação em desenvolvimento Back-end Java.

---

## 🎯 Objetivo do projeto

O objetivo é desenvolver uma API capaz de representar um sistema de adoção de animais, permitindo o gerenciamento de:

- Abrigos
- Pets
- Tutores
- Solicitações de adoção
- Validações das regras de negócio
- Status das solicitações de adoção

Além da implementação da API, o projeto teve como principal foco a criação de **testes automatizados**, garantindo maior confiabilidade e segurança no comportamento da aplicação.

---

## 🛠️ Tecnologias utilizadas

- ☕ Java
- 🌱 Spring Boot
- 🌐 Spring Web
- 🗄️ JPA / Hibernate
- 🐘 PostgreSQL
- 🧪 JUnit
- 🎭 Mockito
- 📦 Maven
- 🔗 API REST

---

## 🏗️ Estrutura do projeto

O projeto foi organizado utilizando uma estrutura baseada em responsabilidades:

```text
src
├── main
│   └── java
│       └── br.com.alura.adopet.api
│           ├── controller
│           ├── dto
│           ├── exceptions
│           ├── model
│           ├── repository
│           ├── service
│           └── validations
│               └── validacaoAdocao
│
└── test
    └── java
        └── br.com.alura.adopet.api
            ├── controller
            ├── service
            └── validations
                └── validacaoAdocao