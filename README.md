# vendas

API REST em **Spring Boot** para cadastro e gerenciamento de vendas, clientes, vendedores e produtos.

---

## Tecnologias

* Java 17+ (ou versão compatível com o projeto)
* Spring Boot
* Spring Data JPA
* H2 (banco em memória, usado para desenvolvimento)
* MapStruct (ou outro mapper) para conversões entre Entidade <-> DTO
* DTOs separados: *Request* (entrada) e *Response* (saída)

---

## Entidades principais

* **Cliente**
* **Vendedor**
* **Produto**
* **Venda** (contém itens da venda vinculando Produto preço)

O projeto segue a arquitetura em camadas: `controller` → `service` → `repository` e usa mappers para converter entre entidades e DTOs.

---

## Estrutura sugerida do projeto

```
src/
 ├── main/
 │   ├── java/com/test/venda/
 │   │    ├── api/
 │   │    │   ├── common/
 │   │    │   ├── controller/
 │   │    │   ├── dto/
 │   │    │   ├── exceptionhandler/
 │   │    │   └── mappers/
 │   │    ├── domain/
 │   │    │   ├── entity/
 │   │    │   ├── exceptions/
 │   │    │   ├── repository/
 │   │    │   └── service/
 │   │    └── VendaApplication.java
 │   └── resources/
 └── test/java/com/test/venda/

```
* URL: `http://localhost:8080/h2-console`
* JDBC URL (padrão em `application.properties`): `jdbc:h2:mem:testdb`
* Usuário/Senha: conforme `application.properties` (padrão: `sa` / `""`).

