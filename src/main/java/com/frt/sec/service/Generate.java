package com.frt.sec.service;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class Generate {

    private static String FILETYPE = ".info";
    private static String INFO = "INFO_";
    private static String OPAQUE = "OPAQUE_";
    private static String CHARSET = "utf-8";
    ZipParameters zipParameters = new ZipParameters();
    ZipParameters zipParametersNoPass = new ZipParameters();
    public Generate() {
        //zipParameters.setEncryptFiles(true);
        zipParameters.setCompressionLevel(CompressionLevel.NO_COMPRESSION);
        //zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);

        zipParametersNoPass.setEncryptFiles(false);
        zipParametersNoPass.setCompressionLevel(CompressionLevel.HIGHER);
    }

    @Async(value = "generatorVirtualThreadExecutor")
    public void generate(String id) {
        createFiles(id);
    }

    private void createFiles(String id) {
        String INFO_FILE = INFO+id+FILETYPE;
        String OPAQUE_FILE = OPAQUE+id+FILETYPE;

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(INFO_FILE), CHARSET));
             ZipFile zipFile = new ZipFile(INFO+id+".zip", "password".toCharArray())) {
            writer.write("INFO:"+id);
            zipFile.addFile(new File(INFO_FILE), zipParameters);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //File fileToDelete = new File(INFO_FILE);
           // fileToDelete.delete();

        }
        /*try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OPAQUE_FILE), CHARSET));
             ZipFile zipFile = new ZipFile(OPAQUE+id+".zip")) {
            writer.write("OPQUE:"+id);
            zipFile.addFile(new File(OPAQUE_FILE), zipParametersNoPass);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File fileToDelete = new File(OPAQUE_FILE);
            fileToDelete.delete();
        }*/
    }


}
