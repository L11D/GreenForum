package com.liid.greenforum.service.impl;

import com.liid.greenforum.entity.MessageEntity;
import com.liid.greenforum.entity.TopicEntity;
import com.liid.greenforum.entity.UserEntity;
import com.liid.greenforum.exception.NotFoundException;
import com.liid.greenforum.model.Pagination;
import com.liid.greenforum.model.message.*;
import com.liid.greenforum.repository.MessageRepository;
import com.liid.greenforum.repository.TopicRepository;
import com.liid.greenforum.service.MessageService;
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
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final TopicRepository topicRepository;
    private final UserService userService;
    private final JwtTokenUtils tokenUtils;

    public MessagePaginationDTO getMessages(UUID topicId, SortingValuesMessage sorting, @Valid @Min(1) int pageSize, @Valid @Min(0) int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "creationDate");
        switch (sorting){
            case CREATION_DATE_ASC -> {
                sort = Sort.by(Sort.Direction.ASC, "creationDate");
            }
            case CREATION_DATE_DESC -> {
                sort = Sort.by(Sort.Direction.DESC, "creationDate");
            }
        }

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<MessageEntity> messagePage = messageRepository.findByTopicId(topicId, pageable);

        List<MessageDTO> messages = convertToDTO(messagePage.stream().toList());

        return new MessagePaginationDTO(messages, new Pagination(
                messagePage.getSize(),
                messagePage.getTotalPages(),
                messagePage.getNumber()
        ));
    }

    private List<MessageDTO> convertToDTO(List<MessageEntity> entities){
        List<MessageDTO> messages = new ArrayList<>();
        for (MessageEntity m : entities){
            messages.add(new MessageDTO(
                    m.getId(),
                    m.getText(),
                    m.getParentTopic().getId(),
                    m.getUserCreatorId(),
                    m.getUserCreatorNickname(),
                    m.getCreationDate()
            ));
        }
        return messages;
    }


    @SneakyThrows
    public MessageDTO create(UUID topicId, @Valid CreateMessageRequest request, Authentication authentication) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        UserEntity user = userService.findUserById(userId);

        TopicEntity parentTopic = topicRepository.findById(topicId).orElseThrow(
                ()-> new NotFoundException("Topic not found")
        );

        MessageEntity message = MessageEntity.of(
                null,
                userId,
                user.getNickname(),
                LocalDateTime.now(),
                request.text(),
                parentTopic);

        MessageEntity createdMessage = messageRepository.save(message);
        return new MessageDTO(
                createdMessage.getId(),
                createdMessage.getText(),
                createdMessage.getParentTopic().getId(),
                createdMessage.getUserCreatorId(),
                createdMessage.getUserCreatorNickname(),
                createdMessage.getCreationDate()
        );
    }

    @SneakyThrows
    public void edit(@Valid EditMessageRequest request) {
        MessageEntity message = messageRepository.findById(request.id()).orElseThrow(() ->
                new NotFoundException("Message not found"));

        message.setText(request.text());
        messageRepository.save(message);
    }

    @SneakyThrows
    public void delete(UUID messageId) {
        MessageEntity message = messageRepository.findById(messageId).orElseThrow(() ->
                new NotFoundException("Message not found"));
        messageRepository.delete(message);
    }
}
