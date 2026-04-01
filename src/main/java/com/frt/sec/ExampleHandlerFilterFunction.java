package com.frt.sec;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
public class ExampleHandlerFilterFunction
        implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
                             WebFilterChain webFilterChain) {
        System.out.println(serverWebExchange.getRequest().getPath());
        if ("/api/retrievesec".equalsIgnoreCase(serverWebExchange.getRequest().getPath().toString())) {
            ServerHttpRequest request = serverWebExchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            var gdprKey = Optional.ofNullable(headers.get("X-GDPR-API-KEY"))
                    .map(List::getFirst)
                    .orElse(null);
            System.out.println(gdprKey);
            ServerHttpResponse response = serverWebExchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        return webFilterChain.filter(serverWebExchange);
    }
}