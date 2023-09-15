package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.example.*")
@EnableHystrix
public class EcommerceProductCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceProductCatalogApplication.class, args);
	}
	
	
//all apis running and connected to DB
	
//	@Bean  
//	//creating a sampler called always sampler ,this code was for distributed 
	//tracing using zipkin and rabbitmq .not used in this project, however dependency of slueth is added
//	public Sampler defaultSampler()  
//	{  
//	return Sampler.ALWAYS_SAMPLE;  
	
}
