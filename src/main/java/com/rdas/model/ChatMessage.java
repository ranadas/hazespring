package com.rdas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatMessage implements Serializable {
    private final String messageUid;
    private final String recipient;
    private final String sender;
    private final String text;

    public ChatMessage(String messageUid, String sender, String recipient, String text) {
        this.messageUid = messageUid;
        this.recipient = recipient;
        this.sender = sender;
        this.text = text;
    }
}
