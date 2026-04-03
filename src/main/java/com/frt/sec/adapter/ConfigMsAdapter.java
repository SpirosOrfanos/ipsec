package com.frt.sec.adapter;

import com.frt.sec.model.dto.ConfigItem;
import com.frt.sec.model.dto.ConfigWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigMsAdapter {

    public ConfigWrapper loadConfig() {
        return new ConfigWrapper(List.of(
                new ConfigItem("SE", "12345678"),
                new ConfigItem("PO", "87654321"))
        );
    }
}
