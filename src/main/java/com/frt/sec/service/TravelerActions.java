package com.frt.sec.service;

import com.frt.sec.model.dto.GdprRequest;
import com.frt.sec.model.dto.ResendJourneyRequest;

public interface TravelerActions {
    void generate(GdprRequest request);
    void resend(ResendJourneyRequest request);
    void retrieve(String country);
    void loadConfig();
}
