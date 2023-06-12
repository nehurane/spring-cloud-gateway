package com.example.springcloudgatewayfilter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;


@Component
@Slf4j
public class GlobalRequestPreFilter extends
        AbstractGatewayFilterFactory<GlobalRequestPreFilter.Config>  {

    public GlobalRequestPreFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("GatewayFilter being called ................. ");

            ServerHttpRequest request = exchange.getRequest();
            var path = request.getPath().toString();
            log.info("Original request path is " + path);

            var userToken = String.valueOf(Objects.requireNonNull(request.getHeaders().get("x-auth-token"),
                    "Token is missing"));
            if(!isTokenValid(userToken)) {
                //remove stale token
                request.getHeaders().remove("x-auth-token");
                //get refreshed token
                request.getHeaders().add("x-auth-token", "e58ed763-928c-4155-bee9-fdbaaadc15f3");
                //mutate request
                return chain.filter(exchange.mutate().request(request).build());
            }

            return chain.filter(exchange);
        };
    }

    static class Config {}

    private URI buildURI(String path) throws URISyntaxException {
        return new URI("http://localhost:8080" + path);
    }

    boolean isTokenValid(String token){
        //maker call to auth server to get refreshed token
        return false;
    }
}
