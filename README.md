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
AUTH
POST   /auth/login
POST   /auth/cadastro
USUARIOS
GET    /usuarios/:id
PUT    /usuarios/:id
DELETE /usuarios/:id
NEGOCIOS
GET    /negocios
GET    /negocios/:id
POST   /negocios
PUT    /negocios/:id
DELETE /negocios/:id
POSTAGENS
GET    /postagens/feed
GET    /postagens/:id
POST   /postagens
DELETE /postagens/:id
COMENTARIOS
GET    /postagens/:id/comentarios
POST   /postagens/:id/comentarios
DELETE /comentarios/:id
AVALIACOES
GET    /negocios/:id/avaliacoes
POST   /negocios/:id/avaliacoes
SEGUIDORES
POST   /usuarios/:id/seguir
DELETE /usuarios/:id/seguir
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

> [mapa-do-corre (front-end)](https://github.com/raibeep/prg04webraikaverena)

---

## 🔗 Deploy

> 🚧 Link da API: _em breve_

---

<p align="center">Feito com ❤️ para fortalecer a economia local</p>
