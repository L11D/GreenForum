package com.liid.greenforum.model.topic;

import com.liid.greenforum.model.message.MessageDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TopicMessagesDTO(
        UUID id,
        String name,
        UUID userCreatorId,
        String userCreatorNickname,
        LocalDateTime creationDate,
        List<MessageDTO> message

) {
}
