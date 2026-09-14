package io.github.avocoders.notificationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String subject,
        @NotBlank
        String body
) {
}
