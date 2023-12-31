package com.baye.exempleaccountservice;

import com.baye.exemplecustomerservice.web.CustomerSoapService;
import com.baye.exemplecustomerservice.web.CustomerWS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class ExempleAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExempleAccountServiceApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	CustomerSoapService customerSoapService(){
		return  new CustomerWS().getCustomerSoapServicePort();
	}
}
