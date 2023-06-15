package com.baye.exemplecustomerservice.web;

import com.baye.exemplecustomerservice.dto.CustomerRequest;
import com.baye.exemplecustomerservice.entites.Customer;
import com.baye.exemplecustomerservice.mapper.CustomerMapper;
import com.baye.exemplecustomerservice.repository.CustomerRepository;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@WebService(serviceName = "CustomerWS")
public class CustomerSoapService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    @WebMethod
    public List<Customer> customerList(){
        return customerRepository.findAll();
    }

    @WebMethod
    public Customer customerById(@WebParam(name = "id") Long id){
        return customerRepository.findById(id).get();
    }

    @WebMethod
    public Customer saveCustomer(@WebParam CustomerRequest customerRequest){
        Customer customer = customerMapper.from(customerRequest);
        return customerRepository.save(customer);
    }
}
