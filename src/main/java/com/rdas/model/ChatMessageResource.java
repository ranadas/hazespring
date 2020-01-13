package com.rdas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;
@NoArgsConstructor
@AllArgsConstructor
@Data
//@FieldDefaults(level = PRIVATE, makeFinal = true)
@FieldDefaults(level = PRIVATE)
public class ChatMessageResource {
    @JsonProperty("recipient")
    String recipient;
    @JsonProperty("sender")
    String sender;
    @JsonProperty("text")
    String text;
}
