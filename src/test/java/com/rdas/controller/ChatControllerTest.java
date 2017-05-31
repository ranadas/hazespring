package com.rdas.controller;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.rdas.ApplicationMain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.WebIntegrationTest;
//import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * https://github.com/nicusX/springboot-hazelcast-example/blob/master/src/test/java/com/opencredo/examples/hzchat/api/ChatControllerTest.java
 * Created by rdas on 18/03/2016.
 * <p>
 * https://opencredo.com/spring-booting-hazelcast/
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = ApplicationMain.class)
//@WebIntegrationTest("server.port=0") // Use a random free port
//@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMain.class)
public class ChatControllerTest {

    // THIS IS A BAD TEST:
    // All the methods reuses the same Hazelcast instance

    // In Spring tests, when the Hazelcast instance is managed by Spring Boot
    // you SHOULD NOT call Hazelcast.shutdownAll() in the @After method
    // See: https://github.com/hazelcast/hazelcast/issues/6339
    // The problem is that this way the HazelcastInstance DOES NOT SHUTDOWN
    // so tests are not independent


    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testSend() {
        final String chatMessageJson = "{" +
                "\"messageUid\" : \"A1234567\", " +
                "\"sender\" : \"aSender\", " +
                "\"recipient\" : \"aRecipient\", " +
                "\"text\" : \"this is the text\" " +
                "}";

        given().contentType(ContentType.JSON).body(chatMessageJson).
                when().post("/messages").
                then().assertThat().statusCode(200);
    }

    @Test
    public void testSendThenReceive() throws Exception {
        final String chatMessageJson = "{" +
                "\"messageUid\" : \"B1234567\", " +
                "\"sender\" : \"aSender\", " +
                "\"recipient\" : \"bRecipient\", " +
                "\"text\" : \"this is the text\" " +
                "}";

        given().contentType(ContentType.JSON).body(chatMessageJson).
                when().post("/messages").
                then().assertThat().statusCode(200);

        Thread.sleep(5000);

        when().get("/messages/bRecipient").
                then().assertThat().statusCode(200).
                and().contentType(ContentType.JSON).
                and().body("size()", equalTo(1)).
                and().body("[0].messageUid", equalTo("B1234567")).
                and().body("[0].sender", equalTo("aSender")).
                and().body("[0].recipient", equalTo("bRecipient")).
                and().body("[0].text", equalTo("this is the text"));
    }

    @Test
    public void testReceiveNoMessage() {
        when().get("/messages/cRecipient").
                then().assertThat().statusCode(200).
                and().contentType(ContentType.JSON).
                and().body("size()", equalTo(0));
    }

    @Test
    public void testDiscardDuplicateMessages() {
        final String chatMessageJson = "{" +
                "\"messageUid\" : \"C1234567\", " +
                "\"sender\" : \"aSender\", " +
                "\"recipient\" : \"dRecipient\", " +
                "\"text\" : \"this is the text\" " +
                "}";

        given().contentType(ContentType.JSON).body(chatMessageJson).
                when().post("/messages");
        given().contentType(ContentType.JSON).body(chatMessageJson).
                when().post("/messages");

        when().get("/messages/dRecipient").
                then().assertThat().statusCode(200).
                and().contentType(ContentType.JSON).
                and().body("size()", equalTo(1));
    }
}