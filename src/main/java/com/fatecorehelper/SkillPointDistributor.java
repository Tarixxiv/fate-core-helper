package com.fatecorehelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SkillPointDistributor {
    Random random = new Random();
    SkillRandomizer skillRandomizer = new SkillRandomizer();
    int skillPoints = 20;
    private final int maxSkillLevel = 5;
    int skillLevelTierWidth = 5;
    ArrayList<ArrayList<String>> skillPyramid;

    public SkillPointDistributor() throws FileNotFoundException {
    }

    void distributeSkillPoints(){
        skillPyramid = new ArrayList();
        ArrayList<String> skills = new ArrayList<String>();

        for (int i = 0; i < skillLevelTierWidth; i++) {
            skillPyramid.add(new ArrayList<String>());
        }
        int skillPointsLeft = skillPoints;

        while (skillPointsLeft > 0){
            ArrayList<Integer> possibleSlots = new ArrayList<Integer>();
            for (int i = 0; i < skillLevelTierWidth; i++) {
                if (skillPyramid.get(i).size() < maxSkillLevel && skillPyramid.get(i).size() + 1 <= skillPointsLeft){
                    possibleSlots.add(i);
                }
            }
            if (possibleSlots.isEmpty()){
                break;
            }
            int chosenSkillColumn = random.nextInt(possibleSlots.size());
            skillPointsLeft -= (skillPyramid.get(chosenSkillColumn).size() + 1);
            skillPyramid.get(chosenSkillColumn).add(skillRandomizer.nextSkill());
        }
        Collections.sort(skillPyramid, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                return o2.size() - o1.size();
            }
        });
    }

    void printPyramid(){
        for (int i = maxSkillLevel - 1; i >= 0; i--) {
            for (ArrayList<String> skillColumn:
                    skillPyramid) {
                if(skillColumn.size() <= i){
                    System.out.print("    ");
                }else{
                    System.out.print(skillColumn.get(i));
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
