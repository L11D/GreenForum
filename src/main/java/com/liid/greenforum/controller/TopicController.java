package com.liid.greenforum.controller;

import com.liid.greenforum.model.SortingValues;
import com.liid.greenforum.model.message.CreateMessageRequest;
import com.liid.greenforum.model.message.EditMessageRequest;
import com.liid.greenforum.model.message.MessagePaginationDTO;
import com.liid.greenforum.model.message.SortingValuesMessage;
import com.liid.greenforum.model.topic.*;
import com.liid.greenforum.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/topic")
public class TopicController {
    private final TopicService topicService;

    @GetMapping("{id}")
    @ResponseBody
    public TopicMessagePaginationDTO getMessages(
            @PathVariable("id") UUID topicId,
            @RequestParam(value = "sorting", defaultValue = "CREATION_DATE_DESC") SortingValuesMessage sorting,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "page", defaultValue = "0") int page){

        return topicService.getTopicsMessages(topicId, sorting, pageSize, page);
    }

    @GetMapping
    @ResponseBody
    public TopicsPaginationDTO getTopics(@RequestParam(value = "sorting", defaultValue = "CREATION_DATE_DESC") SortingValues sorting,
                                         @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                         @RequestParam(value = "page", defaultValue = "0") int page){

        return topicService.getTopics(sorting, pageSize, page);
    }



    @PostMapping("{id}/message")
    @ResponseBody
    public TopicMessagesDTO createMessage(@PathVariable("id") UUID topicId,
                              @RequestBody CreateMessageRequest request,
                              Authentication authentication){

        return topicService.createMessage(topicId, request, authentication);
    }

    @PutMapping("{id}/message")
    @ResponseBody
    public TopicMessagesDTO editMessage(@PathVariable UUID id,
                            @RequestBody EditMessageRequest request){
        return topicService.editMessage(id, request);
    }

    @DeleteMapping("{id}/message")
    @ResponseBody
    public void delete(@PathVariable UUID id,
                       @RequestParam(value = "messageId", required = true) UUID messageId
                       ){
        topicService.deleteMessage(id, messageId);
    }


    @PostMapping("create")
    @ResponseBody
    public TopicMessagesDTO create(@RequestBody CreateTopicRequest request, Authentication authentication){
        return topicService.create(request, authentication);
    }

    @PutMapping("{id}/edit")
    @ResponseBody
    public TopicMessagesDTO edit(@PathVariable UUID id,
                                 @RequestBody EditTopicRequest request){
        return topicService.edit(request, id);
    }

    @DeleteMapping("{id}/delete")
    @ResponseBody
    public void delete(@PathVariable UUID id){
        topicService.delete(id);
    }
}
