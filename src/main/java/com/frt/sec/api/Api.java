package com.frt.sec.api;

import com.frt.sec.model.dto.GdprRequest;
import com.frt.sec.service.GdpDataGeneration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api")
public class Api {

    private final GdpDataGeneration gdpDataGeneration;

    public Api(GdpDataGeneration gdpDataGeneration) {
        this.gdpDataGeneration = gdpDataGeneration;
    }

    @GetMapping(path = "/retrievesec")
    public Mono<ResponseEntity> getSec(@RequestBody GdprRequest gdprrequest) {
        gdpDataGeneration.generate(gdprrequest);
        return Mono.just(ResponseEntity.ok().build());
    }
}
