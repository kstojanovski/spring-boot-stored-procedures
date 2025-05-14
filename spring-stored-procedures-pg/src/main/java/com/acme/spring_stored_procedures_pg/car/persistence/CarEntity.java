package com.acme.spring_stored_procedures_pg.car.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "car")
public class CarEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column
   private long id;

   @Column
   private String model;

   @Column
   private Integer year;

   @Column
   private String brand;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getModel() {
      return model;
   }

   public void setModel(String model) {
      this.model = model;
   }

   public Integer getYear() {
      return year;
   }

   public void setYear(Integer year) {
      this.year = year;
   }

   public String getBrand() {
      return brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }
}