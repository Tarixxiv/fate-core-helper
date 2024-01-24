package com.fatecorehelper.generator.business;

import java.util.ArrayList;
import java.util.Random;

public class SkillDistributor {
    private static final Random random = new Random();
    SkillShuffler skillShuffler;
    private final int maxPyramidHeight;
    private final int maxPyramidWidth;
    public ArrayList<ArrayList<String>> skillPyramid;


    public SkillDistributor(int maxPyramidWidth, int maxPyramidHeight, SkillShuffler skillShuffler){
        this.maxPyramidWidth = maxPyramidWidth;
        this.maxPyramidHeight = maxPyramidHeight;
        this.skillShuffler = skillShuffler;
        resetSkillPyramid();
    }

    private void resetSkillPyramid(){
        skillPyramid = new ArrayList<>();
        for (int i = 0; i < maxPyramidWidth; i++){
            skillPyramid.add(new ArrayList<>());
        }
    }

    private ArrayList<ArrayList<String>> getEnabledColumns(ArrayList<Integer> disabledSkillColumnIndexes){
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        for (int i = 0; i < skillPyramid.size(); i++) {
            if (!disabledSkillColumnIndexes.contains(i)){
                output.add(skillPyramid.get(i));
            }
        }
        return output;
    }

    private void insertEnabledColumns(ArrayList<ArrayList<String>> insertEnabledColumns, ArrayList<Integer> disabledSkillColumnIndexes){
        int j = 0;
        for (int i = 0; i < skillPyramid.size(); i++) {
            if (!disabledSkillColumnIndexes.contains(i)){
                skillPyramid.set(i,insertEnabledColumns.get(j));
                j++;
            }
        }
    }

    private void sortSkillPyramid(ArrayList<Integer> disabledSkillColumnIndexes){
        ArrayList<ArrayList<String>> enabledColumns = getEnabledColumns(disabledSkillColumnIndexes);
        enabledColumns.sort((o1, o2) -> o2.size() - o1.size());
        insertEnabledColumns(enabledColumns, disabledSkillColumnIndexes);
    }

    boolean isPyramidColumnFull(int index){
        return skillPyramid.get(index).size() >= maxPyramidHeight;
    }

    public ArrayList<ArrayList<String>> distributeSkillPoints(ArrayList<Integer> disabledSkillColumnIndexes, int skillPointsLeft) {
        while (skillPointsLeft > 0){
            ArrayList<Integer> possibleSlots = new ArrayList<>();
            for (int i = 0; i < maxPyramidWidth; i++) {
                int skillCost = skillPyramid.get(i).size() + 1;
                if (!isPyramidColumnFull(i) &&
                        skillCost <= skillPointsLeft &&
                        !disabledSkillColumnIndexes.contains(i)){
                    possibleSlots.add(i);
                }
            }
            if (possibleSlots.isEmpty()){
                break;
            }
            int chosenSkillColumn = possibleSlots.get(random.nextInt(possibleSlots.size()));
            skillPointsLeft -= (skillPyramid.get(chosenSkillColumn).size() + 1);
            skillPyramid.get(chosenSkillColumn).add(skillShuffler.nextSkill());
        }
        sortSkillPyramid(disabledSkillColumnIndexes);
        return skillPyramid;
    }
}
