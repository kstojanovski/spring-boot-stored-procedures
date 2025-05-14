package com.acme.spring_stored_procedures_mssql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SpringStoredProceduresMssqlApplicationTests {

	@Test
	void contextLoads() {
	}

}
