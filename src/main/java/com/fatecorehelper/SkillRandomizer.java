package com.fatecorehelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SkillRandomizer {
    ArrayList<String> skills;
    int currentSkillIndex = 0;

    SkillRandomizer() throws IOException {
        Scanner s = new Scanner(new File("src/main/data/Skills"), StandardCharsets.UTF_8);
        skills = new ArrayList<String>();
        while (s.hasNextLine()){
            skills.add(s.nextLine());
        }
        s.close();
        Collections.shuffle(skills);
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
