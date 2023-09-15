package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.example.*")
@SpringBootTest
class EcommerceProductCatalogApplicationTests {

	@Test
	void contextLoads() {
	}

}
