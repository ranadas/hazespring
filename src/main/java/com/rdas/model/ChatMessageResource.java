package com.rdas.model;

import lombok.Data;

/**
 * Created by rdas on 18/03/2016.
 */
@Data
public class ChatMessageResource {
    private String messageUid;
    private String recipient;
    private String sender;
    private String text;
}
