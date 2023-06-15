package com.baye.exemplecustomerservice;

import com.baye.exemplecustomerservice.entites.Customer;
import com.baye.exemplecustomerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExempleCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExempleCustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository){
		return args -> {
			customerRepository.save(Customer.builder().name("Baye Dieye").email("bayedieyeba@gmail.com").build());
			customerRepository.save(Customer.builder().name("Baye Modou").email("bayemodouba@gmail.com").build());
			customerRepository.save(Customer.builder().name("Anta").email("antaba@gmail.com").build());
		};
	}
}
