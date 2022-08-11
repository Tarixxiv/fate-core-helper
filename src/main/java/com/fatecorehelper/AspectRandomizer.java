package com.fatecorehelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class AspectRandomizer {

    final static int genericAspectCount = 2;
    final static int aspectCount = genericAspectCount + 3;

    public String reservoirSampleFileLines(String filePath) throws FileNotFoundException {
        String output = "";
        int count = 0;
        Random r = new Random();
        FileInputStream fis = new FileInputStream(filePath);
        Scanner sc = new Scanner(fis, StandardCharsets.UTF_8);
        while(sc.hasNextLine()){
            if (r.nextInt(count+1) == count){
                output = sc.nextLine();
            }else{
                sc.nextLine();
            }
            count++;
        }
        sc.close();
        return output;
    }

    public ArrayList<String> getUniqueRandomFileLines(String filePath, int outputLineCount) throws FileNotFoundException {
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
            output.add(fileBuffer.get(randomIndex));
            fileBuffer.remove(randomIndex);
        }
        return output;
    }

    public ArrayList<String> generateAspects() throws FileNotFoundException {
        ArrayList<String> output = new ArrayList<>();
        output.add(reservoirSampleFileLines("src/main/data/HighConcepts"));
        output.add(reservoirSampleFileLines("src/main/data/Troubles"));
        output.addAll(getUniqueRandomFileLines("src/main/data/GenericAspects",2));
        output.add(reservoirSampleFileLines("src/main/data/Relations"));
        return output;
    }

    public int getAspectCount() {
        return aspectCount;
    }
}
