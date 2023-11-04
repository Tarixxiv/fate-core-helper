package com.fatecorehelper.generator.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class FileReader {
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
    public ArrayList<String> parseFileLinesToArray(String filePath) throws FileNotFoundException {
        ArrayList<String> fileBuffer = new ArrayList<>();
        Scanner sc = new Scanner(new FileInputStream(filePath), StandardCharsets.UTF_8);
        while (sc.hasNextLine()) {
            fileBuffer.add(sc.nextLine());
        }
        sc.close();

        return fileBuffer;
    }
}
