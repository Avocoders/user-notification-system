package io.github.avocoders.userservicespring.event;

import org.junit.jupiter.api.Test;

import static io.github.avocoders.userservicespring.event.UserOperation.CREATED;
import static org.junit.jupiter.api.Assertions.*;

class UserEventTest {

    @Test
    void createdEvent() {
        Long id = 15L;
        String name = "Veronika";
        String email = "veronika@ya.ru";
        Integer age = 20;

        UserEvent event = new UserEvent(CREATED,id, name, email, age);
        assertEquals("veronika@ya.ru", event.email());
        assertEquals(CREATED, event.operation());
    }
}