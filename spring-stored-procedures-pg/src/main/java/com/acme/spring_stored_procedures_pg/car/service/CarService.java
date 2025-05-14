package com.acme.spring_stored_procedures_pg.car.service;

import com.acme.spring_stored_procedures_pg.car.controller.Car;
import com.acme.spring_stored_procedures_pg.car.persistence.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

   CarRepository carRepository;

   CarService(CarRepository carRepository) {
      this.carRepository = carRepository;
   }

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
      return carRepository.getTotalCarsByModel(model, 42);
   }
}
