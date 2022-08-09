package com.fraud.service;

import com.fraud.entity.FraudCheckHistory;
import com.fraud.repo.FraudCheckHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Slf4j
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    @Transactional
    public boolean isFraudulentCustomer(Integer customerId) {
        FraudCheckHistory res = FraudCheckHistory.builder()
                .customerId(customerId)
                .isFraudster(false)
                .createdAt(LocalDateTime.now())
                .build();
        fraudCheckHistoryRepository.saveAndFlush(res);
        log.info("save res {}", res);
        return false;
    }
}
