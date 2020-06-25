package com.delicious.easy_deliver.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;


public class MQConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return new JsonMapper();
    }
}
