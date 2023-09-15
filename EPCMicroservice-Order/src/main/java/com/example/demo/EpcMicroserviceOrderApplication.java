package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = { "com.example", "com.example.Configuration" })
public class EpcMicroserviceOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpcMicroserviceOrderApplication.class, args);
	}

//	@Bean
//	public RestTemplate restTemplate() {
//	    return new RestTemplate();
//	}

}
