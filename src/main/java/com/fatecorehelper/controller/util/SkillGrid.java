package com.fatecorehelper.controller.util;

import java.util.ArrayList;


public class SkillGrid {
    ArrayList<SkillColumn> skillColumns;

    public SkillGrid(ArrayList<SkillColumn> skillColumns){
        this.skillColumns = skillColumns;
    }

    public ArrayList<ArrayList<String>> getSkillGridArray(){
        return new ArrayList<>(skillColumns.stream().map(SkillColumn::getNonBlankTextFieldsText).toList());
    }

    public void setSkillGridFromArray(ArrayList<ArrayList<String>> array){
        for (int i = 0; i < skillColumns.size(); i++) {
            skillColumns.get(i).fillSkills(array.get(i));
        }
    }

    public ArrayList<String> getDisabledSkillTextFieldInput(){
        ArrayList<String> output = new ArrayList<>();
        for (SkillColumn skillColumn:
                skillColumns) {
            if (skillColumn.isDisabled()){
                output.addAll(skillColumn.getNonBlankTextFieldsText());
            }
        }
        return output;
    }

    public ArrayList<Integer> getDisabledSkillColumnIndexes(){
        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < skillColumns.size(); i++) {
            if (skillColumns.get(i).isDisabled()){
                output.add(i);
            }
        }
        return output;
    }

    public void setDisabledSkillColumns(ArrayList<Integer> indexes){
        for (int index:
             indexes) {
            skillColumns.get(index).disable();
        }
    }

    public int countSpentSkillPoints(){
        return skillColumns.stream().mapToInt(SkillColumn::countSkillPointsInColumn).sum();
    }

    public int countDisabledSkillPoints(){
        return skillColumns.stream().filter(SkillColumn::isDisabled)
                .mapToInt(SkillColumn::countSkillPointsInColumn).sum();
    }
}
