package com.fatecorehelper.generator.business;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileWriter {
    public void saveToFile(String text, String filePath) throws IOException {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write(text);
            writer.close();
            osw.close();
    }
}

