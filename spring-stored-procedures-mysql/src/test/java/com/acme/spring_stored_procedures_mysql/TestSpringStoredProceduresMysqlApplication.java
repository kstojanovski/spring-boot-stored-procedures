package com.acme.spring_stored_procedures_mysql;

import org.springframework.boot.SpringApplication;

public class TestSpringStoredProceduresMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringStoredProceduresMysqlApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
