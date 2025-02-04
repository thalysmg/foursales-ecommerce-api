# Projeto Processo Seletivo Salesfour

## API RESTful para E-Commerce com SpringBoot, MySQL, Kafka e ElasticSearch e Autenticação com Spring Security e JWT e Liquibase para versionamento do banco de dados.

Este projeto consiste numa API RESTful simulando algumas operações de um e-Commerce, sendo elas:
- Cadastrar, atualizar e excluir produtos
- Visualizar produtos e realizar pedidos

A aplicação faz uso do ElasticSearch [ElasticSearch](https://www.elastic.co/) - um banco de dados não relacional otimizado para consultas - para listagem dos produtos, sendo feita a indexação no banco sempre que ocorre um cadastro, atualização ou exclusão de produto no banco principal (MySQL).

Foi utilizado o Kafka [Kafka](https://kafka.apache.org/) - como serviço de mensageria e processamento assíncrono - para realizar a baixa do pedido e atualizar o estoque quando ocorre o pagamento de um pedido.

## Instruções para rodar o projeto:

### 1 - Requisitos
- **Docker e Docker Compose instalado** ([Docker](https://www.docker.com/))
- **Uma IDE de sua preferência**

### 2 - Instruções para executar o projeto
1. Clonar repositório em sua máquina.
2. Importar o projeto na sua IDE de escolha.
3. Executar o comando `docker compose up -d` na raiz do projeto (mesmo nível do arquivo `docker-compose.yml`). O arquivo contém os serviços da aplicação (MySQL, ElasticSearch e Kafka).
4. Rodar a aplicação.

## 3 - APIs Disponíveis:

### 3.1 - Login
```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "usuario@test.com",
    "senha": "admin123"
}'
```
**Response:**
```json
{
  "token": "Bearer TOKEN_VALUE",
  "dataExpiracao": "2025-02-06 17:23:15"
}
```

### 3.2 - Cadastrar Produto
```bash
curl --location 'http://localhost:8080/produtos' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN_VALUE' \
--data '{
    "nome": "Produto 4",
    "descricao": "Descricao Produto 4",
    "preco": 1212.61,
    "categoria": "PERIFERICOS",
    "qtdEstoque": 10
}'
```

### 3.3 - Atualizar Produto
```bash
curl --location --request PUT 'http://localhost:8080/produtos/8ae65ce8-2b33-4bbd-be22-108f554d2ef6' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN_VALUE' \
--data '{
    "nome": "Produto 1",
    "descricao": "Descricao Produto 1 Atualizado",
    "preco": 1212.61,
    "categoria": "ELETRONICOS",
    "qtdEstoque": 10
}'
```

### 3.4 - Excluir Produto
```bash
curl --location --request DELETE 'http://localhost:8080/produtos/c05735c5-e65e-4906-b7f1-8c034a56b634' \
--header 'Authorization: Bearer TOKEN_VALUE'
```

### 3.5 - Listar Produtos
```bash
curl --location 'http://localhost:8080/produtos?page=0&size=10&sort=preco,DESC' \
--header 'Authorization: Bearer TOKEN_VALUE'
```
**Parâmetros disponíveis:**
- `nome` (string)
- `categoria` (string)
- `dataMin` (Decimal)
- `dataMax` (Decimal)

### 3.6 - Cadastrar Pedido
```bash
curl --location 'http://localhost:8080/pedidos' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer TOKEN_VALUE' \
--data '{
    "produtosQuantidades": [
        {
            "idProduto": "9a8a9782-0a86-4505-876c-42d27b15dd12",
            "quantidade": 2
        },
        {
            "idProduto": "e1ddd133-39b5-4936-9537-1621b77d5fbb",
            "quantidade": 1
        }
    ]
}'
```

### 3.7 - Registrar Pagamento Pedido
```bash
curl --location --request PUT 'http://localhost:8080/pagamentos?idPedido=dfa58793-1186-4110-9222-d3d169612100' \
--header 'Authorization: Bearer TOKEN_VALUE'
```

### 3.8 - Relatórios
```bash
curl --location 'http://localhost:8080/relatorios' \
--header 'Authorization: Bearer TOKEN_VALUE'
```
**Response:**
```json
{
  "ticketsMedios": [
    {
      "cliente": "917d98a6-e284-11ef-9e24-0242ac120002",
      "valorMedio": 922.635000
    }
  ],
  "clientesValorTotalTop5": [
    {
      "cliente": "917d98a6-e284-11ef-9e24-0242ac120002",
      "valorTotal": 1845.27
    }
  ],
  "faturamentoMensal": 1845.27
}
```