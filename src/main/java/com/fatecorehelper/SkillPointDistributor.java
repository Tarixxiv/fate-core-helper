package com.fatecorehelper;

import java.util.ArrayList;
import java.util.Collections;

public class SkillPointDistributor {
    int skillPoints = 20;
    private int maxSkillLevel = 5;
    int skillLevelTierWidth = 6;
    String[][] skillPiramid;

    public SkillPointDistributor(){
        skillPiramid = new String[maxSkillLevel][skillLevelTierWidth];
    }

    private String[][] distributeSkillPoints(){
        int skillPointsLeft = skillPoints;
        while (skillPointsLeft > 0){

            ArrayList<Integer> possibleSlots = new ArrayList<Integer>();
            for (int i = 0; i < skillLevelTierWidth; i++) {
                possibleSlots.add(i);
            }
            Collections.shuffle(possibleSlots);
            for (int skillColumnIndex:
                 possibleSlots) {
                boolean skillSlotFound = false;
                for (String skillSlot:
                        skillPiramid[skillColumnIndex]) {
                    if (skillSlot == null){
                        skillSlotFound = true;
                        break;
                    }
                }
                if (skillSlotFound){
                    break;
                }
            }
        }
        return skillPiramid;
    }



    private void printPiramid(){
        for (String[] skillTier:
             skillPiramid) {
            for (String skillSlot:
                    skillTier) {
                System.out.print(skillSlot);
            }
            System.out.print("\n");
        }
    }
}
