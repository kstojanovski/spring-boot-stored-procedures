package com.acme.spring_stored_procedures_mysql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class SpringStoredProceduresMysqlApplicationTests {

	@Test
	void contextLoads() {
	}

}
