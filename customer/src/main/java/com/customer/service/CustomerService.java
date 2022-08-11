package com.customer.service;

import com.customer.dto.CustomerRegistrationRequest;
import com.customer.entity.Customer;
import com.customer.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import net.microservices.clients.fraud.FraudCheckResponse;
import net.microservices.clients.fraud.FraudClient;
import net.microservices.clients.notification.NotificationClient;
import net.microservices.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    @Transactional
    public Customer registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi, %s, welcome to Microservices", customer.getFirstName())
                )
        );

        return customer;
    }
}
