package com.rdas.controller;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.rdas.ApplicationMain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by rdas on 18/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class)
@WebIntegrationTest("server.port=0") // Use a random free port
@DirtiesContext
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
}