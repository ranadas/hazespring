package com.rdas.model;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ChatMessage implements Serializable {
    String messageUid;
    String recipient;
    String sender;
    String text;
}
