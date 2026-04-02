package com.frt.sec.api;

import com.frt.sec.service.Generate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api")
public class Api {

    public Api(Generate generate) {
        this.generate = generate;
    }

    private final Generate generate;


    @GetMapping(path = "/retrieve")
    public Mono<ResponseEntity> get(@RequestParam(name = "id") String id) {
        return Mono.just(ResponseEntity.ok().build());
    }

    @GetMapping(path = "/retrievesec")
    public Mono<ResponseEntity> getSec() {
        return Mono.just(ResponseEntity.ok().build());
    }
}
