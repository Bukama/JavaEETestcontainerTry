# Goal of JavaEETestcontainerTry

This project tries to create an integration test example using [Testcontainers](https://github.com/testcontainers/testcontainers-java) against an _Oracle_ database with some special requirements:

# Requirements

**General requirements**:
* Java 8
* Oracle Database (12.1)
* JavaEE 7 application
  * Accessing to Database using _Java EE DataSource_ of application server

**Tools inside application**:
* Hibernate as ORM
* JUnit 5 for tests
* Testcontainers for integration tests

**Database requirements**:
* The user behind the applications data source has only specific DML rights
* There is another user / datasource available with wider DML rights to prepare tests if required (e.g. preparing test data for special purposes which should not to be in general schema) 
* None of the both users has any DDL rights and they are not the schema owner

**Testcontainer requirements**:
* The application schema with main test data should be inside the container image so test preparation 

**Further requirements**:
* Database uses synonms to be stage independet

# Others

Feel free to contribute!
