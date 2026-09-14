package io.github.avocoders.notificationservice.integration;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import io.github.avocoders.notificationservice.event.UserEvent;
import io.github.avocoders.notificationservice.event.UserOperation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.kafka.consumer.auto-offset-reset=earliest"})
@EmbeddedKafka(
        partitions = 1,
        topics = "user-events",
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
public class UserEventListenerIntegrationTest {
    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GreenMailExtension(
            ServerSetupTest.SMTP
    );

    @DynamicPropertySource
    static void mailProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", () -> "localhost");
        registry.add("spring.mail.port", ServerSetupTest.SMTP::getPort);
    }

    @Test
    void shouldSendEmailWhenUserCreated() throws MessagingException, IOException {
        UserEvent userEvent = new UserEvent(UserOperation.CREATED, 15L, "Nika", "nika@ya.ru", 30 );
        kafkaTemplate.send("user-events", userEvent.userId().toString(), userEvent);

        boolean received = greenMailExtension.waitForIncomingEmail(5000, 1);
        assertThat(received).isTrue();

        MimeMessage[] messages = greenMailExtension.getReceivedMessages();
        assertThat(messages).hasSize(1);

        MimeMessage message = messages[0];
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(userEvent.email());
        assertThat(message.getSubject()).isEqualTo("Ваша учетная запись создана");
        assertThat(message.getContent().toString()).contains("Создан пользователь " + userEvent.userId());
    }

    @Test
    void shouldSendEmailWhenUserDeleted() throws MessagingException, IOException {
        UserEvent userEvent = new UserEvent(UserOperation.DELETED, 15L, "Nika", "nika@ya.ru", 30 );
        kafkaTemplate.send("user-events", userEvent.userId().toString(), userEvent);

        boolean received = greenMailExtension.waitForIncomingEmail(5000, 1);
        assertThat(received).isTrue();

        MimeMessage[] messages = greenMailExtension.getReceivedMessages();
        assertThat(messages).hasSize(1);

        MimeMessage message = messages[0];
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(userEvent.email());
        assertThat(message.getSubject()).isEqualTo("Ваша учетная запись удалена");
        assertThat(message.getContent().toString()).contains("Удален пользователь " + userEvent.userId());

    }

}
