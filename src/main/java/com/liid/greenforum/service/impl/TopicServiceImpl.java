package com.liid.greenforum.service.impl;

import com.liid.greenforum.entity.MessageEntity;
import com.liid.greenforum.entity.TopicEntity;
import com.liid.greenforum.entity.UserEntity;
import com.liid.greenforum.exception.NotFoundException;
import com.liid.greenforum.model.Pagination;
import com.liid.greenforum.model.SortingValues;
import com.liid.greenforum.model.message.*;
import com.liid.greenforum.model.topic.*;
import com.liid.greenforum.repository.MessageRepository;
import com.liid.greenforum.repository.TopicRepository;
import com.liid.greenforum.service.MessageService;
import com.liid.greenforum.service.TopicService;
import com.liid.greenforum.service.UserService;
import com.liid.greenforum.utils.JwtTokenUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final JwtTokenUtils tokenUtils;
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final MessageService messageService;

    @SneakyThrows
    public TopicMessagesDTO createMessage(UUID topicId, @Valid CreateMessageRequest request, Authentication authentication){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        UserEntity user = userService.findUserById(userId);

        TopicEntity topic = topicRepository.findById(topicId).orElseThrow(() ->
                new NotFoundException("Topic not found"));

        MessageEntity message = MessageEntity.of(
                null,
                userId,
                user.getNickname(),
                LocalDateTime.now(),
                request.text(),
                topic);
        messageRepository.save(message);

        return topicToDTO(topic, null);
    }
    @SneakyThrows
    public TopicMessagesDTO editMessage(UUID topicId, EditMessageRequest request) {
        TopicEntity topic = topicRepository.findById(topicId).orElseThrow(() ->
                new NotFoundException("Topic not found"));
        if (!topicRepository.containMessage(topicId, request.id())){
            throw new NotFoundException("Message not found in topic");
        }
        messageService.edit(request);
        return topicToDTO(topic, null);
    }

    @SneakyThrows
    public void deleteMessage(UUID topicId, UUID messageId) {
        TopicEntity topic = topicRepository.findById(topicId).orElseThrow(() ->
                new NotFoundException("Topic not found"));
        if (!topicRepository.containMessage(topicId, messageId)){
            throw new NotFoundException("Message not found in topic");
        }
        messageService.delete(messageId);
        List<MessageEntity> messageEntities = messageRepository.findByTopicId(topicId);
        if (messageEntities.isEmpty()){
            delete(topicId);
        }
    }

    @SneakyThrows
    public TopicMessagePaginationDTO getTopicsMessages(UUID topicId, SortingValuesMessage sorting, @Valid @Min(1) int pageSize, @Valid @Min(0) int page){
        TopicEntity topic = topicRepository.findById(topicId).orElseThrow(() ->
                new NotFoundException("Topic not found"));

        MessagePaginationDTO messages = messageService.getMessages(topicId, sorting, pageSize, page);
        return new TopicMessagePaginationDTO(
                new TopicDTO(
                        topic.getId(),
                        topic.getName(),
                        topic.getUserCreatorId(),
                        topic.getUserCreatorNickname(),
                        topic.getCreationDate()
                        ),
                messages.messages(),
                messages.pagination()
        );
    }

    public TopicsPaginationDTO getTopics(SortingValues sorting, @Valid @Min(1) int pageSize, @Valid @Min(0) int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        switch (sorting){
            case NAME_ASC -> {
                sort = Sort.by(Sort.Direction.ASC, "name");
            }
            case NAME_DESC -> {
                sort = Sort.by(Sort.Direction.DESC, "name");
            }
            case CREATION_DATE_ASC -> {
                sort = Sort.by(Sort.Direction.ASC, "creationDate");
            }
            case CREATION_DATE_DESC -> {
                sort = Sort.by(Sort.Direction.DESC, "creationDate");
            }
        }

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<TopicEntity> topicsPage = topicRepository.findAll(pageable);
        List<TopicDTO> topics = new ArrayList<>();
        for (TopicEntity t : topicsPage.stream().toList()){
            topics.add(new TopicDTO(
                    t.getId(),
                    t.getName(),
                    t.getUserCreatorId(),
                    t.getUserCreatorNickname(),
                    t.getCreationDate()
            ));
        }
        return new TopicsPaginationDTO(topics, new Pagination(
                topicsPage.getSize(),
                topicsPage.getTotalPages(),
                topicsPage.getNumber()
        ));
    }

    @SneakyThrows
    public TopicMessagesDTO create(@Valid CreateTopicRequest request, Authentication authentication) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        UserEntity user = userService.findUserById(userId);

        TopicEntity topic = TopicEntity.of(
                null,
                userId,
                user.getNickname(),
                LocalDateTime.now(),
                request.name());
        TopicEntity createdTopic = topicRepository.save(topic);

        MessageDTO createdMessage = messageService.create(
                createdTopic.getId(),
                new CreateMessageRequest(request.message()),
                authentication);

        return topicToDTO(createdTopic, List.of(createdMessage));
    }

    @SneakyThrows
    public TopicMessagesDTO edit(EditTopicRequest request, UUID topicId) {
        TopicEntity topic = topicRepository.findById(topicId).orElseThrow(() ->
                new NotFoundException("Topic not found"));
        topic.setName(request.name());
        topicRepository.save(topic);
        return topicToDTO(topic, null);
    }

    @SneakyThrows
    public void delete(UUID topicId) {
        TopicEntity topic = topicRepository.findById(topicId).orElseThrow(() ->
                new NotFoundException("Topic not found"));

        List<MessageEntity> messageEntities = messageRepository.findByTopicId(topic.getId());
        for (MessageEntity e: messageEntities){
            messageService.delete(e.getId());
        }
        topicRepository.delete(topic);
    }

    private TopicMessagesDTO topicToDTO(TopicEntity topic, List<MessageDTO> messages){
        List<MessageDTO> messageDTOS = new ArrayList<>();
        if (messages == null){
            List<MessageEntity> messageEntities = messageRepository.findByTopicId(topic.getId());
            for (MessageEntity e: messageEntities){
                messageDTOS.add(new MessageDTO(
                        e.getId(),
                        e.getText(),
                        e.getParentTopic().getId(),
                        e.getUserCreatorId(),
                        e.getUserCreatorNickname(),
                        e.getCreationDate()
                ));
            }
        }
        else {
            messageDTOS = messages;
        }

        return new TopicMessagesDTO(
                topic.getId(),
                topic.getName(),
                topic.getUserCreatorId(),
                topic.getUserCreatorNickname(),
                topic.getCreationDate(),
                messageDTOS
        );
    }

}
