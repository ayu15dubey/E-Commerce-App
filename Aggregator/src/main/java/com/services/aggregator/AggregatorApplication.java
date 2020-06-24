package com.services.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
@EnableCircuitBreaker
public class AggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AggregatorApplication.class, args);
	}

}
