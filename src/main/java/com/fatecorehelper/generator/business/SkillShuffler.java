package com.fatecorehelper.generator.business;

import com.fatecorehelper.model.Cache;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class SkillShuffler {
    ArrayList<String> skills;
    int currentSkillIndex = 0;
    String skillsPath = "DefaultSkills";

    public SkillShuffler(ArrayList<String> disabledSkillTextFieldInput) {
        if (Cache.gerInstance().skills.isPresent()){
            skills = Cache.gerInstance().skills.get();
            System.out.println(skills);
        }else {
            readSkillFile();
        }

        for (String skill:
                disabledSkillTextFieldInput) {
            while(skills.remove(skill));
        }
        Collections.shuffle(skills);
    }

    private void readSkillFile() {
        Scanner s = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream(skillsPath)), StandardCharsets.UTF_8);
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
