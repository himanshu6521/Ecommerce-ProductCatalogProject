package com.example.Configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class OrderRestTemplate {

	@Bean
	@LoadBalanced
	//loadbalanced is used to distribute load among instances
	public RestTemplate restTemplateOrder() {
	    return new RestTemplate();
	}

}
