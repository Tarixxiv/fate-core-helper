package com.fatecorehelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class AspectRandomizer {

    final static int genericAspectCount = 2;
    final static int aspectCount = genericAspectCount + 3;

    int highConceptFileLineCount, troubleAspectFileCount, genericAspectFileLineCount, relationFileLineCount;


    public ArrayList<String> getUniqueRandomFileLines(String filePath, int outputLineCount, ArrayList<String> excludedEntries) throws FileNotFoundException {
        ArrayList<String> fileBuffer = new ArrayList<>();
        Random r = new Random();
        FileInputStream fis = new FileInputStream(filePath);
        Scanner sc = new Scanner(fis, StandardCharsets.UTF_8);
        while(sc.hasNextLine()){
            fileBuffer.add(sc.nextLine());
        }
        sc.close();

        if (outputLineCount > fileBuffer.size()){
            throw new IllegalArgumentException("outputLineCount is greater than line count in file");
        }

        ArrayList<String> output = new ArrayList<>();

        for (int i = 0; i < outputLineCount; i++) {
            int randomIndex = r.nextInt(fileBuffer.size());
            while (excludedEntries.contains(fileBuffer.get(randomIndex))){
                fileBuffer.remove(randomIndex);
                randomIndex = r.nextInt(fileBuffer.size());
                if (outputLineCount - i > fileBuffer.size()){
                    throw new IllegalArgumentException("outputLineCount is greater than line count in file");
                }
            }
            output.add(fileBuffer.get(randomIndex));
            fileBuffer.remove(randomIndex);
        }
        return output;
    }

    public ArrayList<String> generateAspects(ArrayList<String> excludedEntries) throws FileNotFoundException {
        ArrayList<String> output = new ArrayList<>();
        output.add(getUniqueRandomFileLines("src/main/data/HighConcepts",1,excludedEntries).get(0));
        output.add(getUniqueRandomFileLines("src/main/data/Troubles",1,excludedEntries).get(0));
        output.addAll(getUniqueRandomFileLines("src/main/data/GenericAspects",2,excludedEntries));
        output.add(getUniqueRandomFileLines("src/main/data/Relations",1,excludedEntries).get(0));
        return output;
    }

    public int getAspectCount() {
        return aspectCount;
    }
}
