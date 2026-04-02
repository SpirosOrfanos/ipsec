package com.frt.sec.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Generate {


    @Async(value = "generatorVirtualThreadExecutor")
    public void generate() {

    }

    private void createFiles() {

    }
}
