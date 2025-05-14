# Spring Boot example with DB stored procedures through Spring Data JPA
## Introduction
This is a example how stored procedures defined in the database are used in a Spring Boot application through Spring
Data JPA.
## Delete data strategy as testing preparation
From the article: https://maciejwalkowiak.com/blog/spring-boot-flyway-clear-database-integration-tests/
### JdbcTestUtils
    @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "person");
    }
### Repository
    @BeforeEach
    void clearDatabase(@Autowired PersonRepository personRepository) {
        personRepository.deleteAll();
    }
### Flyway
	@BeforeEach
	void clearDatabase(@Autowired Flyway flyway) {
		flyway.clean();
		flyway.migrate();
	}
## New findings
### Stored procedures and functions
In the article https://www.baeldung.com/spring-data-jpa-stored-procedures, MySql and here Postgres as relational database is used.
Using Postgres means also that some solutions which are implemented in MySql as stores procedures need to be declared as functions in Postgres, especially when you want to read data sets from the database tables. Why they need to be so different it's another question, but it was "fun" to rethink the solution. 
### Testcontainers
Questions opened for me:
1. Why do I need to wait three seconds so that everything should work as intended?
2. Why there are still alert messages that there are ryuk connection errors and no solution can solve or suppress them? 
## Links
Based on:
* https://www.baeldung.com/spring-data-jpa-stored-procedures
* https://maciejwalkowiak.com/blog/spring-boot-flyway-clear-database-integration-tests/
* https://www.baeldung.com/database-migrations-with-flyway