package com.rdas.model;

import lombok.Data;
@Data
public class ChatMessageResource {
    private final String messageUid;
    private final String recipient;
    private final String sender;
    private final String text;
}
