package com.liid.greenforum.model.topic;

import com.liid.greenforum.model.Pagination;

import java.util.List;

public record TopicsPaginationDTO(
        List<TopicDTO> topics,
        Pagination pagination
) {
}