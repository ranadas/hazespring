package com.rdas.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {
    private static final int RECEICED_MESSAGES_TRACK_TTL_SECS = 60 * 60;

    @Bean(destroyMethod = "shutdown")
    public HazelcastInstance hazelcast(Config config) {
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    Config config(ApplicationContext applicationContext, NetworkConfig networkConfig) {
        final Config config = new Config()
                .addMapConfig(
                        new MapConfig()
                                .setName("accepted-messages")
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(RECEICED_MESSAGES_TRACK_TTL_SECS)
                );
        config.setNetworkConfig(networkConfig);
        config.getGroupConfig().setName(applicationContext.getId());
        return config;
    }

    @Bean
    NetworkConfig networkConfig(@Value("${hazelcast.port:5701}") int port
                                //, JoinConfig joinConfig
    ) {
        final NetworkConfig networkConfig = new NetworkConfig();
        //networkConfig.setJoin(joinConfig);
        networkConfig.setPort(port);
        return networkConfig;
    }

//    @Bean
//    JoinConfig joinConfig(TcpIpConfig tcpIpConfig) {
//        final JoinConfig joinConfig = disabledMulticast();
//        joinConfig.setTcpIpConfig(tcpIpConfig);
//        return joinConfig;
//    }

//    private JoinConfig disabledMulticast() {
//        JoinConfig join = new JoinConfig();
//        final MulticastConfig multicastConfig = new MulticastConfig();
//        multicastConfig.setEnabled(false);
//        join.setMulticastConfig(multicastConfig);
//        return join;
//    }

//    @Bean
//    TcpIpConfig tcpIpConfig(ApplicationContext applicationContext,
//                            ServiceDiscovery<Void> serviceDiscovery) throws Exception {
//        final TcpIpConfig tcpIpConfig = new TcpIpConfig();
//        final List<String> instances = queryOtherInstancesInZk(applicationContext.getId(), serviceDiscovery);
//        tcpIpConfig.setMembers(instances);
//        tcpIpConfig.setEnabled(true);
//        return tcpIpConfig;
//    }
//
//    private List<String> queryOtherInstancesInZk(String name, ServiceDiscovery<Void> serviceDiscovery) throws Exception {
//        return serviceDiscovery
//                .queryForInstances(name)
//                .stream()
//                .map(ServiceInstance::buildUriSpec)
//                .collect(Collectors.toList());
//    }
//    @Bean
//    public Config config() {
//        return new Config().addMapConfig(
//                // Set up TTL for the Map tracking received Messages IDs
//                new MapConfig()
//                        .setName(ChatServiceHazelcastImpl.ACCEPTED_MESSAGES_TRACKING_MAP_NAME)
//                        .setEvictionPolicy(EvictionPolicy.LRU)
//                        .setTimeToLiveSeconds(RECEICED_MESSAGES_TRACK_TTL_SECS));
//    }

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
