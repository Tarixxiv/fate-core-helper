package com.fatecorehelper.controller.util;

import com.fatecorehelper.model.CharacterDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharacterLoader {
    List<String> lines;
    int skillGridWidth = 6;
    int aspectCount = 5;
    CharacterDTO characterDTO = new CharacterDTO(skillGridWidth);

    private void loadAspects(){
        List<String> aspectLines = lines.subList(0,aspectCount);
        aspectLines.forEach(e -> characterDTO.aspects.add(e.split(" ", 2)[1]));
    }
    private void loadSkills(){
        List<String> skillLines = lines.subList(aspectCount, lines.size());
        ArrayList<String> skillLinesReversed = new ArrayList<>(skillLines);
        Collections.reverse(skillLinesReversed);
        for (String line:
                skillLinesReversed) {
            ArrayList<String> skillRow = new ArrayList<>(Arrays.asList(line.split(" *\\| *"))) ;
            skillRow.remove(0);
            for (int i = 0; i < skillRow.size(); i++) {
                characterDTO.skillGrid.get(i).add(skillRow.get(i));
            }
        }
    }
    public CharacterDTO load(String text){
        lines = Arrays.stream(text.split("\n")).filter(e -> !e.isBlank()).toList();
        loadAspects();
        loadSkills();
        return characterDTO;
    }
}
