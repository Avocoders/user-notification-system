package io.github.avocoders.notificationservice.kafka;

import io.github.avocoders.notificationservice.event.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    @KafkaListener(topics = "user-events")
    public void listen(UserEvent userEvent) {
        System.out.println(userEvent);
    }
}
