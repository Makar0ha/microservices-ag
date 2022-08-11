package com.fraud.controller;

import com.fraud.service.FraudCheckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.microservices.clients.fraud.FraudCheckResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/fraud-check")
@Slf4j
public class FraudController {

    private final FraudCheckService fraudCheckService;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(
            @PathVariable("customerId") Integer customerId) {
        boolean isFraudulentCustomer = fraudCheckService
                .isFraudulentCustomer(customerId);
        log.info("fraud check for customer {}", customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
