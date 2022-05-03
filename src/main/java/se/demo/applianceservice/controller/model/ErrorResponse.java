package se.demo.applianceservice.controller.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(String error,
                            String message,
                            LocalDateTime timestamp) {
}
