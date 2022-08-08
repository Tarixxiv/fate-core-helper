package com.fatecorehelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SkillPointDistributor {
    Random random = new Random();
    SkillRandomizer skillRandomizer;
    int skillPoints = 20;
    int skillPointsLeft;
    final int maxPyramidHeight = 5;
    int pyramidWidth = 5;
    ArrayList<ArrayList<String>> skillPyramid;

    private void clearSkillPyramid(){
        skillPyramid = new ArrayList();
        for (int i = 0; i < pyramidWidth; i++) {
            skillPyramid.add(new ArrayList<String>());
        }
    }

    private void sortSkillPyramid(){
        skillPyramid.sort(new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                return o2.size() - o1.size();
            }
        });
    }
    void distributeSkillPoints() throws IOException {
        skillRandomizer = new SkillRandomizer();
        skillPointsLeft = skillPoints;
        clearSkillPyramid();
        while (skillPointsLeft > 0){
            ArrayList<Integer> possibleSlots = new ArrayList<Integer>();
            for (int i = 0; i < pyramidWidth; i++) {
                if (skillPyramid.get(i).size() < maxPyramidHeight && (skillPyramid.get(i).size() + 1) <= skillPointsLeft){
                    possibleSlots.add(i);
                }
            }
            if (possibleSlots.isEmpty()){
                break;
            }
            int chosenSkillColumn = possibleSlots.get(random.nextInt(possibleSlots.size()));
            skillPointsLeft -= (skillPyramid.get(chosenSkillColumn).size() + 1);
            skillPyramid.get(chosenSkillColumn).add(skillRandomizer.nextSkill());
        }
        sortSkillPyramid();
    }
}
