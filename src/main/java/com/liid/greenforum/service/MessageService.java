package com.liid.greenforum.service;

import com.liid.greenforum.model.message.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface MessageService {
//    MessagePaginationDTO getMessages(UUID topicId, SortingValuesMessage sorting, @Valid @Min(1) int pageSize, @Valid @Min(1) int page);
    MessagePaginationDTO getMessages(UUID topicId, SortingValuesMessage sorting, @Valid @Min(1) int pageSize, @Valid @Min(0) int page);
    MessageDTO create(UUID topicId, @Valid CreateMessageRequest request, Authentication authentication);
    void edit(@Valid EditMessageRequest request);
    void delete(UUID messageId);
}
