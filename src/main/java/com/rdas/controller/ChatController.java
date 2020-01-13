package com.rdas.controller;

import com.rdas.model.ChatMessage;
import com.rdas.model.ChatMessageResource;
import com.rdas.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by rdas on 18/03/2016.
 * <p>
 * Messages are held in (distributed) Queues before being polled by recipients.
 * <p>
 * To make the send operation idempotent (dropping duplicated messages), received messages IDs are stored in a collection.
 * This collection has a TTL, not to keep them forever.
 * Note that I had to use a (distributed) Map and not a Set, as only Maps supports TTL at the moment.
 * <p>
 * Note that this application has many limitations. For example:
 * <p>
 * Queues have no TTL and no persistence: They might ‘explode’ if a recipient never polls, and will be lost if all nodes restart.
 * <p>
 * The recipient polling is not transactional: If any error occurs during the operation, messages are lost.
 */
@RestController
@RequestMapping("/messages")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public void send(@RequestBody ChatMessageResource messageResource) {
        chatService.send(mesgResourceToMesg.apply(messageResource));
    }

    @PostMapping(value = "/{receiver}")
    public List<ChatMessageResource> receive(@PathVariable("receiver") String receiver) {
        return chatService.receive(receiver).stream().map(mesgToMesgResource).collect(Collectors.toList());
    }

    Function<ChatMessage, ChatMessageResource> mesgToMesgResource = t ->
            new ChatMessageResource(t.getMessageUid(), t.getRecipient(), t.getSender(), t.getText());

    Function<ChatMessageResource, ChatMessage> mesgResourceToMesg = t ->
            new ChatMessage(t.getMessageUid(), t.getRecipient(), t.getSender(), t.getText());
    ;
}
