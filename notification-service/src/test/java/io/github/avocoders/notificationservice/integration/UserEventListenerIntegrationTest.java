package io.github.avocoders.notificationservice.integration;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = "user-events",
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
public class UserEventListenerIntegrationTest {
    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GreenMailExtension(
            ServerSetupTest.SMTP
    );

    @DynamicPropertySource
    static void mailProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", () -> "localhost");
        registry.add("spring.mail.port", ServerSetupTest.SMTP::getPort);
    }



}
