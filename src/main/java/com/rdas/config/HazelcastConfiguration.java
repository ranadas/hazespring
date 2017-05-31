package com.rdas.config;


import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.rdas.service.ChatServiceHazelcastImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rdas on 18/03/2016.
 */
@Configuration
public class HazelcastConfiguration {
    private static final int RECEICED_MESSAGES_TRACK_TTL_SECS = 60 * 60;

    /**
     * Spring boot automatically starts a Hazelcast instance when it finds both
     * 1. Hazelcast in classpath and
     * 2. com.hazelcast.config.Config bean.
     * <p>
     * On Startup, an instance of com.hazelcast.core.HazelcastInstance is added to Spring Application instance.
     */
    @Bean
    public Config config() {
        return new Config().addMapConfig(
                // Set up TTL for the Map tracking received Messages IDs
                new MapConfig()
                        .setName(ChatServiceHazelcastImpl.ACCEPTED_MESSAGES_TRACKING_MAP_NAME)
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(RECEICED_MESSAGES_TRACK_TTL_SECS));
    }

    /*
    private static final int RECEICED_MESSAGES_TRACK_TTL_SECS = 60 * 60;

    // When Spring Boot find a com.hazelcast.config.Config automatically instantiate a HazelcastInstance
    @Bean
    public Config config() {
        return new Config().addMapConfig(
                // Set up TTL for the Map tracking received Messages IDs
                new MapConfig()
                        .setName(ChatServiceHazelcastImpl.ACCEPTED_MESSAGES_TRACKING_MAP_NAME)
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(RECEICED_MESSAGES_TRACK_TTL_SECS));

    }

        return new Config().addMapConfig(
                new MapConfig()
                    .setName("accepted-messages")
                    .setEvictionPolicy(EvictionPolicy.LRU)
                    .setTimeToLiveSeconds(2400))
                    .setProperty("hazelcast.logging.type","slf4j");
     */
}
