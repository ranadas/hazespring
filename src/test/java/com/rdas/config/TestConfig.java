package com.rdas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({HazelcastConfiguration.class})
public class TestConfig {

}
