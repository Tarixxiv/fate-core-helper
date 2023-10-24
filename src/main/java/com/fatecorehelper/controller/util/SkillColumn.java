package com.fatecorehelper.controller.util;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class SkillColumn {
    private final ArrayList<TextField> textFields;
    private final CheckBox checkBox;

    public SkillColumn(ArrayList<TextField> textFields, CheckBox checkBox){
        this.textFields = textFields;
        checkBox.setOnAction(event -> {
            for (TextField textField:
                    textFields) {
                textField.setDisable(checkBox.isSelected());
            }
        });
        this.checkBox = checkBox;
    }

    private void clear(){
        for (TextField textField:
                textFields) {
            textField.setText("");
        }
    }

    public void fillSkills(ArrayList<String> arrayList){
        clear();
        for (int i = 0; i < arrayList.size(); i++) {
            textFields.get(textFields.size() - i - 1).setText(arrayList.get(i));
        }
    }

    public boolean isDisabled(){
        return checkBox.isSelected();
    }

    public int countSkillPointsInColumn(){
        int output = 0;
        for (int i = 0; i < textFields.size(); i++) {
            if (!textFields.get(i).getText().isBlank()){
                output += (textFields.size() - i);
            }
        }
        return output;
    }

    public ArrayList<String> getSkills() {
        ArrayList<String> output = new ArrayList<>();
        for (TextField textField:
                textFields){
            if (!textField.getText().isBlank()){
                output.add(textField.getText());
            }
        }
        return output;
    }
}
