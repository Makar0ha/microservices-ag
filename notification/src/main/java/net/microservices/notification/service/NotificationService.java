package net.microservices.notification.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.microservices.clients.notification.NotificationRequest;
import net.microservices.notification.entity.Notification;
import net.microservices.notification.repo.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerName())
                        .sender("gavrilov")
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
