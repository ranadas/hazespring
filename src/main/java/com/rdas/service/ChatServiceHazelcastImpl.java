package com.rdas.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.rdas.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rdas on 18/03/2016.
 */
@Slf4j
@Service
public class ChatServiceHazelcastImpl implements ChatService {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public static final String ACCEPTED_MESSAGES_TRACKING_MAP_NAME = "received";
    public static final String RECIPIENT_QUEUE_NAME_SUFFIX = "recipient-";

    @Override
    public void send(ChatMessage message) {
// Check if the message is duplicate. If duplicate, silently ignore it
        if (!isDuplicate(message)) {
            log.debug("Submitting the message id:{}", message.getMessageUid());
            recipientQueue(message.getRecipient()).offer(message);

            // Save UID as accepted
            markAsAccepted(message);
        }
    }

    @Override
    public List<ChatMessage> receive(String recipient) {
        log.debug("Polling message for recipient: {}", recipient);

        // Poll recipient's queue until empty
        final List<ChatMessage> messages = new ArrayList();
        while ( true ) {
            final ChatMessage message = recipientQueue(recipient).poll();
            if ( message == null ) break;
            log.debug("Polled message id:{}", message.getMessageUid());
            messages.add(message);
        }
        log.debug("Returning {} messages", messages.size());

        // This is not a transactional receiver: If something happens here, the messages are lost...

        // Return the received messages
        return Collections.unmodifiableList(messages);
    }

    private boolean isDuplicate(ChatMessage message) {
        // We just store and check the message UID. A distributed Set would suffice, but unfortunately
        // Hazelcast ISet doesn't support automatic eviction
        final boolean duplicate = acceptedMessageUidMap().containsKey(message.getMessageUid());
        log.debug("Message id:{} is duplicate? {}", message.getMessageUid(), duplicate);
        return duplicate;
    }

    private void markAsAccepted(ChatMessage message) {
        log.debug("Marking message id:{} as accepted", message.getMessageUid());
        acceptedMessageUidMap().put(message.getMessageUid(),"");
    }

    // Starting the HazelcastInstance is heavyweight, while retrieving a distributed object from it is not
    private IQueue<ChatMessage> recipientQueue(String user) {
        return hazelcastInstance.getQueue(RECIPIENT_QUEUE_NAME_SUFFIX + user);
    }


    private IMap<Object, Object> acceptedMessageUidMap() {
        return hazelcastInstance.getMap(ACCEPTED_MESSAGES_TRACKING_MAP_NAME);
    }
}
