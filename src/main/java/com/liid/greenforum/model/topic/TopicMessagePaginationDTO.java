package com.liid.greenforum.model.topic;

import com.liid.greenforum.model.Pagination;
import com.liid.greenforum.model.message.MessageDTO;

import java.util.List;

public record TopicMessagePaginationDTO(
        TopicDTO topic,
        List<MessageDTO> messages,
        Pagination pagination
) {
}
