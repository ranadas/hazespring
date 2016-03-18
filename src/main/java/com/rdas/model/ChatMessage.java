package com.rdas.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by rdas on 18/03/2016.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ChatMessage  implements Serializable {
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
