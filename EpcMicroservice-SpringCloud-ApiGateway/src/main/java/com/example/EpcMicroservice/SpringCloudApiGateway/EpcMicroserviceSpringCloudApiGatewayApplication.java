package com.example.EpcMicroservice.SpringCloudApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;


@SpringBootApplication
@EnableDiscoveryClient

public class EpcMicroserviceSpringCloudApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpcMicroserviceSpringCloudApiGatewayApplication.class, args);
	}
	//creating a bean  
	@Bean  
	//creating a sampler called   
	public Sampler defaultSampler()  
	{  
	return Sampler.ALWAYS_SAMPLE;  
	}  
}
