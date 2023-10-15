package com.matrix.mediconcallapp.service.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class MapPathUtility {
    public static String mapPath(MultipartFile file, String UPLOAD_DIR) {
        try {
            File tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);
            Path targetPath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
            tempFile.renameTo(targetPath.toFile());
            String filePath = targetPath.toString();
            return filePath;
        } catch (IOException e) {
            log.error("error to due " + e.getMessage());
        }
        return "-";

    }
}
