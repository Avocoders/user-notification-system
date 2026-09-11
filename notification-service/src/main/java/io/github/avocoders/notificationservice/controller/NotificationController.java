package io.github.avocoders.notificationservice.controller;

import io.github.avocoders.notificationservice.dto.EmailRequest;
import io.github.avocoders.notificationservice.service.EmailNotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final EmailNotificationService emailNotificationService;

    @PostMapping("/email")
    public void sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        emailNotificationService.sendEmail(emailRequest.email(), emailRequest.subject(), emailRequest.body());
    }
}
