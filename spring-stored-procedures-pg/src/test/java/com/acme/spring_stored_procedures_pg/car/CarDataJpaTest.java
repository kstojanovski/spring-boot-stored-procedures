package com.acme.spring_stored_procedures_pg.car;

import com.acme.spring_stored_procedures_pg.car.persistence.CarEntity;
import com.acme.spring_stored_procedures_pg.car.persistence.CarRepository;
import org.assertj.core.api.AbstractIntegerAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.startupcheck.MinimumDurationRunningStartupCheckStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;

@Testcontainers
@DataJpaTest(
    properties = {
        "spring.flyway.clean-disabled=false" //means allow to use flyway clean!
    }
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarDataJpaTest {

   @Container
   @ServiceConnection
   static PostgreSQLContainer<?> postgresContainer =
       new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.5"))
           .withStartupCheckStrategy(
               // I can not explain why we do need this.
               new MinimumDurationRunningStartupCheckStrategy(Duration.ofSeconds(3))
           );
   @Autowired
   CarRepository carRepository;

   @BeforeEach
   void clearDatabase(@Autowired Flyway flyway) {
      flyway.clean();
      flyway.migrate();
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
      int totalCarsByModel = carRepository.getTotalCarsByModel("Focus", 123456);

      // Assert
      AbstractIntegerAssert<?> abstractIntegerAssert = Assertions.assertThat(totalCarsByModel);
      abstractIntegerAssert.isNotNull();
      abstractIntegerAssert.isEqualTo(2);
   }
}
