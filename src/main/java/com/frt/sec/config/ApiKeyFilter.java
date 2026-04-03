package com.frt.sec.config;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ApiKeyFilter implements WebFilter {

    @Value("${app.x-gdpr-api.key.header.name}")
    private String gdpHeaderName;

    @Value("${app.x-gdpr-api.key.header.value}")
    private String gdpHeaderValue;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
                             @Nonnull WebFilterChain webFilterChain) {

        if ("/api/retrievesec".equalsIgnoreCase(serverWebExchange.getRequest().getPath().toString())) {
            ServerHttpRequest request = serverWebExchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            var gdprKey = Optional.ofNullable(headers.get(gdpHeaderName))
                    .map(List::getFirst)
                    .orElse(null);
            if (Objects.isNull(gdprKey) || gdpHeaderValue.equalsIgnoreCase(gdprKey)) {
                ServerHttpResponse response = serverWebExchange.getResponse();
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return response.setComplete();
            }
        }
        return webFilterChain.filter(serverWebExchange);
    }
}