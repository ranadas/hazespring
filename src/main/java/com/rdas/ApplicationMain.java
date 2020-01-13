package com.rdas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApplicationMain {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationMain.class, args);

        /*
        log.info("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        Arrays.asList(beanNames).stream().forEach(s -> System.out.println(s));
        */
    }
}
