package com.matrix.mediconcallapp.service.utility;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class MapPathUtility {
    public static String mapPath(MultipartFile file, String UPLOAD_DIR) {
//        try {
//            File tempFile = File.createTempFile("temp", file.getOriginalFilename());
//            file.transferTo(tempFile);
//            Path targetPath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
//            tempFile.renameTo(targetPath.toFile());
//            String filePath = targetPath.toString();
//            return filePath;
//        } catch (IOException e) {
//            log.error("error to due " + e.getMessage());
//        }
//        return "-";


        try {
            String originalFilename = file.getOriginalFilename();
            Path targetPath = Paths.get(UPLOAD_DIR, originalFilename);

            // Eğer dosya zaten varsa, ismin sonuna sırayla bir rakam ekleyerek benzersiz bir isim oluştur.
            int counter = 1;
            while (Files.exists(targetPath)) {
                String baseName = FilenameUtils.getBaseName(originalFilename);
                String extension = FilenameUtils.getExtension(originalFilename);
                String newFilename = baseName + "_" + counter + "." + extension;
                targetPath = Paths.get(UPLOAD_DIR, newFilename);
                counter++;
            }

            File tempFile = File.createTempFile("temp", "");
            file.transferTo(tempFile);
            tempFile.renameTo(targetPath.toFile());
            String filePath = targetPath.toString();
            return filePath;
        } catch (IOException e) {
            log.error("error due to " + e.getMessage());
        }
        return "-";

    }
}
