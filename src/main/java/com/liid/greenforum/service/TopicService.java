package com.liid.greenforum.service;

import com.liid.greenforum.model.SortingValues;
import com.liid.greenforum.model.message.CreateMessageRequest;
import com.liid.greenforum.model.message.EditMessageRequest;
import com.liid.greenforum.model.message.MessagePaginationDTO;
import com.liid.greenforum.model.message.SortingValuesMessage;
import com.liid.greenforum.model.topic.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface TopicService {

    TopicMessagesDTO createMessage(UUID topicId, @Valid CreateMessageRequest request, Authentication authentication);
    TopicMessagesDTO editMessage(UUID topicId, @Valid EditMessageRequest request);
    void deleteMessage(UUID topicId, UUID messageId);

    TopicMessagePaginationDTO getTopicsMessages(UUID topicId, SortingValuesMessage sorting, @Valid @Min(1) int pageSize, @Valid @Min(0) int page);
    TopicsPaginationDTO getTopics(SortingValues sorting, @Valid @Min(1) int pageSize, @Valid @Min(0) int page);
    TopicMessagesDTO create(@Valid CreateTopicRequest request, Authentication authentication);
    TopicMessagesDTO edit(@Valid EditTopicRequest request, UUID topicId);
    void delete(UUID topicId);
}
