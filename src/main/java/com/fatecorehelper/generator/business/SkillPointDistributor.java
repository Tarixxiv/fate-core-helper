package com.fatecorehelper.generator.business;
import com.fatecorehelper.controller.util.SkillColumn;

import java.util.ArrayList;
import java.util.Random;

public class SkillPointDistributor {
    private static final Random random = new Random();
    SkillShuffler skillShuffler;
    int skillPointsLeft;
    private final int maxPyramidHeight;
    private final int pyramidWidth;
    public ArrayList<ArrayList<String>> skillPyramid;
    ArrayList<Integer> disabledSkillColumnIndexes;
    ArrayList<SkillColumn> skillGrid;
    public int getSkillPointsLeft() {
        return skillPointsLeft;
    }

    public SkillPointDistributor(ArrayList<SkillColumn> skillGrid, int maxPyramidHeight, int skillPoints){
        this.skillGrid = skillGrid;
        this.pyramidWidth = skillGrid.size();
        this.maxPyramidHeight = maxPyramidHeight;
        resetSkillPyramid();
        disabledSkillColumnIndexes = getDisabledSkillColumnIndexes();
        skillShuffler = new SkillShuffler(getDisabledSkillTextFieldInput());
        skillPointsLeft = skillPoints - countSpentSkillPoints();
        resetSkillPyramid();
    }

    private void resetSkillPyramid(){
        skillPyramid = new ArrayList<>();
        for (int i = 0; i < pyramidWidth; i++){
            skillPyramid.add(new ArrayList<>());
        }
    }

    private ArrayList<ArrayList<String>> getEnabledColumns(){
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        for (int i = 0; i < skillPyramid.size(); i++) {
            if (!disabledSkillColumnIndexes.contains(i)){
                output.add(skillPyramid.get(i));
            }
        }
        return output;
    }

    private void insertEnabledColumns(ArrayList<ArrayList<String>> insertEnabledColumns){
        int j = 0;
        for (int i = 0; i < skillPyramid.size(); i++) {
            if (!disabledSkillColumnIndexes.contains(i)){
                skillPyramid.set(i,insertEnabledColumns.get(j));
                j++;
            }
        }
    }

    private void sortSkillPyramid(){
        ArrayList<ArrayList<String>> enabledColumns = getEnabledColumns();
        enabledColumns.sort((o1, o2) -> o2.size() - o1.size());
        insertEnabledColumns(enabledColumns);
    }

    private ArrayList<String> getDisabledSkillTextFieldInput(){
        ArrayList<String> output = new ArrayList<>();
        for (SkillColumn skillColumn:
                skillGrid) {
            if (skillColumn.isDisabled()){
                output.addAll(skillColumn.getNonBlankTextFieldsText());
            }
        }
        return output;
    }

    private ArrayList<Integer> getDisabledSkillColumnIndexes(){
        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < skillGrid.size(); i++) {
            if (skillGrid.get(i).isDisabled()){
                output.add(i);
            }
        }
        return output;
    }

    private int countSpentSkillPoints(){
        int output = 0;
        for (SkillColumn skillColumn:
                skillGrid) {
            if (skillColumn.isDisabled()){
                output += skillColumn.countSkillPointsInColumn();
            }
        }
        return output;
    }

    boolean isPyramidColumnFull(int index){
        return skillPyramid.get(index).size() >= maxPyramidHeight;
    }

    public ArrayList<ArrayList<String>> distributeSkillPoints() {
        while (skillPointsLeft > 0){
            ArrayList<Integer> possibleSlots = new ArrayList<>();
            for (int i = 0; i < pyramidWidth; i++) {
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
        sortSkillPyramid();
        return skillPyramid;
    }
}
