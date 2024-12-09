package com.example.tacocloud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = {
        "com.example.tacocloudmessagingjms.services",
        "com.example.tacocloudmessagingrabbitmq.services",
        "com.example.tacocloudmessagingrabbitmq.configuration",
        "com.example.tacocloudkafka.configuration",
        "com.example.tacocloudkafka.services",
        "com.example.tacocloudemailintegration.services"
})
public class BeansConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
