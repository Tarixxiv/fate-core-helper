package com.fatecorehelper.generator.business;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class FileWriter {
    public void saveToFile(String text, String filePath){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write(text);
            writer.close();
            osw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
