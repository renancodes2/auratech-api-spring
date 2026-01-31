# 🚀 AuraTech API (Spring Boot Migration)

Este repositório contém a nova versão do Backend da [AuraTech](https://auratech-frontend.vercel.app), originalmente desenvolvido em NestJS e Prisma, agora sendo migrado para o ecossistema Java com Spring Boot 3.
O objetivo desta migração é elevar a robustez da aplicação, aproveitando a tipagem forte do Java 21 e a maturidade do Spring Security para o gerenciamento de autenticação e permissões (RBAC).


## ✅ O que já foi implementado:

- Fundação & Performance: Migração para Java 21 e Spring Boot 3.5.x para aproveitar o melhor desempenho da JVM.
- Segurança: Implementação de autenticação via JWT com filtros customizados no Spring Security para proteção de rotas.
- Gestão de Identidade: Endpoints de Registro e Login configurados com suporte a UUID nativo e permissões (Roles).
- Persistência Profissional: PostgreSQL configurado com Flyway para versionamento de banco de dados (substituindo as migrations do Prisma).

## Tecnologias:

- Java 21 & Spring Boot 3
- Spring Security (Autenticação e autorização com JWT (JSON Web Tokens))
- Spring Data JPA
- Spring Validation
- PostgreSQL(neon(aws))
- Flyway
- Lombok & MapStruct
- Cloudinary
- Docker & GitHub Actions (Infraestrutura e CI/CD)

## Infraestrutura e DevOps

- Docker: Containerização para ambientes idênticos em dev e produção.
- GitHub Actions: Pipeline de CI/CD para deploy automatizado no Render.
- Maven: Gerenciamento de dependências e automação de build.

