package com.liid.greenforum.model.message;

import com.liid.greenforum.model.Pagination;
import com.liid.greenforum.model.topic.TopicDTO;

import java.util.List;

public record MessagePaginationDTO(
        List<MessageDTO> messages,
        Pagination pagination
) {
}
