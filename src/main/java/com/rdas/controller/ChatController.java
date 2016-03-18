package com.rdas.controller;

import com.rdas.model.ChatMessage;
import com.rdas.model.ChatMessageResource;
import com.rdas.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rdas on 18/03/2016.
 */
@Slf4j
@RestController
@RequestMapping("/messages")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping(method = RequestMethod.POST)
    public void send(@RequestBody ChatMessageResource messageResource) {
        // TODO Add validation and error handling
        chatService.send(map(messageResource));
    }

    @RequestMapping(value = "/{receiver}", method = RequestMethod.GET)
    public List<ChatMessageResource> receive(@PathVariable("receiver") String receiver) {
        return chatService.receive(receiver).stream().map( ChatController::map ).collect(Collectors.toList());
    }

    private static ChatMessage map(ChatMessageResource messageResource) {
        return new ChatMessage(
                messageResource.getMessageUid(),
                messageResource.getSender(),
                messageResource.getRecipient(),
                messageResource.getText());
    }

    private static ChatMessageResource map(ChatMessage message) {
        final ChatMessageResource messageResource = new ChatMessageResource();
        messageResource.setMessageUid(message.getMessageUid());
        messageResource.setSender(message.getSender());
        messageResource.setRecipient(message.getRecipient());
        messageResource.setText(message.getText());
        return messageResource;
    }
}
