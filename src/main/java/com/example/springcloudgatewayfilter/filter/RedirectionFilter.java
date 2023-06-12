package com.example.springcloudgatewayfilter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Component
@Slf4j
public class RedirectionFilter extends AbstractGatewayFilterFactory<RedirectionFilter.RedirectionFilterConfig> {

    public RedirectionFilter() {
        super(RedirectionFilterConfig.class);
    }

    @Override
    public GatewayFilter apply(RedirectionFilterConfig config) {
        return (exchange, chain) -> {

            log.info("Inside RedirectionFilter....");
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            var path = serverHttpRequest.getPath().value();
            URI redirectURI = null;
            try {
                redirectURI = buildURI(path);
            } catch (URISyntaxException e) {
                log.info("URI Syntax exception");
                log.error(e.getMessage());
            }

            ServerHttpRequest modifiedRequest = exchange
                    .getRequest()
                    .mutate()
                    .uri(redirectURI)
                    .build();

            ServerWebExchange modifiedExchange = exchange
                    .mutate()
                    .request(modifiedRequest)
                    .build();

            modifiedExchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, redirectURI);

            return chain.filter(modifiedExchange);
        };
    }

    private URI buildURI(String path) throws URISyntaxException {
        return new URI("http://localhost:8080" + path);
    }

    public static class RedirectionFilterConfig {

    }
}
