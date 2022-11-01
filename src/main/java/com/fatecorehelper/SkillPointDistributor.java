package com.fatecorehelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class SkillPointDistributor {
    Random random = new Random();
    SkillRandomizer skillRandomizer;
    int defaultSkillPoints = 20;
    int skillPointsLeft;
    final int maxPyramidHeight = 5;
    final int pyramidWidth = 5;
    ArrayList<ArrayList<String>> skillPyramid = new ArrayList<>();

    SkillPointDistributor(){
        for (int i = 0; i < pyramidWidth; i++){
            skillPyramid.add(new ArrayList<>());
        }
    }

    private void clearSkillPyramid(ArrayList<Integer> excludedColumnIndexes){
        for (int i = 0; i < pyramidWidth; i++) {
            if (!excludedColumnIndexes.contains(i)){
                skillPyramid.set(i,new ArrayList<>());
            }
        }
    }

    private ArrayList<ArrayList<String>> getNonDisabledColumns(ArrayList<Integer> disabledSkillColumnIndexes){
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        for (int i = 0; i < skillPyramid.size(); i++) {
            if (!disabledSkillColumnIndexes.contains(i)){
                output.add(skillPyramid.get(i));
            }
        }
        return output;
    }

    private void insertNonDisabledColumns(ArrayList<ArrayList<String>> nonDisabledColumns, ArrayList<Integer> disabledSkillColumnIndexes){
        int j = 0;
        for (int i = 0; i < skillPyramid.size(); i++) {
            if (!disabledSkillColumnIndexes.contains(i)){
                skillPyramid.set(i,nonDisabledColumns.get(j));
                j++;
            }
        }
    }

    private void sortSkillPyramid(ArrayList<Integer> disabledSkillColumnIndexes){
        ArrayList<ArrayList<String>> nonDisabledColumns = getNonDisabledColumns(disabledSkillColumnIndexes);
        nonDisabledColumns.sort(new Comparator<>() {
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                return o2.size() - o1.size();
            }
        });
        insertNonDisabledColumns(nonDisabledColumns,disabledSkillColumnIndexes);
    }

    private ArrayList<String> getDisabledSkillTextFieldInput(ArrayList<SkillColumn> skillGrid){
        ArrayList<String> output = new ArrayList<>();
        for (SkillColumn skillColumn:
                skillGrid) {
            if (skillColumn.isDisabled()){
                output.addAll(skillColumn.getSkills());
            }
        }
        return output;
    }

    private ArrayList<Integer> getDisabledSkillColumnIndexes(ArrayList<SkillColumn> skillGrid){
        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < skillGrid.size(); i++) {
            if (skillGrid.get(i).isDisabled()){
                output.add(i);
            }
        }
        return output;
    }

    private int countSpentSkillPoints(ArrayList<SkillColumn> skillGrid){
        int output = 0;
        for (SkillColumn skillColumn:
                skillGrid) {
            if (skillColumn.isDisabled()){
                output += skillColumn.getSkillPointsInColumn();
            }
        }
        return output;
    }

    void distributeSkillPoints(ArrayList<SkillColumn> skillGrid, int skillPoints) throws IOException {
        skillPointsLeft = skillPoints - countSpentSkillPoints(skillGrid);
        ArrayList<String> disabledSkillTextFieldInput = getDisabledSkillTextFieldInput(skillGrid);
        ArrayList<Integer> disabledSkillColumnIndexes = getDisabledSkillColumnIndexes(skillGrid);
        skillRandomizer = new SkillRandomizer(disabledSkillTextFieldInput);
        clearSkillPyramid(disabledSkillColumnIndexes);
        while (skillPointsLeft > 0){
            ArrayList<Integer> possibleSlots = new ArrayList<>();
            for (int i = 0; i < pyramidWidth; i++) {
                if (skillPyramid.get(i).size() < maxPyramidHeight && (skillPyramid.get(i).size() + 1) <= skillPointsLeft && !disabledSkillColumnIndexes.contains(i)){
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
        sortSkillPyramid(disabledSkillColumnIndexes);
    }
}
