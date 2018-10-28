package com.fmi.mpr.e_00;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileOperations {

    private String fileName;
    
    public FileOperations(String fileName) {
        this.fileName = fileName;
    }

    public String readFile() throws IOException {

        File file = new File(fileName);
        byte[] buffer = new byte[1024];
        StringBuilder content = new StringBuilder();
        try (InputStream in = new FileInputStream(file)) {
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer, 0, 1024)) > 0) {
                content.append(new String(buffer, 0, bytesRead));
            }
        }
        return content.toString();
    }

    public void writeToFile(String content) throws IOException {

        File file = new File(fileName);

        try (OutputStream out = new FileOutputStream(file)) {
            out.write(content.getBytes());
            out.flush();
        }
    }
}
