package com.frt.sec.service;

import com.frt.sec.model.dto.GdprRequest;

public interface GdpDataGeneration {
    void generate(GdprRequest gdprrequest);
    void loadConfig();
}
