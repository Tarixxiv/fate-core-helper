package com.fatecorehelper;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class SkillColumnCheckbox extends CheckBox {
    private final ArrayList<TextField> skillColumn;

    SkillColumnCheckbox(ArrayList<TextField> skillColumn){
        this.skillColumn = skillColumn;
        setOnAction(event -> {
            for (TextField textField:
                    skillColumn) {
                textField.setDisable(isSelected());
            }
        });
    }

    public ArrayList<TextField> getSkillColumn() {
        return skillColumn;
    }


}
