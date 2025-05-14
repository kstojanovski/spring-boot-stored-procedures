package com.acme.spring_stored_procedures_orcl.car.service;

import com.acme.spring_stored_procedures_orcl.car.controller.Car;
import com.acme.spring_stored_procedures_orcl.car.persistence.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

   CarRepository carRepository;

   CarService(CarRepository carRepository) {
      this.carRepository = carRepository;
   }

   @Transactional(readOnly = true)
   public List<Car> getCarsAfterYears(int years) {
      return carRepository.findCarsAfterYear(years)
          .stream()
          .map(carEntity ->
              new Car(
                  carEntity.getId(),
                  carEntity.getModel(),
                  carEntity.getYear(),
                  carEntity.getBrand()
              ))
          .collect(Collectors.toList());
   }

   public int getTotalCarsByModel(String model) {
      return carRepository.getTotalCarsByModel(model);
   }

}
