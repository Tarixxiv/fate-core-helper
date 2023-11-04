package com.fatecorehelper.generator.business;

import java.util.ArrayList;
import java.util.Random;


public class AspectRandomizer {
    final static int genericAspectCount = 2;
    final static int aspectCount = genericAspectCount + 3;
    final static String highConceptPath = "HighConcepts";
    final static String troublePath = "Troubles";
    final static String genericAspectPath = "GenericAspects";
    final static String relationsPath = "GenericAspects";
    private static final Random random = new Random();
    FileReader fileParser = new FileReader();

    public ArrayList<String> getUniqueRandomFileLines(String resourcePath, int outputLineCount, ArrayList<String> excludedEntries) {
        ArrayList<String> fileBuffer = fileParser.parseResourceLinesToArray(resourcePath);
        if (outputLineCount > fileBuffer.size()){
            throw new IllegalArgumentException("outputLineCount is greater than line count in file");
        }
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < outputLineCount; i++) {
            int randomIndex = random.nextInt(fileBuffer.size());
            while (excludedEntries.contains(fileBuffer.get(randomIndex)) || fileBuffer.get(randomIndex).isBlank()){
                fileBuffer.remove(randomIndex);
                randomIndex = random.nextInt(fileBuffer.size());
                if (outputLineCount - i > fileBuffer.size()){
                    throw new IllegalArgumentException("outputLineCount is greater than line count in file");
                }
            }
            output.add(fileBuffer.get(randomIndex));
            fileBuffer.remove(randomIndex);
        }
        return output;
    }

    public ArrayList<String> generateAspects(ArrayList<String> excludedEntries) {
        ArrayList<String> output = new ArrayList<>();
        output.add(getUniqueRandomFileLines(highConceptPath,1,excludedEntries).get(0));
        output.add(getUniqueRandomFileLines(troublePath,1,excludedEntries).get(0));
        output.addAll(getUniqueRandomFileLines(genericAspectPath,2,excludedEntries));
        output.add(getUniqueRandomFileLines(relationsPath,1,excludedEntries).get(0));
        return output;
    }

    public int getAspectCount() {
        return aspectCount;
    }
}
