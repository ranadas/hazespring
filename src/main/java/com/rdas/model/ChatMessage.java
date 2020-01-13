package com.rdas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatMessage implements Serializable {
    private final String messageUid;
    private final String recipient;
    private final String sender;
    private final String text;
}
