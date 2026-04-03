package com.frt.sec.adapter;

import com.frt.sec.model.dto.GdprData;
import com.frt.sec.model.dto.GdprRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TravellerMsAdapter {

    public GdprData retrieveGdprData(GdprRequest gdprRequest) {
        return new GdprData(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()), List.of("MY DATA 1", "MY DATA 2"));
    }
}
