# Inventory Management API ☕

API REST para controle de produtos e movimentações de estoque, desenvolvida como projeto de estudos em **Java e Spring Boot**. O tema foi escolhido por sua aplicação em sistemas de gestão (ERP).

> **Status:** versão inicial de estudo (MVP). Não é um sistema pronto para produção.

## Tecnologias

- Java 21
- Spring Boot 3.5.16
- Maven
- Spring Web, Spring Data JPA e Bean Validation
- H2 em memória para desenvolvimento e testes
- JUnit 5 e MockMvc (testes de integração)

## Funcionalidades

- Cadastrar produtos com nome, SKU, preço e quantidade inicial
- Listar e consultar produtos por ID
- Alterar nome e preço
- Registrar entrada e saída de estoque
- Impedir saída quando não há quantidade disponível
- Rejeitar preço negativo, quantidade negativa e SKU duplicado
- Excluir produtos

## Executar localmente

Pré-requisitos: **JDK 21** e **Maven 3.6.3+** instalados.

```bash
git clone https://github.com/ArthurMSousa/inventory-management-api.git
cd inventory-management-api
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

> O banco H2 é **temporário**: os dados são perdidos quando a aplicação é encerrada.

## Exemplo prático

Criar produto:

```bash
curl -i -X POST http://localhost:8080/api/products \
  -H 'Content-Type: application/json' \
  -d '{"name":"Filtro de óleo","sku":"FILTRO-001","price":39.90,"quantity":5}'
```

Listar produtos:

```bash
curl http://localhost:8080/api/products
```

Registrar saída de duas unidades do produto de ID 1:

```bash
curl -i -X POST http://localhost:8080/api/products/1/stock-out \
  -H 'Content-Type: application/json' \
  -d '{"quantity":2}'
```

## Endpoints

| Método | Rota | Ação |
|---|---|---|
| `POST` | `/api/products` | Cadastrar produto |
| `GET` | `/api/products` | Listar produtos |
| `GET` | `/api/products/{id}` | Consultar por ID |
| `PUT` | `/api/products/{id}` | Atualizar nome e preço |
| `POST` | `/api/products/{id}/stock-in` | Registrar entrada |
| `POST` | `/api/products/{id}/stock-out` | Registrar saída |
| `DELETE` | `/api/products/{id}` | Excluir produto |

## Testes

```bash
mvn test
```

Os testes cobrem criação, listagem e rejeição de preço negativo. Novos testes serão adicionados conforme o projeto evoluir.

## Organização do código

- `product/ProductController.java`: endpoints HTTP
- `product/ProductService.java`: regras de negócio
- `product/ProductRepository.java`: persistência com JPA
- `product/Product.java`: entidade do banco
- `product/ProductDtos.java`: objetos de entrada e saída e validações

## Próximas melhorias

- [ ] Histórico de movimentações de estoque (data, tipo e quantidade)
- [ ] Testes para estoque insuficiente e SKU duplicado
- [ ] PostgreSQL com migrações Flyway
- [ ] Paginação, filtros e documentação OpenAPI
- [ ] Tratamento padronizado de erros
- [ ] Docker e automação de testes com GitHub Actions

## Sobre

Projeto educacional de [Arthur Sousa](https://github.com/ArthurMSousa), bacharel em Sistemas de Informação e profissional com experiência em sistemas corporativos e ERP, em desenvolvimento técnico em Java.
