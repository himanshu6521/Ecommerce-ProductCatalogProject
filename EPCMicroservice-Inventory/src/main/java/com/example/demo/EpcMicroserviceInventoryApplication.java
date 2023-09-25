package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;

@ComponentScan(basePackages = "com.example.*")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RibbonClients

public class EpcMicroserviceInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpcMicroserviceInventoryApplication.class, args);
	}
	
//	@Bean
//	@Primary
//	public RestTemplate InventoryrestTemplate() {
//		return new RestTemplate();
//	}
	
//	method realted to tracing 
	//@Bean(name = "customDefaultSampler") // Rename the bean
//	public Sampler defaultSampler() {
//	    return Sampler.ALWAYS_SAMPLE;
//	}
}
