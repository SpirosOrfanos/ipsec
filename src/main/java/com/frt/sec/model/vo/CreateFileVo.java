package com.frt.sec.model.vo;

import net.lingala.zip4j.model.ZipParameters;

import java.util.List;

public record CreateFileVo (String filename,
                            String zipfFilename,
                            List<String> data,
                            boolean encrypted,
                            String password,
                            ZipParameters zipParameters,
                            String ticketId){
}
