# Sistema de Gestão de Tarefas

Este é um sistema completo de gestão de tarefas, desenvolvido como projeto de faculdade. Ele permite que usuários gerenciem suas tarefas em um painel estilo Kanban, com suporte a comentários, filtros e autenticação segura.

## 🔧 Tecnologias Utilizadas

### 🖥️ Frontend
- [React.js](https://reactjs.org/)
- CSS Modularizado

### 🛠️ Backend
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL

## 💡 Funcionalidades

- Cadastro e login de usuários com autenticação via token JWT
- Criação, edição e exclusão de tarefas
- Listagem de tarefas em layout **Kanban**
- Filtros por status, título e descrição
- Comentários em tarefas
- Controle de acesso por papéis (`ROLE_CUSTOMER`, `ROLE_ADMINISTRATOR`)
- Responsividade e experiência fluida

## 🧱 Estrutura do Banco de Dados

O projeto utiliza PostgreSQL. As tabelas principais são:

- `users`: Cadastro de usuários
- `roles`: Perfis de acesso
- `tasks`: Tarefas com título, descrição, status e prazo
- `task_comments`: Comentários vinculados a tarefas
- `users_roles`: Relacionamento N:N entre usuários e papéis

Veja o script completo [aqui](./dados/setup.sql).

## Documentacao

- [Casos de Uso descritos](./documentation/documentation-usecases.md)
- [Diagrama de Casos de Uso](./documentation/DIAGRAMAS%20DE%20CASOS%20DE%20USO.pdf)
- [Diagrama de Entidade e Relacionamentos](./documentation/der_gestao-tarefas.png)
- [Diagrama de Classes](./documentation/diagrama-de-classes.jpg)

## 🚀 Como Executar

### Pré-requisitos

- Java 17
- Node.js + npm
- Docker e Docker Compose (opcional para subir o PostgreSQL)

<h3>Abra o terminal na raiz do projeto e execute os comandos:</h3>

### Banco de dados

```bash
docker-compose up -d
```

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm start
```

## 🐛 Testes

Após executar os passos anteriores, você pode testar no navegador acessando a rota: http://localhost:5173/

### Insomnia Collection

Caso queira, é possível testar o projeto via Insomnia ou Postman com a collection: [Insomnia Collection](./Insomnia_Collections_2025-05-24.json)