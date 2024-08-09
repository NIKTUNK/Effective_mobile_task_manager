package com.nikitatunkel.eff_mob;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskManagementApplicationTests {

	@Test
	void contextLoads() {
		// Этот тест проверяет, что контекст Spring успешно загружается
	}

	@Test
	void testApplicationStarts() {
		TaskManagementApplication.main(new String[] {});
		// Этот тест проверяет, что основное приложение запускается без ошибок
	}
}
