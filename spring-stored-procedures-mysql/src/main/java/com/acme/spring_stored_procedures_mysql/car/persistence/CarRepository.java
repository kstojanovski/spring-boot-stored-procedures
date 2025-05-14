package com.acme.spring_stored_procedures_mysql.car.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {

   @Query(value = "CALL FIND_CARS_AFTER_YEAR(:yearIn);", nativeQuery = true)
   List<CarEntity> findCarsAfterYear(@Param("yearIn") Integer year);

   @Procedure("GET_TOTAL_CARS_BY_MODEL")
   int getTotalCarsByModel(String model);

   @Query(value = "CALL GET_TOTAL_CARS_BY_MODEL_SELECT(:modelIn, @count_out);", nativeQuery = true)
   int getTotalCarsByModelSelect(String modelIn);

}
