package com.acme.spring_stored_procedures_mysql.car;

import com.acme.spring_stored_procedures_mysql.car.persistence.CarEntity;
import com.acme.spring_stored_procedures_mysql.car.persistence.CarRepository;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.assertj.core.api.AbstractIntegerAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.List;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableConfigurationProperties(LiquibaseProperties.class)
class CarDataJpaTest {

   @Container
   @ServiceConnection
   static MySQLContainer<?> mysqlServerContainer = new MySQLContainer<>("mysql:8.4.5");

   @Autowired
   CarRepository carRepository;

   @Autowired
   SpringLiquibase springLiquibase;

   /**
    * Only a try if it is possible to do similar approach like Flyway. There, Flyway has already a bean and here we need
    * to create it manually.
    *
    * @throws LiquibaseException
    * @throws SQLException
    */
   @BeforeEach
   void setUp() throws LiquibaseException, SQLException {
      Liquibase liquibase = new Liquibase(
          "./db/changelog/test-changelog-instance.xml",
          new ClassLoaderResourceAccessor(CarRepository.class.getClassLoader()),
          DatabaseFactory
              .getInstance()
              .findCorrectDatabaseImplementation(
                  new JdbcConnection(springLiquibase.getDataSource().getConnection())
              )
      );
//      liquibase.dropAll(); // also drop-everything-from-db-script needs to be done here.
      liquibase.clearCheckSums();
//      liquibase.validate();
      liquibase.update();
   }

   @Test
   void testGetCarsAfterYears() {
      // Act
      List<CarEntity> carsAfterYear = carRepository.findCarsAfterYear(1980);

      // Assert
      ListAssert<CarEntity> carEntityListAssert = Assertions.assertThat(carsAfterYear);
      carEntityListAssert.isNotNull();
      carEntityListAssert.isNotEmpty();
      carEntityListAssert.hasSize(5);
   }

   @Test
   void testGetTotalCarsByModel() {
      // Act
      int totalCarsByModel = carRepository.getTotalCarsByModel("Civic");

      // Assert
      AbstractIntegerAssert<?> abstractIntegerAssert = Assertions.assertThat(totalCarsByModel);
      abstractIntegerAssert.isNotNull();
      abstractIntegerAssert.isEqualTo(1);
   }

   @Test
   void testGetTotalCarsByModelSelect() {
      // Act
      int totalCarsByModel = carRepository.getTotalCarsByModelSelect("Civic");

      // Assert
      AbstractIntegerAssert<?> abstractIntegerAssert = Assertions.assertThat(totalCarsByModel);
      abstractIntegerAssert.isNotNull();
      abstractIntegerAssert.isEqualTo(1);
   }
}
