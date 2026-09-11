package io.github.avocoders.notificationservice.kafka;

import io.github.avocoders.notificationservice.event.UserEvent;
import io.github.avocoders.notificationservice.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {
    private final EmailNotificationService emailNotificationService;

    @KafkaListener(topics = "user-events")
    public void listen(UserEvent userEvent) {
        String subject;
        String body;

        switch (userEvent.operation()) {
            case CREATED -> {
                subject = "Ваша учетная запись создана";
                body = "Создан пользователь " + userEvent.userId();
            }
            case DELETED -> {
                subject = "Ваша учетная запись удалена";
                body = "Удален пользователь " + userEvent.userId();
            }
            default -> throw new IllegalStateException(
                    "Неизвестная операция: " + userEvent.operation()
            );
        }
        log.info("Получено событие из Kafka: {}", userEvent);

        try {
            emailNotificationService.sendEmail(
                    userEvent.email(),
                    subject,
                    body
            );

            log.info("Письмо отправлено пользователю: {}", userEvent.email());

        } catch (Exception e) {

            log.error("Ошибка отправки письма пользователю: {}", userEvent.email(), e);
            throw e;
        }

    }

}
