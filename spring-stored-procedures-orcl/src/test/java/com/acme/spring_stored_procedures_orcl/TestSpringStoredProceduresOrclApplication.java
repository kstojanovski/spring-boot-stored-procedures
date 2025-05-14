package com.acme.spring_stored_procedures_orcl;

import org.springframework.boot.SpringApplication;

public class TestSpringStoredProceduresOrclApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringStoredProceduresOrclApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
