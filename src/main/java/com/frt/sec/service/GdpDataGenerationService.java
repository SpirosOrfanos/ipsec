package com.frt.sec.service;

import com.frt.sec.adapter.ConfigMsAdapter;
import com.frt.sec.adapter.SnowAdapter;
import com.frt.sec.adapter.TravellerMsAdapter;
import com.frt.sec.model.dto.ConfigItem;
import com.frt.sec.model.dto.GdprData;
import com.frt.sec.model.dto.GdprRequest;
import com.frt.sec.model.vo.CreateFileVo;
import io.netty.util.CharsetUtil;
import jakarta.annotation.PostConstruct;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class GdpDataGenerationService implements GdpDataGeneration{


    private static final String CONTENTS_FILE_SUFFIX = ".info";
    private static final String ZIP_FILE_SUFFIX = ".zip";
    private static final String OPAQUE = "OPAQUE_";

    private final ZipParameters zipParameters = new ZipParameters();
    private final ZipParameters zipParametersNoPass = new ZipParameters();
    private final Map<String, ConfigItem> configItemMap = new HashMap<>();

    private final ConfigMsAdapter configMsAdapter;
    private final TravellerMsAdapter travellerMsAdapter;
    private final SnowAdapter snowAdapter;
    public GdpDataGenerationService(ConfigMsAdapter configMsAdapter, TravellerMsAdapter travellerMsAdapter, SnowAdapter snowAdapter) {
        this.configMsAdapter = configMsAdapter;
        this.travellerMsAdapter = travellerMsAdapter;
        this.snowAdapter = snowAdapter;
    }

    @PostConstruct
    void post() {
        zipParameters.setEncryptFiles(true);
        zipParameters.setCompressionLevel(CompressionLevel.NO_COMPRESSION);
        zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
        zipParameters.setUnixMode(true);
        zipParametersNoPass.setEncryptFiles(false);
        zipParametersNoPass.setCompressionLevel(CompressionLevel.NO_COMPRESSION);
    }

    @Scheduled(initialDelay = 5000, fixedRate = 60000)
    public void loadConfig() {
        configItemMap.clear();
        configMsAdapter.loadConfig()
                .config()
                .forEach(item -> configItemMap.put(item.country(), item));
    }

    @Async(value = "generatorVirtualThreadExecutor")
    public void generate(GdprRequest gdprrequest) {
        ConfigItem configItem = configItemMap.get(gdprrequest.country());
        if (Objects.isNull(configItem)) {
            return;
        }
        GdprData gdprData = travellerMsAdapter.retrieveGdprData(gdprrequest);
        String filename = gdprrequest.ticketId().concat(CONTENTS_FILE_SUFFIX);
        String zipFileName = gdprrequest.ticketId().concat(ZIP_FILE_SUFFIX);
        createFiles(new CreateFileVo(filename, zipFileName, gdprData.travelerData(),
                true, configItem.password(), zipParameters,
                gdprrequest.ticketId()));
        createFiles(new CreateFileVo(OPAQUE.concat(filename), OPAQUE.concat(zipFileName),
                gdprData.opaque(), false, null, zipParametersNoPass,
                gdprrequest.ticketId()));
    }



    private void createFiles(CreateFileVo createFileVo) {
        boolean encrypted = createFileVo.encrypted() && Objects.nonNull(createFileVo.password()) && !createFileVo.password().isBlank();
        try (PrintWriter writer = new PrintWriter(new FileWriter(createFileVo.filename(), CharsetUtil.UTF_8, false))) {//Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(createFileVo.filename()), CharsetUtil.UTF_8))) {
            for (String data:createFileVo.data()) {
                writer.println(data);
            }
            writer.close();
            ZipFile zipFile = encrypted ?
                    new ZipFile(createFileVo.zipfFilename(), createFileVo.password().toCharArray()) :
                    new ZipFile(createFileVo.zipfFilename());
            zipFile.addFile(new File(createFileVo.filename()), createFileVo.zipParameters());
            zipFile.close();
            byte[] byteArray = Files.readAllBytes(Paths.get(createFileVo.zipfFilename()));
            snowAdapter.attachFile(createFileVo.filename(), createFileVo.ticketId(), byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File fileToDelete = new File(createFileVo.filename());
            fileToDelete.delete();
            fileToDelete = new File(createFileVo.zipfFilename());
            fileToDelete.delete();
        }
    }
}
