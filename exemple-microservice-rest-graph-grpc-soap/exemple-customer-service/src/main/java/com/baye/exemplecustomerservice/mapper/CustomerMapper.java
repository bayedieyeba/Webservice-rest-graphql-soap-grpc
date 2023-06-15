package com.baye.exemplecustomerservice.mapper;

import com.baye.exemplecustomerservice.dto.CustomerRequest;
import com.baye.exemplecustomerservice.entites.Customer;
import com.baye.exemplecustomerservice.stub.CustomerServiceOuterClass;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    private ModelMapper modelMapper = new ModelMapper();
    public Customer from(CustomerRequest customerRequest){
        //Customer customer = new Customer();
        //customer.setName(customerRequest.name());
        //customer.setEmail(customerRequest.email());
       return  modelMapper.map(customerRequest,Customer.class);
    }

    public CustomerServiceOuterClass.Customer fromCustomer(Customer customer){
        return modelMapper.map(customer,CustomerServiceOuterClass.Customer.Builder.class).build();
    }

    public Customer fromGrpcCustomerRequest(CustomerServiceOuterClass.CustomerRequest customerRequest){
        return modelMapper.map(customerRequest,Customer.class);
    }
}
