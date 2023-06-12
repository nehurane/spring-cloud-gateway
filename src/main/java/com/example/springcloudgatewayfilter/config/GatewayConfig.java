package com.example.springcloudgatewayfilter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        log.info("Loading Gateway Config");
        return routeLocatorBuilder
                .routes()
                .route("hello-service",
                        r -> r.path("hello-service/v1/greetings")//replace with env variables
                                .and().method("POST", "DELETE", "GET")
                                .and().host("localhost")//replace with env variables
                                .uri("http://localhost:8080"))//replace with env variables
                .route("localhost",
                        r -> r.path("/v1/something-else")//replace with env variables
                                .and().method("POST", "DELETE", "GET")
                                .and().host("localhost")//replace with env variables
                                .uri("http://localhost:8080"))//replace with env variables
                .build();
    }
}
