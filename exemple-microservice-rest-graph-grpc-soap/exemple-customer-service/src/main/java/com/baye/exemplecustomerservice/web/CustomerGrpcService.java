package com.baye.exemplecustomerservice.web;

import com.baye.exemplecustomerservice.entites.Customer;
import com.baye.exemplecustomerservice.mapper.CustomerMapper;
import com.baye.exemplecustomerservice.repository.CustomerRepository;
import com.baye.exemplecustomerservice.stub.CustomerServiceGrpc;
import com.baye.exemplecustomerservice.stub.CustomerServiceOuterClass;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class CustomerGrpcService extends CustomerServiceGrpc.CustomerServiceImplBase {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void getAllCustomers(CustomerServiceOuterClass.GetAllCustomersRequest request, StreamObserver<CustomerServiceOuterClass.GetAllCustomerResponse> responseObserver) {

        List<Customer> customerList = customerRepository.findAll();
        List<CustomerServiceOuterClass.Customer> grpcCustomers= new ArrayList<>();

        List<CustomerServiceOuterClass.Customer> customers =
                customerList.stream().map(customerMapper::fromCustomer).collect(Collectors.toList());
        CustomerServiceOuterClass.GetAllCustomerResponse customerResponse=
                CustomerServiceOuterClass.GetAllCustomerResponse.newBuilder()
                        .addAllCustomers(grpcCustomers)
                                .build();
        responseObserver.onNext(customerResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomerById(CustomerServiceOuterClass.GetCustomerByIdRequest request, StreamObserver<CustomerServiceOuterClass.GetCustomerByIdResponse> responseObserver) {
        Customer customerEntity = customerRepository.findById(request.getCustomerId()).get();
        CustomerServiceOuterClass.GetCustomerByIdResponse response=
                CustomerServiceOuterClass.GetCustomerByIdResponse.newBuilder()
                        .setCustomer(customerMapper.fromCustomer(customerEntity))
                        .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveCustomer(CustomerServiceOuterClass.SaveCustomerRequest request, StreamObserver<CustomerServiceOuterClass.SaveCustomerResponse> responseObserver) {
        CustomerServiceOuterClass.CustomerRequest customerRequest = request.getCustomer();
        Customer customer= customerMapper.fromGrpcCustomerRequest(customerRequest);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerServiceOuterClass.SaveCustomerResponse response=
                CustomerServiceOuterClass.SaveCustomerResponse.newBuilder()
                        .setCustomer(customerMapper.fromCustomer(savedCustomer))
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
