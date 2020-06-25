package com.delicious.easy_deliver.gateway.security.filter;

//@Component
//public class TokenFilter{
//
//}

import com.delicious.easy_deliver.gateway.security.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

/**
 * 过滤没有token的请求，或者过时的、非法的token
 */
@Slf4j
public class TokenFilter implements GatewayFilter, Ordered {

    private final ReactiveStringRedisTemplate stringRedisTemplate;
    private final ReactiveRedisTemplate<Object, Object> redisTemplate;

    public TokenFilter(ReactiveStringRedisTemplate stringRedisTemplate, ReactiveRedisTemplate<Object, Object> redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("enter login filter...");
        ServerHttpRequest request = exchange.getRequest();
        if(Objects.isNull(request.getHeaders()) || Objects.isNull(request.getHeaders().get(SecurityConstants.TOKEN_HEADER))){
            return unauthorized(exchange);
        }
        String token = request.getHeaders().get(SecurityConstants.TOKEN_HEADER).get(0);
        if (StringUtils.isEmpty(token) || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return unauthorized(exchange);
        }
        System.out.println("token: "+token);
        String userId = "1";
        // modified mark
        Boolean modified = (Boolean) redisTemplate.opsForValue().get(userId).block();
        if (modified) {
            // 重新登陆，获得新的权限
            redisTemplate.delete(userId).subscribe(i -> log.info("delete modified mark ok!"));
            // 将旧的token存入黑名单，过期时间和新生成的token一样
            redisTemplate.opsForValue().set(token, true, Duration.ofSeconds(SecurityConstants.EXPIRATION_SECOND));
            return unauthorized(exchange);
        } else if ((Boolean) redisTemplate.opsForValue().get(token).block()) {
            // 是旧的非法的token
            log.info("old token!");
            return unauthorized(exchange);
        } else {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void>  unauthorized(ServerWebExchange exchange){
        log.warn("no token or invalid token.");
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}