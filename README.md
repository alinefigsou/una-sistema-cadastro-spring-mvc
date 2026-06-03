# Sistema de Cadastro e Gestão de Pedidos

Sistema web desenvolvido com Spring MVC e Thymeleaf para gerenciamento de usuários, clientes, produtos e pedidos. Projeto acadêmico — UNA.

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.5.0 |
| Spring MVC | (via spring-boot-starter-web) |
| Spring Data JPA | (via spring-boot-starter-data-jpa) |
| Thymeleaf | (via spring-boot-starter-thymeleaf) |
| Spring Security | (via spring-boot-starter-security) |
| Spring Validation | (via spring-boot-starter-validation) |
| PostgreSQL | 16 |
| Hibernate | 6.6 |
| Lombok | (via anotações) |
| Bootstrap | 5.3.3 (CDN) |
| Bootstrap Icons | 1.11.3 (CDN) |
| Maven | Wrapper incluso |

---

## Pré-requisitos

- Java 17+
- Docker e Docker Compose
- Maven (ou usar o wrapper `./mvnw` incluso)

---

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/aline/cadastro/
│   │   ├── SistemaCadastroSpringMvcApplication.java
│   │   ├── config/
│   │   │   └── SecurityConfig.java          # Desativa proteção HTTP, expõe BCryptPasswordEncoder
│   │   ├── controller/
│   │   │   ├── HomeController.java           # GET / → dashboard
│   │   │   ├── UsuarioController.java        # CRUD /usuarios
│   │   │   ├── ClienteController.java        # CRUD /clientes
│   │   │   ├── ProdutoController.java        # CRUD /produtos
│   │   │   └── PedidoController.java         # CRUD /pedidos
│   │   ├── dto/
│   │   │   ├── PedidoForm.java               # Form object para criação de pedido
│   │   │   └── PedidoItemForm.java           # Item individual do pedido
│   │   ├── entity/
│   │   │   ├── Usuario.java
│   │   │   ├── Cliente.java
│   │   │   ├── Produto.java
│   │   │   ├── Pedido.java
│   │   │   └── PedidoItem.java
│   │   ├── enums/
│   │   │   ├── Role.java                     # ADMIN | USER
│   │   │   ├── StatusPedido.java             # PENDENTE | CONFIRMADO | CANCELADO | ENTREGUE
│   │   │   └── CategoriaProduto.java         # ELETRONICO | VESTUARIO | ALIMENTO | MOVEL | OUTROS
│   │   ├── repository/
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── ClienteRepository.java
│   │   │   ├── ProdutoRepository.java
│   │   │   ├── PedidoRepository.java
│   │   │   └── PedidoItemRepository.java
│   │   └── service/
│   │       ├── UsuarioService.java           # Inclui codificação BCrypt
│   │       ├── ClienteService.java
│   │       ├── ProdutoService.java
│   │       └── PedidoService.java            # Valida estoque e calcula total
│   └── resources/
│       ├── application.properties
│       └── templates/
│           ├── layout/
│           │   └── base.html                 # Layout mestre (navbar + flash messages)
│           ├── index.html                    # Dashboard
│           ├── usuario/  (list.html, form.html)
│           ├── cliente/  (list.html, form.html)
│           ├── produto/  (list.html, form.html)
│           └── pedido/   (list.html, form.html, detail.html)
└── test/
    ├── java/.../SistemaCadastroSpringMvcApplicationTests.java
    └── resources/application.properties     # Override com H2 para testes
```

---

## Configuração do Ambiente

### 1. Arquivo `.env`

Crie um arquivo `.env` na raiz do projeto com as credenciais do banco:

```env
POSTGRES_DB=cadastro_db
POSTGRES_USER=cadastro_user
POSTGRES_PASSWORD=sua_senha_aqui
```

> As variáveis `POSTGRES_DB`, `POSTGRES_USER` e `POSTGRES_PASSWORD` são usadas tanto pelo Docker Compose (para criar o banco) quanto pelo Spring Boot (para se conectar). Mantenha os valores iguais nos dois contextos.

Variáveis opcionais (têm valores padrão):

```env
DB_HOST=localhost        # padrão: localhost
DB_PORT=5432             # padrão: 5432
SERVER_PORT=8080         # padrão: 8080
```

---

## Como Executar

### 1. Subir o banco de dados

```bash
docker compose up -d
```

Isso inicia um container PostgreSQL 16 com os dados persistidos em volume Docker (`postgres_data`).

Para verificar se subiu corretamente:

```bash
docker ps
# deve mostrar: cadastro_postgres   Up X seconds
```

Para parar:

```bash
docker compose down
```

Para parar **e remover os dados** (reset completo do banco):

```bash
docker compose down -v
```

---

### 2. Executar a aplicação

O Spring Boot não lê o arquivo `.env` automaticamente. É necessário exportar as variáveis para o shell antes de rodar:

```bash
set -a && source .env && set +a && ./mvnw spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

---

### 3. Executar os testes

Os testes usam banco H2 em memória — **não exigem Docker rodando**:

```bash
./mvnw test
```

---

## Rotas da Aplicação

### Dashboard

