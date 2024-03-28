package com.liid.greenforum.model.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateTopicRequest(
        @NotBlank(message = "Topic name cannot be empty")
        @Size(min = 3, max = 100, message = "Topic name size must be between 3 and 100 characters")
        String name,
        @NotBlank(message = "Message text cannot be empty")
        @Size(min = 1, max = 10000, message = "Message text size must be between 1 and 10000 characters")
        String message
) {
}