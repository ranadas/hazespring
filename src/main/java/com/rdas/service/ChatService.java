package com.rdas.service;

import com.rdas.model.ChatMessage;

import java.util.List;

/**
 * Created by rdas on 18/03/2016.
 */
public interface ChatService {
    String send(ChatMessage message);

    List<ChatMessage> receive(String recipient);
}
