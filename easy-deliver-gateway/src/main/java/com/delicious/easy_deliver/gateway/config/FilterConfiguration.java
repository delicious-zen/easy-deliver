package com.delicious.easy_deliver.gateway.config;

import com.delicious.easy_deliver.gateway.security.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

@Configuration
public class FilterConfiguration {

    final ReactiveStringRedisTemplate stringRedisTemplate;
    final ReactiveRedisTemplate<Object,Object> redisTemplate;

    public FilterConfiguration(ReactiveStringRedisTemplate stringRedisTemplate, ReactiveRedisTemplate<Object, Object> redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public TokenFilter loginFilter(){
        return new TokenFilter(stringRedisTemplate, redisTemplate);
    }
}
