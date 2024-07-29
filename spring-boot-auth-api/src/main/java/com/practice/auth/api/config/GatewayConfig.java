package com.practice.auth.api.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRoute(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("currency-exchange",
                        route -> route.path("/ces/currency-exchange/**")
                                .uri("http://localhost:8000"))
                .route("currency-conversion",
                        route -> route.path("/ces/currency-conversion/**")
                                .uri("http://localhost:8100"))
                .build();
    }
}
