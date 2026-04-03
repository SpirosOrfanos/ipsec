package com.frt.sec.api;

import com.frt.sec.model.dto.GdprRequest;
import com.frt.sec.model.dto.ResendJourneyRequest;
import com.frt.sec.service.TravelerActions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api")
public class Api {

    private final TravelerActions travelerActions;

    public Api(TravelerActions travelerActions) {
        this.travelerActions = travelerActions;
    }

    @PostMapping(path = "/retrieve-gdpr")
    public Mono<ResponseEntity> retrieveGdpr(@RequestBody GdprRequest gdprrequest) {
        travelerActions.generate(gdprrequest);
        return Mono.just(ResponseEntity.ok().build());
    }

    @PatchMapping(path = "/journey/resend")
    public Mono<ResponseEntity> resendJourney(@RequestBody ResendJourneyRequest resendJourneyRequest) {
        travelerActions.resend(resendJourneyRequest);
        return Mono.just(ResponseEntity.ok().build());
    }

    @GetMapping(path = "/journeys")
    public Mono<ResponseEntity> getJourneys(@RequestParam String country) {
        travelerActions.retrieve(country);
        return Mono.just(ResponseEntity.ok().build());
    }
}
