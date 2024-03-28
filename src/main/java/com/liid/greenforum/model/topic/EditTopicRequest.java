package com.liid.greenforum.model.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EditTopicRequest(
        @NotBlank(message = "Topic name cannot be empty")
        @Size(min = 3, max = 100, message = "Topic name size must be between 3 and 100 characters")
        String name
) {
}
