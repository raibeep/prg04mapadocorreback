# 🌍 Mapa do Corre — Back-end

> API REST para a plataforma Mapa do Corre.

O back-end do **Mapa do Corre** é responsável por toda a lógica de negócio da plataforma, incluindo autenticação de usuários, gerenciamento de negócios, feed de postagens, comentários, avaliações e sistema de seguidores.

---

## 🎯 Responsabilidades

- Autenticação e autorização de usuários
- CRUD de negócios, postagens e comentários
- Sistema de seguidores e feed personalizado
- Avaliações de estabelecimentos
- Busca por nome, categoria e localização

---

## 🗂️ Entidades do Sistema

| Entidade | Descrição |
|---|---|
| `Usuario` | Pessoa cadastrada na plataforma, consumidor ou dono de negócio |
| `Negocio` | Estabelecimento cadastrado por um usuário |
| `Endereco` | Localização de um negócio |
| `Categoria` | Classificação temática dos negócios |
| `Postagem` | Conteúdo publicado no feed por um usuário |
| `Comentario` | Resposta de um usuário a uma postagem |
| `Avaliacao` | Nota e comentário atribuídos a um negócio |
| `Seguidor` | Relação de seguir entre dois usuários |

---

## 🔗 Relacionamentos

| Relacionamento | Cardinalidade |
|---|---|
| Usuario → Negocio | 1 para N |
| Negocio → Endereco | 1 para 1 |
| Negocio → Categoria | N para 1 |
| Usuario → Postagem | 1 para N |
| Negocio → Postagem | 1 para N |
| Postagem → Comentario | 1 para N |
| Negocio → Avaliacao | 1 para N |
| Usuario → Seguidor | N para N |

---

## 🛣️ Rotas previstas

### 🔐 Auth
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Login do usuário |
| `POST` | `/auth/cadastro` | Cadastro de novo usuário |

### 👤 Usuários
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/usuarios/:id` | Buscar usuário por ID |
| `PUT` | `/usuarios/:id` | Atualizar usuário |
| `DELETE` | `/usuarios/:id` | Deletar usuário |

### 🏪 Negócios
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/negocios` | Listar todos os negócios |
| `GET` | `/negocios/:id` | Buscar negócio por ID |
| `POST` | `/negocios` | Cadastrar negócio |
| `PUT` | `/negocios/:id` | Atualizar negócio |
| `DELETE` | `/negocios/:id` | Deletar negócio |

### 📰 Postagens
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/postagens/feed` | Feed personalizado |
| `GET` | `/postagens/:id` | Buscar postagem por ID |
| `POST` | `/postagens` | Criar postagem |
| `DELETE` | `/postagens/:id` | Deletar postagem |

### 💬 Comentários
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/postagens/:id/comentarios` | Listar comentários |
| `POST` | `/postagens/:id/comentarios` | Comentar em postagem |
| `DELETE` | `/comentarios/:id` | Deletar comentário |

### ⭐ Avaliações
| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/negocios/:id/avaliacoes` | Listar avaliações |
| `POST` | `/negocios/:id/avaliacoes` | Avaliar negócio |

### 👥 Seguidores
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/usuarios/:id/seguir` | Seguir usuário |
| `DELETE` | `/usuarios/:id/seguir` | Deixar de seguir |
---

## 🏗️ Estrutura do Projeto

```
📦 mapa-do-corre-api
└── 📂 src
    └── 📂 main
        ├── 📂 java/com/mapadocorre
        │   ├── 📂 controller      # Endpoints REST
        │   ├── 📂 service         # Regras de negócio
        │   ├── 📂 repository      # Acesso ao banco de dados
        │   ├── 📂 model           # Entidades (classes do diagrama)
        │   └── 📄 Application.java
        └── 📂 resources
            └── 📄 application.properties
```
---

## 🛠️ Tecnologias

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)

---

## 🔗 Repositório Front-end

> [mapa-do-corre (front-end)](https://github.com/raibeep/prg04mapadocorrefront)

---

## 🔗 Deploy

> 🚧 Link da API: _em breve_

---

<p align="center">Feito com ❤️ para fortalecer a economia local</p>
