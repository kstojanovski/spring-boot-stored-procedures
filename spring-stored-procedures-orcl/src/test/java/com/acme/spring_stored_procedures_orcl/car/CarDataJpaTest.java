package com.acme.spring_stored_procedures_orcl.car;

import com.acme.spring_stored_procedures_orcl.car.persistence.CarEntity;
import com.acme.spring_stored_procedures_orcl.car.persistence.CarRepository;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.assertj.core.api.AbstractIntegerAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.startupcheck.MinimumDurationRunningStartupCheckStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.oracle.OracleContainer;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

@Disabled(value = "Disabled due to problems with test container and liquibase")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableConfigurationProperties(LiquibaseProperties.class)
@ActiveProfiles(profiles={"test", "default"})
class CarDataJpaTest {

   @Container
   @ServiceConnection
   static OracleContainer orclServerContainer =
           new OracleContainer("gvenzl/oracle-free:latest"); // 23.4-slim-faststart

   static {
      orclServerContainer.start();
      System.out.println(orclServerContainer.getLogs());
   }

   @Autowired
   CarRepository carRepository;

   @Autowired
   SpringLiquibase springLiquibase;

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

}
