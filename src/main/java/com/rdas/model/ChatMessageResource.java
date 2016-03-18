package com.rdas.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by rdas on 18/03/2016.
 */
@Getter
@Setter
@ToString
public class ChatMessageResource {
    private String messageUid;
    private String recipient;
    private String sender;
    private String text;
}
