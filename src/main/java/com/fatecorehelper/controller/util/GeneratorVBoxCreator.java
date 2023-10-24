package com.fatecorehelper.controller.util;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class GeneratorVBoxCreator {
    final int prefTextBoxWidth = 400;
    final int defaultMaxPyramidHeight;
    final int pyramidWidth;
    final int aspectCount;
    final ArrayList<CheckBox> aspectCheckboxes = new ArrayList<>();
    final ArrayList<TextField> aspectFields = new ArrayList<>();
    final ArrayList<SkillColumn> skillGrid = new ArrayList<>();
    final ArrayList<String> disabledAspectTextFieldInput = new ArrayList<>();

    public GeneratorVBoxCreator(int defaultMaxPyramidHeight, int pyramidWidth, int aspectCount) {
        this.defaultMaxPyramidHeight = defaultMaxPyramidHeight;
        this.pyramidWidth = pyramidWidth;
        this.aspectCount = aspectCount;
    }

    public VBox createAspectFieldsAndCheckboxes(){
        VBox output = new VBox();
        for (int i = 0; i < aspectCount; i++) {
            HBox hbox = new HBox(8);
            TextField textField = new TextField();
            textField.setPrefWidth(prefTextBoxWidth);
            CheckBox checkBox = new CheckBox();
            checkBox.setOnAction(event -> {
                textField.setDisable(checkBox.isSelected());
                if (checkBox.isSelected()){
                    disabledAspectTextFieldInput.add(textField.getText());
                }else{
                    disabledAspectTextFieldInput.remove(textField.getText());
                }
            });
            hbox.getChildren().addAll(checkBox,textField);
            aspectCheckboxes.add(checkBox);
            aspectFields.add(textField);
            output.getChildren().add(hbox);
        }
        return output;
    }

    public GridPane createSkillFieldsAndCheckBoxes() {
        GridPane output = new GridPane();
        output.setPadding(new Insets(10, 10, 10, 10));
        output.setVgap(5);
        output.setHgap(5);
        int maxPyramidHeight = defaultMaxPyramidHeight;
        for (int i = 0; i < maxPyramidHeight; i++) {
            Label label = new Label("+" + (maxPyramidHeight - i));
            output.add(label,0,i + 1);
        }
        for (int i = 0; i < pyramidWidth; i++) {
            ArrayList<TextField> textFieldsColumn = new ArrayList<>();
            CheckBox checkBox = new CheckBox();
            SkillColumn skillColumn = new SkillColumn(textFieldsColumn,checkBox);
            skillGrid.add(skillColumn);
            output.add(checkBox,i + 1,0);
            for (int j = 0; j < maxPyramidHeight; j++) {
                HBox hbox = new HBox(8);
                TextField textField = new TextField();
                textFieldsColumn.add(textField);
                hbox.getChildren().addAll(textField);
                output.add(hbox,i + 1,j + 1);
            }
        }
        return output;
    }



    public ArrayList<CheckBox> getAspectCheckboxes() {
        return aspectCheckboxes;
    }

    public ArrayList<TextField> getAspectFields() {
        return aspectFields;
    }

    public ArrayList<SkillColumn> getSkillGrid() {
        return skillGrid;
    }

    public ArrayList<String> getDisabledAspectTextFieldInput() {
        return disabledAspectTextFieldInput;
    }

}
