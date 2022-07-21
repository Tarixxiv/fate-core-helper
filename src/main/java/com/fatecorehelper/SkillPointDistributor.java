package com.fatecorehelper;

import java.io.FileNotFoundException;
import java.util.*;

public class SkillPointDistributor {
    Random random = new Random();
    SkillRandomizer skillRandomizer = new SkillRandomizer();
    int skillPoints = 20;
    int skillPointsLeft;
    private final int maxSkillLevel = 5;
    int skillLevelTierWidth = 5;
    ArrayList<ArrayList<String>> skillPyramid;

    public SkillPointDistributor() throws FileNotFoundException {
    }

    private void clearSkillPyramid(){
        skillPyramid = new ArrayList();
        for (int i = 0; i < skillLevelTierWidth; i++) {
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
    //TODO fix negative SP left
    void distributeSkillPoints(){
        skillPointsLeft = skillPoints;
        clearSkillPyramid();
        while (skillPointsLeft > 0){
            ArrayList<Integer> possibleSlots = new ArrayList<Integer>();
            for (int i = 0; i < skillLevelTierWidth; i++) {
                if (skillPyramid.get(i).size() < maxSkillLevel && (skillPyramid.get(i).size() + 1) <= skillPointsLeft){
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
        sortSkillPyramid();
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
