# 🌍 Mapa do Corre — Back-end

> API REST para a plataforma Mapa do Corre — vitrine digital para empreendedores informais.

O back-end do **Mapa do Corre** é responsável por toda a lógica de negócio da plataforma, incluindo autenticação de usuários, gerenciamento de negócios, avaliações e sistema de pedidos.

---

## 🎯 Responsabilidades

- Autenticação e autorização de usuários (Cliente e Empresário)
- Cadastro e gerenciamento de negócios informais
- Classificação de negócios por categoria e tipo
- Avaliações de negócios por clientes
- Sistema de pedidos com controle de status
- Busca por nome, categoria e localização

---

## 🗂️ Entidades do Sistema

| Entidade | Descrição |
|---|---|
| `Usuario` | Credenciais de acesso à plataforma (email e senha) |
| `Perfil` | Nível de acesso do usuário (Admin, Cliente ou Empresário) |
| `Pessoa` | Classe abstrata com dados pessoais comuns a Cliente e Empresário |
| `Cliente` | Consumidor da plataforma — pode avaliar negócios e realizar pedidos |
| `Empresario` | Empreendedor informal — pode cadastrar e gerenciar seus negócios |
| `Negocio` | Empreendimento divulgado na plataforma |
| `Endereco` | Localização de um negócio |
| `Categoria` | Classificação temática dos negócios |
| `Avaliacao` | Nota e comentário atribuídos a um negócio por um cliente |
| `Pedido` | Solicitação de compra ou serviço feita por um cliente |

---

## 🔗 Relacionamentos

| Relacionamento | Cardinalidade |
|---|---|
| `Usuario` → `Perfil` | 1 para 1 |
| `Pessoa` → `Cliente` | Herança |
| `Pessoa` → `Empresario` | Herança |
| `Empresario` → `Negocio` | 1 para N |
| `Negocio` → `Endereco` | 1 para 1 |
| `Negocio` → `Categoria` | N para 1 |
| `Negocio` → `Avaliacao` | 1 para N |
| `Cliente` → `Avaliacao` | 1 para N |
| `Cliente` → `Pedido` | 1 para N |
| `Pedido` → `Negocio` | N para 1 |

---

## 🛣️ Rotas previstas

### 🔐 Auth
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Login do usuário |
| `POST` | `/auth/logout` | Logout do usuário |

### 👤 Usuários
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/usuarios` | Listar todos os usuários |
| `GET` | `/usuarios/{id}` | Buscar usuário por ID |
| `PUT` | `/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/usuarios/{id}` | Deletar usuário |

### 🧑 Clientes
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/clientes` | Cadastrar cliente (cria Usuario + Perfil automaticamente) |
| `GET` | `/clientes` | Listar todos os clientes |
| `GET` | `/clientes/{id}` | Buscar cliente por ID |
| `PUT` | `/clientes/{id}` | Atualizar cliente |
| `DELETE` | `/clientes/{id}` | Deletar cliente |

### 💼 Empresários
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/empresarios` | Cadastrar empresário (cria Usuario + Perfil automaticamente) |
| `GET` | `/empresarios` | Listar todos os empresários |
| `GET` | `/empresarios/{id}` | Buscar empresário por ID |
| `PUT` | `/empresarios/{id}` | Atualizar empresário |
| `DELETE` | `/empresarios/{id}` | Deletar empresário |

### 🏪 Negócios
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/negocios` | Cadastrar negócio (vinculado ao empresário logado) |
| `GET` | `/negocios` | Listar todos os negócios (feed) |
| `GET` | `/negocios/{id}` | Buscar negócio por ID |
| `PUT` | `/negocios/{id}` | Atualizar negócio |
| `DELETE` | `/negocios/{id}` | Deletar negócio |

### 🏷️ Categorias
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/categorias` | Cadastrar categoria |
| `GET` | `/categorias` | Listar todas as categorias |
| `GET` | `/categorias/{id}` | Buscar categoria por ID |
| `PUT` | `/categorias/{id}` | Atualizar categoria |
| `DELETE` | `/categorias/{id}` | Deletar categoria |

### ⭐ Avaliações
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/negocios/{id}/avaliacoes` | Avaliar negócio |
| `GET` | `/negocios/{id}/avaliacoes` | Listar avaliações de um negócio |

### 📦 Pedidos
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/pedidos` | Realizar pedido |
| `GET` | `/pedidos/{id}` | Buscar pedido por ID |
| `GET` | `/clientes/{id}/pedidos` | Listar pedidos de um cliente |
| `PUT` | `/pedidos/{id}/status` | Atualizar status do pedido |
| `DELETE` | `/pedidos/{id}` | Cancelar pedido |

---

## 📋 Sprints

### Sprint 1 — 12 Jun a 28 Jun
| Task | Descrição |
|---|---|
| MAP-1 | CRUD de Usuário (GET, PUT, DELETE) |
| MAP-2 | CRUD de Clientes (com cadastro via cascade) |
| MAP-3 | CRUD de Empresários (com cadastro via cascade) |
| MAP-4 | Autenticação (login, logout, redirecionamento por perfil) |
| MAP-5 | CRUD de Categoria |
| MAP-6 | CRUD de Negócio |

### Sprint 2
| Task | Descrição |
|---|---|
| MAP-7 | CRUD de Endereço (vinculado ao Negócio) |
| MAP-8 | CRUD de Avaliação |
| MAP-9 | CRUD de Pedido com controle de StatusPedido |

---

## 🏗️ Estrutura do Projeto

```
📦 mapadocorreapi
└── 📂 src
    └── 📂 main
        ├── 📂 java/br/com/ifba/mapadocorreapi
        │   ├── 📂 controller      # Endpoints REST
        │   ├── 📂 service         # Regras de negócio
        │   ├── 📂 repository      # Acesso ao banco de dados
        │   ├── 📂 entity          # Entidades JPA
        │   ├── 📂 dto             # Data Transfer Objects
        │   └── 📄 MapadocorreApplication.java
        └── 📂 resources
            └── 📄 application.properties
```

---

## 🛠️ Tecnologias

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)

---

## 🔗 Repositório Front-end

> [mapa-do-corre (front-end)](https://github.com/raibeep/prg04mapadocorrefront)

---


## 🔗 Deploy

> 🔗 https://prg04mapadocorreback-production.up.railway.app

---

<p align="center">Feito com ❤️ para fortalecer a economia local</p>
