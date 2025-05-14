package com.acme.spring_stored_procedures_mssql;

import org.springframework.boot.SpringApplication;

public class TestSpringStoredProceduresMssqlApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringStoredProceduresMssqlApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
