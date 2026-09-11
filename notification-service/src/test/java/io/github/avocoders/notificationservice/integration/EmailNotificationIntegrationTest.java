package io.github.avocoders.notificationservice.integration;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import io.github.avocoders.notificationservice.service.EmailNotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

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
                "spring.mail.port", ServerSetupTest.SMTP::getPort
        );
    }

    @Test
    void shouldSendEmail() throws MessagingException, IOException {
        String to = "test@ya.ru";
        String subject = "Тестовая тема";
        String body = "Тестовое сообщение";

        emailNotificationService.sendEmail(to, subject, body);

        MimeMessage[] messages = greenMailExtension.getReceivedMessages();

        assertThat(messages).hasSize(1);

        MimeMessage message = messages[0];
        assertThat(message.getSubject()).isEqualTo(subject);
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(to);
        assertThat(message.getContent().toString()).contains(body);
    }

}
