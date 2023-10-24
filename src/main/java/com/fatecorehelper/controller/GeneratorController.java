package com.fatecorehelper.controller;

import com.fatecorehelper.controller.util.SceneChanger;
import com.fatecorehelper.generator.business.AspectRandomizer;
import com.fatecorehelper.generator.business.SkillPointDistributor;
import com.fatecorehelper.generator.ui.SkillColumn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GeneratorController {
    @FXML
    VBox vBox;
    @FXML
    TextField skillPointsTextField;
    @FXML
    TextField skillCapTextField;
    AspectRandomizer aspectRandomizer = new AspectRandomizer();
    SkillPointDistributor skillPointDistributor = new SkillPointDistributor();
    Label skillPointsLeftLabel = new Label();
    ArrayList<CheckBox> aspectCheckboxes = new ArrayList<>();
    ArrayList<TextField> aspectFields = new ArrayList<>();
    ArrayList<SkillColumn> skillGrid = new ArrayList<>();
    ArrayList<String> disabledAspectTextFieldInput = new ArrayList<>();

    private void generatePyramid() throws Exception {
        skillPointDistributor.distributeSkillPoints(skillGrid, Integer.parseInt(skillPointsTextField.getText()),
                Integer.parseInt(skillCapTextField.getText()));
        for (int i = 0; i < skillPointDistributor.pyramidWidth; i++) {
            if (!skillGrid.get(i).isDisabled()){
                skillGrid.get(i).parseSkills(skillPointDistributor.skillPyramid.get(i));
            }
        }
    }

    private void fillCharacterAspects() throws FileNotFoundException {
        ArrayList<String> aspects = aspectRandomizer.generateAspects(disabledAspectTextFieldInput);
        for (int i = 0; i < aspectCheckboxes.size(); i++) {
            if (!aspectCheckboxes.get(i).isSelected()){
                aspectFields.get(i).setText(aspects.get(i));
            }
        }
    }

    private void fillCharacterSkills() throws Exception {
        generatePyramid();
        skillPointsLeftLabel.setText("SP left after generation : " + skillPointDistributor.skillPointsLeft);
    }
    
    private void fillGeneratedCharacterData() throws Exception {
        fillCharacterAspects();
        fillCharacterSkills();
    }

    VBox createAspectFieldsAndCheckboxes(){
        VBox output = new VBox();
        for (int i = 0; i < aspectRandomizer.getAspectCount(); i++) {
            HBox hbox = new HBox(8);
            TextField textField = new TextField();
            textField.setPrefWidth(400);
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

    GridPane createSkillFieldsAndCheckBoxes() {
        GridPane output = new GridPane();
        output.setPadding(new Insets(10, 10, 10, 10));
        output.setVgap(5);
        output.setHgap(5);
        int maxPyramidHeight = skillPointDistributor.defaultMaxPyramidHeight;
        for (int i = 0; i < maxPyramidHeight; i++) {
            Label label = new Label("+" + (maxPyramidHeight - i));
            output.add(label,0,i + 1);
        }
        for (int i = 0; i < skillPointDistributor.pyramidWidth; i++) {
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

    @FXML
    private void initialize() throws Exception {
        skillPointsTextField.setText(String.valueOf(skillPointDistributor.defaultSkillPoints));
        skillCapTextField.setText(String.valueOf(skillPointDistributor.defaultMaxPyramidHeight));

        vBox.getChildren().addAll(createAspectFieldsAndCheckboxes(),
                createSkillFieldsAndCheckBoxes(),skillPointsLeftLabel);

        fillGeneratedCharacterData();
    }

    @FXML
    public void onGenerateButtonClick(ActionEvent actionEvent) {
        try {
            fillGeneratedCharacterData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onEditSkillsButtonClick(ActionEvent actionEvent) throws IOException {
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/SkillEditorView.fxml");
        sceneChanger.changeScene();
    }
}
