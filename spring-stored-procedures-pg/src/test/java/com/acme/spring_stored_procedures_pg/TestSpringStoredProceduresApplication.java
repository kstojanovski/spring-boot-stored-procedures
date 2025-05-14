package com.acme.spring_stored_procedures_pg;

import org.springframework.boot.SpringApplication;

public class TestSpringStoredProceduresApplication {

   public static void main(String[] args) {
      SpringApplication.from(SpringStoredProceduresApplication::main).with(TestcontainersConfiguration.class).run(args);
   }

}
