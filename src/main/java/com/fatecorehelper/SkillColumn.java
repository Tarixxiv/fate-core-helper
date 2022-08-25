package com.fatecorehelper;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class SkillColumn {
    private final ArrayList<TextField> skillColumn;
    private final CheckBox checkBox;

    SkillColumn(ArrayList<TextField> skillColumn, CheckBox checkBox){
        this.skillColumn = skillColumn;
        checkBox.setOnAction(event -> {
            for (TextField textField:
                    skillColumn) {
                textField.setDisable(checkBox.isSelected());
            }
        });
        this.checkBox = checkBox;
    }

    public void clear(){
        for (TextField textField:
                skillColumn) {
            textField.setText("");
        }
    }

    public boolean isDisabled(){
        return checkBox.isSelected();
    }

    public int getSkillPointsInColumn(){
        int output = 0;
        for (int i = 0; i < skillColumn.size(); i++) {
            if (!skillColumn.get(i).getText().isBlank()){
                output += (skillColumn.size() - i);
            }
        }
        return output;
    }

    public ArrayList<String> getSkills() {
        ArrayList<String> output = new ArrayList<>();
        for (TextField textField:
                skillColumn){
            if (!textField.getText().isBlank()){
                output.add(textField.getText());
            }
        }
        return output;
    }
}
