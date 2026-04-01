package com.frt.sec;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api")
public class Api {


    @GetMapping(path = "/retrieve")
    public Mono<ResponseEntity> get() {
        return Mono.just(ResponseEntity.ok().build());
    }

    @GetMapping(path = "/retrievesec")
    public Mono<ResponseEntity> getSec() {
        return Mono.just(ResponseEntity.ok().build());
    }
}
