package com.fatecorehelper.generator.business;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SkillShuffler {
    ArrayList<String> skills;
    int currentSkillIndex = 0;

    SkillShuffler(ArrayList<String> disabledSkillTextFieldInput) throws IOException{
        readSkillFile();
        for (String skill:
                disabledSkillTextFieldInput) {
            while(skills.remove(skill));
        }
        Collections.shuffle(skills);
    }

    private void readSkillFile() throws IOException {
        Scanner s = new Scanner(new File("src/main/data/Skills"), StandardCharsets.UTF_8);
        skills = new ArrayList<>();
        while (s.hasNextLine()){
            skills.add(s.nextLine());
        }
        s.close();
    }

    String nextSkill(){

        if (skills.isEmpty() || skills.size() == currentSkillIndex){
            return "noSkillFound";
        }
        String output = skills.get(currentSkillIndex);
        currentSkillIndex++;
        return output;
    }
}
