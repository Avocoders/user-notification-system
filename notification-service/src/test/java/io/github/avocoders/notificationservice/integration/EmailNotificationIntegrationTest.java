package io.github.avocoders.notificationservice.integration;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import io.github.avocoders.notificationservice.service.EmailNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(properties = {"spring.kafka.listener.auto-startup=false"})
public class EmailNotificationIntegrationTest {
    @Autowired
    private EmailNotificationService emailNotificationService;

    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GreenMailExtension(
            ServerSetupTest.SMTP
    );

    @DynamicPropertySource
    static void mailProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", () -> "localhost");
        registry.add(
                "spring.mail.port", () -> greenMailExtension.getSmtp().getPort()
        );
    }

    @Test
    void shouldSendEmail() {
        String to = "test@ya.ru";
        String subject = "Тестовая тема";
        String body = "Тестовое сообщение";

        emailNotificationService.sendEmail(to, subject, body);
    }

}
