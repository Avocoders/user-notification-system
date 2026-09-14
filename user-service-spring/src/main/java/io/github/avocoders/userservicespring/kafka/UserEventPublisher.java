package io.github.avocoders.userservicespring.kafka;

import io.github.avocoders.userservicespring.event.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void publish(UserEvent userEvent) {
        kafkaTemplate.send("user-events", userEvent.userId().toString(), userEvent);
    }
}
