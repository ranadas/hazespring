package com.rdas.model;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ChatMessageResource {
    String recipient;
    String sender;
    String text;
}
