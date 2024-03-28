package com.liid.greenforum.model.message;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageDTO(
        UUID id,
        String text,
        UUID topicId,
        UUID userCreatorId,
        String userCreatorNickname,
        LocalDateTime creationDate
) {
}
