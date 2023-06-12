package com.example.springcloudgatewayfilter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.cloud.gateway")
@Getter
@Setter
public class ProxyConfig {

    private List<RouteDefinition> routes;
}
