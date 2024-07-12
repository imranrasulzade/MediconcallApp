package com.matrix.mediconcallapp.service.utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Base64;

public class ImageToBase64 {

    public static String convertHttpsUrlsImageToBase64(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();

            byte[] imageBytes = baos.toByteArray();
            baos.close();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            return base64Image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertLocalImageToBase64(String filePath) {
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            byte[] imageBytes = baos.toByteArray();

            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            fis.close();
            baos.close();

            return base64Image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}