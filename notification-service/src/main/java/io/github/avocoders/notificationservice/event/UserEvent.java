package io.github.avocoders.notificationservice.event;

public record UserEvent(
        UserOperation operation,
        Long userId,
        String name,
        String email,
        Integer age
) {
}
