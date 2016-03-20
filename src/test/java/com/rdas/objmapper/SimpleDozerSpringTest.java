package com.rdas.objmapper;

import com.rdas.config.TestConfig;
import com.rdas.model.ChatMessage;
import com.rdas.model.ChatMessageResource;
import lombok.extern.slf4j.Slf4j;
import org.dozer.MappingException;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by rdas on 19/03/2016.
 */
@Slf4j
@ContextConfiguration(classes = {TestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleDozerSpringTest {

    @Autowired
    private DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean;

    @Test
    public void chatMessage2chatMessageResourceMapping() throws Exception {
        org.dozer.Mapper mapper = dozerBeanMapperFactoryBean.getObject();

        ChatMessage chatMessage = new ChatMessage(
                "XXMessageUid",
                "RDAS",
                "JEN",
                "HELLO");

        ChatMessageResource chatMessageResource = new ChatMessageResource();
        mapper.map(chatMessage, chatMessageResource, "chat");

        log.debug(chatMessageResource.toString());

        assertThat(chatMessage.getMessageUid()).containsIgnoringCase(chatMessageResource.getMessageUid());
        assertThat(chatMessage.getRecipient()).containsIgnoringCase(chatMessageResource.getRecipient());
        assertThat(chatMessage.getSender()).containsIgnoringCase(chatMessageResource.getSender());
        assertThat(chatMessage.getText()).containsIgnoringCase(chatMessageResource.getText());
    }

    // This won't work because ChatMessage fields are final.
    @Test(expected = MappingException.class)
    public void chatMessageResource2chatMessageMapping() throws Exception {
        org.dozer.Mapper mapper = dozerBeanMapperFactoryBean.getObject();

        final ChatMessageResource messageResource = new ChatMessageResource();
        messageResource.setMessageUid("XXMessageUid");
        messageResource.setSender("RDAS");
        messageResource.setRecipient("Jennifer");
        messageResource.setText("Hello World");


        ChatMessage chatMessage = new ChatMessage();
        mapper.map(messageResource, chatMessage, "chat");
    }
}
