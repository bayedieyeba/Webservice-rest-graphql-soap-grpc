package com.baye.exempleaccountservice.web;

import com.baye.exempleaccountservice.feign.CustomerRestClient;
import com.baye.exempleaccountservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/account-service")
public class AccountRestController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CustomerRestClient customerRestClient;

    @GetMapping("/customers")
    public List<Customer> listCustomers(){
        Customer[] customers=  restTemplate.getForObject("http://localhost:8082/customers", Customer[].class);

        return List.of(customers);
    }

    @GetMapping("/customer/{id}")
    public Customer customerById(@PathVariable Long id){
        Customer customer=  restTemplate.getForObject("http://localhost:8082/customers/"+id, Customer.class);
        return customer;
    }
    @GetMapping("/customers/v2")
    public Flux<Customer> listCustomersV2(){
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();

       Flux<Customer> customerFlux= webClient.get()
                .uri("/customers")
                .retrieve().bodyToFlux(Customer.class);
        return customerFlux;
    }
    @GetMapping("/customer/v2/{id}")
    public Mono<Customer> customerByIdV2(@PathVariable Long id){
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();

        Mono<Customer> customerMono= webClient.get()
                .uri("/customers/{id}",id)
                .retrieve().bodyToMono(Customer.class);
        return customerMono;
    }

    @GetMapping("/customers/v3")
    public List<Customer> listCustomersV3(){
        return customerRestClient.getCustomers();
    }

    @GetMapping("/customer/v3/{id}")
    public Customer customerByIdV3(@PathVariable Long id){

        return customerRestClient.getCustomerById(id);
    }

    @GetMapping("/gql/customers")
    public Mono<List<Customer>> customerByIdGql(){
        HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder()
                .url("http://localhost:8082/graphql")
                .build();
        var httpRequestDecument = """
                query{
                  allCustomers{
                    id, name, email
                  }
                }
                """;
        Mono<List<Customer>> customers =graphQlClient.document(httpRequestDecument)
                .retrieve("allCustomers")
                .toEntityList(Customer.class);

        return customers;
    }

    @GetMapping("/gql/customers/{id}")
    public Mono<Customer> customerByIdGql(@PathVariable Long id){
        HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder()
                .url("http://localhost:8082/graphql")
                .build();
        var httpRequestDecument = """
                query($id:Int){
                    customerById(id:$id){
                        id, name , email
                    }
                }
                """;
        Mono<Customer> customerById =graphQlClient.document(httpRequestDecument)
                .variable("id",id)
                .retrieve("customerById")
                .toEntity(Customer.class);

        return customerById;
    }

}