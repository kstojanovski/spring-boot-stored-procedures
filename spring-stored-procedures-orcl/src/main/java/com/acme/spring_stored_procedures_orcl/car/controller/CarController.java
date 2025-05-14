package com.acme.spring_stored_procedures_orcl.car.controller;

import com.acme.spring_stored_procedures_orcl.car.service.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
class CarController {

   private final CarService carService;

   CarController(CarService carService) {
      this.carService = carService;
   }

   @GetMapping("/get-cars-after-years/{years}")
   List<Car> getCarsAfterYears(@PathVariable Integer years) {
      return carService.getCarsAfterYears(years);
   }

   @GetMapping("/get-total-cars-by-model/{model}")
   int getTotalCarsByModel(@PathVariable String model) {
      return carService.getTotalCarsByModel(model);
   }
}
