package com.fatecorehelper.generator.business;
import com.fatecorehelper.model.CharacterDTO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharacterLoader {
    List<String> lines;
    int aspectCount = 5;
    CharacterDTO characterDTO = new CharacterDTO();

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
            ArrayList<String> skillRow = new ArrayList<>(Arrays.asList(line.split(" *\\| *")));
            skillRow.remove(0);
            for (int i = 0; i < skillRow.size(); i++) {
                characterDTO.skillGrid.get(i).add(skillRow.get(i));
            }
        }
    }

    private void load(){
        loadAspects();
        loadSkills();
    }

    public CharacterDTO loadFromString(String text){
        characterDTO = new CharacterDTO();
        lines = Arrays.stream(text.split("\n")).filter(e -> !e.isBlank()).toList();
        load();
        return characterDTO;
    }

    public CharacterDTO loadFromFile(String path){
        characterDTO = new CharacterDTO();
        FileReader fileReader = new FileReader();
        try{
            lines = fileReader.parseFileLinesToArray(path);
            load();
        } catch (FileNotFoundException ignored) {
        }
        return characterDTO;
    }


}
