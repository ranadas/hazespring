package com.rdas.config;


import com.hazelcast.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rdas on 18/03/2016.
 */
@Configuration
public class HazelcastConfiguration {

    /**
     * Spring boot automatically starts a Hazelcast instance when it finds both
     * 1. Hazelcast in classpath and
     * 2. com.hazelcast.config.Config bean.
     * <p>
     * On Startup, an instance of com.hazelcast.core.HazelcastInstance is added to Spring Application instance.
     */
    @Bean
    public Config config() {
        return new Config();
    }
}
