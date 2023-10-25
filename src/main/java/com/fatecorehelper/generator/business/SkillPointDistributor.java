package com.fatecorehelper.generator.business;
import com.fatecorehelper.controller.util.SkillColumn;

import java.util.ArrayList;
import java.util.Random;

public class SkillPointDistributor {
    Random random = new Random();
    SkillShuffler skillShuffler;
    int skillPointsLeft;
    private final int defaultMaxPyramidHeight;
    private final int pyramidWidth;
    public ArrayList<ArrayList<String>> skillPyramid;
    public int getSkillPointsLeft() {
        return skillPointsLeft;
    }

    public SkillPointDistributor(int defaultMaxPyramidHeight, int pyramidWidth){
        this.defaultMaxPyramidHeight = defaultMaxPyramidHeight;
        this.pyramidWidth = pyramidWidth;

        skillPyramid = new ArrayList<>();
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
        nonDisabledColumns.sort((o1, o2) -> o2.size() - o1.size());
        insertNonDisabledColumns(nonDisabledColumns,disabledSkillColumnIndexes);
    }

    private ArrayList<String> getDisabledSkillTextFieldInput(ArrayList<SkillColumn> skillGrid){
        ArrayList<String> output = new ArrayList<>();
        for (SkillColumn skillColumn:
                skillGrid) {
            if (skillColumn.isDisabled()){
                output.addAll(skillColumn.getNonBlankTextFieldsText());
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
                output += skillColumn.countSkillPointsInColumn();
            }
        }
        return output;
    }

    public void distributeSkillPoints(ArrayList<SkillColumn> skillGrid, int skillPoints, int maxPyramidHeight) throws Exception {
        if (maxPyramidHeight > defaultMaxPyramidHeight + 1){
            throw new Exception("too low defaultMaxPyramidHeight");
        }
        skillPointsLeft = skillPoints - countSpentSkillPoints(skillGrid);
        ArrayList<String> disabledSkillTextFieldInput = getDisabledSkillTextFieldInput(skillGrid);
        ArrayList<Integer> disabledSkillColumnIndexes = getDisabledSkillColumnIndexes(skillGrid);
        skillShuffler = new SkillShuffler(disabledSkillTextFieldInput);
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
            skillPyramid.get(chosenSkillColumn).add(skillShuffler.nextSkill());
        }
        sortSkillPyramid(disabledSkillColumnIndexes);
    }
}