| Método | URL | Descrição |
|---|---|---|
| GET | `/` | Dashboard com contadores |

### Usuários

| Método | URL | Descrição |
|---|---|---|
| GET | `/usuarios` | Listar usuários |
| GET | `/usuarios/novo` | Formulário de criação |
| POST | `/usuarios/novo` | Salvar novo usuário |
| GET | `/usuarios/{id}/editar` | Formulário de edição |
| POST | `/usuarios/{id}/editar` | Atualizar usuário |
| GET | `/usuarios/{id}/excluir` | Excluir usuário |

### Clientes

| Método | URL | Descrição |
|---|---|---|
| GET | `/clientes` | Listar clientes |
| GET | `/clientes/novo` | Formulário de criação |
| POST | `/clientes/novo` | Salvar novo cliente |
| GET | `/clientes/{id}/editar` | Formulário de edição |
| POST | `/clientes/{id}/editar` | Atualizar cliente |
| GET | `/clientes/{id}/excluir` | Excluir cliente |

### Produtos

| Método | URL | Descrição |
|---|---|---|
| GET | `/produtos` | Listar produtos |
| GET | `/produtos/novo` | Formulário de criação |
| POST | `/produtos/novo` | Salvar novo produto |
| GET | `/produtos/{id}/editar` | Formulário de edição |
| POST | `/produtos/{id}/editar` | Atualizar produto |
| GET | `/produtos/{id}/excluir` | Excluir produto |

### Pedidos

| Método | URL | Descrição |
|---|---|---|
| GET | `/pedidos` | Listar pedidos |
| GET | `/pedidos/novo` | Formulário de criação com itens dinâmicos |
| POST | `/pedidos/novo` | Criar pedido (valida estoque) |
| GET | `/pedidos/{id}` | Detalhe do pedido |
| POST | `/pedidos/{id}/status` | Atualizar status do pedido |
| GET | `/pedidos/{id}/excluir` | Excluir pedido |

---

## Modelo de Dados

### Usuario

| Campo | Tipo | Restrições |
|---|---|---|
| id | Long | PK, auto |
| nome | String | obrigatório, 2–100 chars |
| email | String | obrigatório, único, formato e-mail |
| senha | String | armazenada como hash BCrypt |
| role | Role (enum) | ADMIN \| USER |
| ativo | boolean | padrão: true |

### Cliente

| Campo | Tipo | Restrições |
|---|---|---|
| id | Long | PK, auto |
| nome | String | obrigatório, 2–100 chars |
| telefone | String | obrigatório |
| email | String | formato e-mail (opcional) |
| cpfCnpj | String | obrigatório, único |
| endereco | String | opcional |
| ativo | boolean | padrão: true |

### Produto

| Campo | Tipo | Restrições |
|---|---|---|
| id | Long | PK, auto |
| nome | String | obrigatório, 2–100 chars |
| descricao | String | opcional (TEXT) |
| preco | BigDecimal | obrigatório, mínimo 0.01 |
| estoque | int | mínimo 0 |
| categoria | CategoriaProduto (enum) | opcional |
| ativo | boolean | padrão: true |

### Pedido

| Campo | Tipo | Restrições |
|---|---|---|
| id | Long | PK, auto |
| cliente | Cliente | ManyToOne, obrigatório |
| itens | List\<PedidoItem\> | OneToMany, cascade ALL |
| total | BigDecimal | calculado automaticamente |
| dataPedido | LocalDateTime | preenchido no @PrePersist |
| status | StatusPedido (enum) | padrão: PENDENTE |

### PedidoItem

| Campo | Tipo | Restrições |
|---|---|---|
| id | Long | PK, auto |
| pedido | Pedido | ManyToOne |
| produto | Produto | ManyToOne |
| quantidade | int | mínimo 1 |
| precoUnitario | BigDecimal | capturado no momento da venda |

---

## Regras de Negócio

- **Senha de usuário**: armazenada com BCrypt. Em edição, deixar o campo em branco preserva a senha atual.
- **Estoque**: ao criar um pedido, o sistema valida se há estoque suficiente para cada item. Se não houver, a operação é rejeitada com mensagem de erro.
- **Preço unitário**: capturado do produto no momento da criação do pedido, preservando o valor histórico mesmo que o preço do produto mude depois.
- **Total do pedido**: calculado automaticamente pela soma de `precoUnitario × quantidade` de cada item.
- **DDL automático**: `spring.jpa.hibernate.ddl-auto=update` — o Hibernate cria e atualiza as tabelas automaticamente na inicialização.

---

## Variáveis de Ambiente — Referência Completa

| Variável | Usado por | Padrão | Descrição |
|---|---|---|---|
| `POSTGRES_DB` | Docker + Spring | `cadastro_db` | Nome do banco |
| `POSTGRES_USER` | Docker + Spring | `cadastro_user` | Usuário do banco |
| `POSTGRES_PASSWORD` | Docker + Spring | `cadastro_pass` | Senha do banco |
| `DB_HOST` | Spring | `localhost` | Host do PostgreSQL |
| `DB_PORT` | Spring | `5432` | Porta do PostgreSQL |
| `SERVER_PORT` | Spring | `8080` | Porta do servidor web |
