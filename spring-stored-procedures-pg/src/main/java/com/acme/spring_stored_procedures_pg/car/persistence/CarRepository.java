package com.acme.spring_stored_procedures_pg.car.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {

   @Query(value = "SELECT * FROM FIND_CARS_AFTER_YEAR(:yearParam)", nativeQuery = true)
   List<CarEntity> findCarsAfterYear(@Param("yearParam") Integer year);

   @Query(value = "CALL GET_TOTAL_CARS_BY_MODEL(:modelIn, :countOut);", nativeQuery = true)
   int getTotalCarsByModel(String modelIn, Integer countOut);

}
