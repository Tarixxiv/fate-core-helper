package com.fatecorehelper.generator.business;
import com.fatecorehelper.model.CharacterDTO;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterSaver {
    private CharacterDTO characterDTO;
    private String parseSkills(){
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<ArrayList<String>> skills = characterDTO.skillGrid;
        skills.forEach(Collections::reverse);
        for (int i = 0; i < skills.get(0).size(); i++) {
            int pyramidSize = skills.get(0).size();
            int reverseIndex = pyramidSize - i - 1;
            stringBuilder.append("+");
            stringBuilder.append(reverseIndex + 1);
            for (ArrayList<String> skillColumn:
                    skills) {
                if (skillColumn.size() > reverseIndex){
                    stringBuilder.append("  |  ");
                    stringBuilder.append(skillColumn.get(reverseIndex));
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private String parseAspects(){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        String aspectAnnotation;
        for (String aspect:
                characterDTO.aspects) {
            aspectAnnotation = switch (i) {
                case 0 -> "HC: ";
                case 1 -> "Tr: ";
                default -> "* ";
            };
            stringBuilder.append(aspectAnnotation);
            stringBuilder.append(aspect);
            stringBuilder.append("\n");
            i++;
        }
        return stringBuilder.toString();
    }

    public String parseCharacter(CharacterDTO characterDTO){
        this.characterDTO = characterDTO;
        parseAspects();
        parseSkills();
        return parseAspects() + "\n" + parseSkills();
    }

    public void saveToFile(CharacterDTO characterDTO, String path){
        this.characterDTO = characterDTO;
        FileWriter fileWriter = new FileWriter();
        fileWriter.saveToFile(parseCharacter(characterDTO), path);
    }
}
