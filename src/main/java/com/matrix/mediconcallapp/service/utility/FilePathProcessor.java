package com.matrix.mediconcallapp.service.utility;

public class FilePathProcessor {

    public static String processFilePath(String fullPath) {
        String[] pathParts = fullPath.split("\\\\");

        int length = pathParts.length;
        if (length < 2) {
            return fullPath;
        }
        String processedPath = "\\" + pathParts[length - 2] + "\\" + pathParts[length - 1];
        return processedPath;
    }
}