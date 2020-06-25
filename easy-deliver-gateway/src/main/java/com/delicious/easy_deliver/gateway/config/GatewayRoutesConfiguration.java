package com.delicious.easy_deliver.gateway.config;

import com.delicious.easy_deliver.gateway.security.filter.TokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class GatewayRoutesConfiguration {

    final TokenFilter tokenFilter;

    public GatewayRoutesConfiguration(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    /**
     * java 配置 server 服务路由
     */
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        log.info("ServerGatewayFilter filter........");
//        return builder.routes()
//                .route(r ->
//                        r.path("/sample/**")
//                                .filters(
//                                        f -> f.stripPrefix(1)
//                                                .filter(tokenFilter)
//                                )
//                                // 通过filter后直接转发到百度
//                                .uri("https://www.baidu.com/")
//                )
//
//                .build();
//    }
}