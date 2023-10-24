package com.fatecorehelper.generator.business;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class FileParser {
    public ArrayList<String> parseResourceLinesToArray(String resourcePath) {
        ArrayList<String> fileBuffer = new ArrayList<>();
        try {
            Scanner sc = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream(resourcePath)), StandardCharsets.UTF_8);
            while (sc.hasNextLine()) {
                fileBuffer.add(sc.nextLine());
            }
            sc.close();
        } catch (Exception e) {
            fileBuffer.add("File not found");
        }
        return fileBuffer;
    }
    public ArrayList<String> parseFileLinesToArray(String filePath) {
        ArrayList<String> fileBuffer = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new FileInputStream(filePath), StandardCharsets.UTF_8);
            while (sc.hasNextLine()) {
                fileBuffer.add(sc.nextLine());
            }
            sc.close();
        } catch (Exception e) {
            fileBuffer.add("File not found");
        }
        return fileBuffer;
    }
}
