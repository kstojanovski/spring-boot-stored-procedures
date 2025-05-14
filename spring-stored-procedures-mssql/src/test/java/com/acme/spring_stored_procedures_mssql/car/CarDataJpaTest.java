package com.acme.spring_stored_procedures_mssql.car;

import com.acme.spring_stored_procedures_mssql.car.persistence.CarEntity;
import com.acme.spring_stored_procedures_mssql.car.persistence.CarRepository;
import jakarta.annotation.Resource;
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
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLException;
import java.util.List;

@Testcontainers
@DataJpaTest
@Resource(name = "resources")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableConfigurationProperties(LiquibaseProperties.class)
public class CarDataJpaTest {

   @Container
   @ServiceConnection
   static MSSQLServerContainer<?> mssqlServerContainer =
       new MSSQLServerContainer<>(DockerImageName.parse("mcr.microsoft.com/mssql/server:2022-CU18-ubuntu-22.04"))
           .acceptLicense();

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
      Liquibase liquibase = new liquibase
          .Liquibase(
          "./db/changelog/test-changelog-instance.xml",
          new ClassLoaderResourceAccessor(CarRepository.class.getClassLoader()),
          DatabaseFactory
              .getInstance()
              .findCorrectDatabaseImplementation(
                  new JdbcConnection(springLiquibase.getDataSource().getConnection())
              )
      );
//      A_Str0ng_Required_Password
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
      int totalCarsByModel = carRepository.getTotalCarsByModel("Civic", 123456);

      // Assert
      AbstractIntegerAssert<?> abstractIntegerAssert = Assertions.assertThat(totalCarsByModel);
      abstractIntegerAssert.isNotNull();
      abstractIntegerAssert.isEqualTo(1);
   }
}
