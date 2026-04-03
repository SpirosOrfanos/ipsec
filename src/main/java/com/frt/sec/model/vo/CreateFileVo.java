package com.frt.sec.model.vo;

import net.lingala.zip4j.model.ZipParameters;

public record CreateFileVo (String filename,
                            String zipfFilename,
                            String data,
                            boolean encrypted,
                            String password,
                            ZipParameters zipParameters){
}
