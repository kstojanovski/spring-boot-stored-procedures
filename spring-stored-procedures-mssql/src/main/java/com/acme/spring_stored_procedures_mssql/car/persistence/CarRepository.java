package com.acme.spring_stored_procedures_mssql.car.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {

   @Query(value = "EXEC FIND_CARS_AFTER_YEAR @year_in = :yearParam;", nativeQuery = true)
   List<CarEntity> findCarsAfterYear(@Param("yearParam") Integer year);

   @Query(value = "EXEC GET_TOTAL_CARS_BY_MODEL @model_in = :modelIn, @count_out = :countOut;", nativeQuery = true)
   int getTotalCarsByModel(String modelIn, Integer countOut);

}
