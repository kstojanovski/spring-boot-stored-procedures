package com.acme.spring_stored_procedures_orcl.car.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {

   @Procedure(name = "Car.findCarsAfterYear")
   List<CarEntity> findCarsAfterYear(@Param("year_in") Integer year);

   @Procedure("GET_TOTAL_CARS_BY_MODEL")
   int getTotalCarsByModel(String model);

}
