package com.fatecorehelper.controller.util;

import com.fatecorehelper.model.CharacterDTO;

import java.util.ArrayList;
import java.util.Collections;

public class CharacterSaver {
    CharacterDTO characterDTO;
    public CharacterSaver(int skillGridWidth){
        characterDTO = new CharacterDTO(skillGridWidth);
    }
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

    public String parseCharacter(){
        parseAspects();
        parseSkills();
        return parseAspects() + "\n" + parseSkills();
    }

    public void setCharacter(CharacterDTO characterDTO) {
        this.characterDTO = characterDTO;
    }
}
