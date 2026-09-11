package io.github.avocoders.userservicespring.event;

public record UserEvent(
        UserOperation operation,
        Long userId,
        String name,
        String email,
        Integer age
) {
}
