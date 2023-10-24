package com.fatecorehelper.controller;

import com.fatecorehelper.controller.util.GeneratorVBoxCreator;
import com.fatecorehelper.controller.util.SkillColumn;
import com.fatecorehelper.generator.business.AspectRandomizer;
import com.fatecorehelper.generator.business.SkillPointDistributor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class GeneratorController {
    public int defaultSkillPoints = 20;
    public final int defaultMaxPyramidHeight = 6;
    public final int pyramidWidth = 5;
    @FXML
    VBox characterVBox;
    @FXML
    TextField skillPointsTextField;
    @FXML
    TextField skillCapTextField;
    @FXML
    Label skillPointsLeftLabel;
    AspectRandomizer aspectRandomizer = new AspectRandomizer();
    SkillPointDistributor skillPointDistributor = new SkillPointDistributor(defaultMaxPyramidHeight,pyramidWidth);
    ArrayList<CheckBox> aspectCheckboxes;
    ArrayList<TextField> aspectFields;
    ArrayList<SkillColumn> skillGrid;
    ArrayList<String> disabledAspectTextFieldInput;

    private void generatePyramid() throws Exception {
        skillPointDistributor.distributeSkillPoints(skillGrid, Integer.parseInt(skillPointsTextField.getText()),
                Integer.parseInt(skillCapTextField.getText()));
        for (int i = 0; i < pyramidWidth; i++) {
            if (!skillGrid.get(i).isDisabled()){
                skillGrid.get(i).fillSkills(skillPointDistributor.skillPyramid.get(i));
            }
        }
    }

    private void fillCharacterAspects() {
        ArrayList<String> aspects = aspectRandomizer.generateAspects(disabledAspectTextFieldInput);
        for (int i = 0; i < aspectCheckboxes.size(); i++) {
            if (!aspectCheckboxes.get(i).isSelected()){
                aspectFields.get(i).setText(aspects.get(i));
            }
        }
    }

    private void fillCharacterSkills() throws Exception {
        generatePyramid();
        skillPointsLeftLabel.setText("SP left after generation : " + skillPointDistributor.getSkillPointsLeft());
    }
    
    private void fillGeneratedCharacterData() throws Exception {
        fillCharacterAspects();
        fillCharacterSkills();
    }

    @FXML
    private void initialize() throws Exception {
        skillPointsTextField.setText(String.valueOf(defaultSkillPoints));
        skillCapTextField.setText(String.valueOf(defaultMaxPyramidHeight));
        GeneratorVBoxCreator generatorVBoxCreator = new GeneratorVBoxCreator(defaultMaxPyramidHeight,pyramidWidth,aspectRandomizer.getAspectCount());
        characterVBox.getChildren().addAll(generatorVBoxCreator.createAspectFieldsAndCheckboxes(),generatorVBoxCreator.createSkillFieldsAndCheckBoxes());
        disabledAspectTextFieldInput = generatorVBoxCreator.getDisabledAspectTextFieldInput();
        aspectFields = generatorVBoxCreator.getAspectFields();
        aspectCheckboxes = generatorVBoxCreator.getAspectCheckboxes();
        skillGrid = generatorVBoxCreator.getSkillGrid();
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

    public void onEditSkillsButtonClick(ActionEvent actionEvent) {
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/SkillEditorView.fxml");
        sceneChanger.changeScene();
    }
}
