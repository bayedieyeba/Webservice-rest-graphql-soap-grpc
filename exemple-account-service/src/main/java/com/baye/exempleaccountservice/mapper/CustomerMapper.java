package com.baye.exempleaccountservice.mapper;

import com.baye.exempleaccountservice.model.Customer;
import com.baye.exemplecustomerservice.stub.CustomerServiceOuterClass;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private ModelMapper modelMapper = new ModelMapper();
    public Customer fromSoapCustomer(com.baye.exemplecustomerservice.web.Customer soapCustomer){
        return modelMapper.map(soapCustomer,Customer.class);
    }

    public Customer fromGrpcCustomer(CustomerServiceOuterClass.Customer grpcCustomer){
        return modelMapper.map(grpcCustomer,Customer.class);
    }
}
