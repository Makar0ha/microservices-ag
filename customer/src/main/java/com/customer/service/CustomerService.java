package com.customer.service;

import com.customer.dto.CustomerRegistrationRequest;
import com.customer.entity.Customer;
import com.customer.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import net.microservices.clients.fraud.FraudCheckResponse;
import net.microservices.clients.fraud.FraudClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;

    @Transactional
    public Customer registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
//                restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );


        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        return customer;
    }
}