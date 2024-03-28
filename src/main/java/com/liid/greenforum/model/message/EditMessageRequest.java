package com.liid.greenforum.model.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EditMessageRequest(
        @NotBlank(message = "Message text cannot be empty")
        @Size(min = 1, max = 10000, message = "Message text size must be between 1 and 10000 characters")
        String text,
        UUID id
) {
}
