package com.baye.exemplecustomerservice.web;

import com.baye.exemplecustomerservice.dto.CustomerRequest;
import com.baye.exemplecustomerservice.entites.Customer;
import com.baye.exemplecustomerservice.mapper.CustomerMapper;
import com.baye.exemplecustomerservice.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerGrapQLController {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @QueryMapping
    public List<Customer> allCustomers(){
        return customerRepository.findAll();
    }

    @QueryMapping
    public Customer customerById(@Argument Long id){
       Customer customer = customerRepository.findById(id).orElse(null);
       if(customer ==null) throw new RuntimeException(String.format("Customer %s not found", id));
       return customer;
    }

    @MutationMapping
    public Customer saveCutomer(@Argument CustomerRequest customerRequest){
        Customer customer = customerMapper.from(customerRequest);
        return customerRepository.save(customer);
    }
}
