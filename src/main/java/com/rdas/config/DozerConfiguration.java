package com.rdas.config;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * Created by rdas on 19/03/2016.
 */
@Configuration
//@ComponentScan(basePackages = {"com.rdas"})
public class DozerConfiguration {
    @Bean
    public DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean() throws IOException {
        DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:dozer-bean-mappings.xml");
        mapper.setMappingFiles(resources);
        return mapper;
    }
}
