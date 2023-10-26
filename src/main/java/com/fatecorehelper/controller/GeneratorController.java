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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private String parseSkillsToOutput(){
        StringBuilder stringBuilder = new StringBuilder();
        List<ArrayList<String>> skills = skillGrid.stream().map(SkillColumn::getNonBlankTextFieldsText).toList();
        skills.forEach(Collections::reverse);
        for (int i = 0; i < skills.get(0).size(); i++) {
            int pyramidSize = skills.get(0).size();
            int reverseIndex = pyramidSize - i - 1;
            for (ArrayList<String> skillColumn:
                    skills) {
                if (skillColumn.size() > reverseIndex){
                    stringBuilder.append(skillColumn.get(reverseIndex));
                    stringBuilder.append("  |  ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private String parseAspectsToOutput(){
        List<String> aspects =  aspectFields.stream().map(TextField::getText).toList();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        String aspectAnnotation;
        for (String aspect:
             aspects) {

            aspectAnnotation = switch (i) {
                case 0 -> "HC: ";
                case 1 -> "Tr: ";
                default -> "* ";
            };

            stringBuilder.append(aspectAnnotation);
            stringBuilder.append(aspect);
            stringBuilder.append("\n");
            i++;
        }
        return stringBuilder.toString();
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

    private String characterToString(){
        parseAspectsToOutput();
        parseSkillsToOutput();
        return parseAspectsToOutput() + "\n" + parseSkillsToOutput();
    }

    public void onClipboardButtonClick(ActionEvent actionEvent) {
        String output = characterToString();
        System.out.println(output);
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(output);
        clipboard.setContent(content);
    }

    public void loadButtonClick(ActionEvent actionEvent) {
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/CharacterLoaderView.fxml");
        CharacterLoaderController controller = (CharacterLoaderController) sceneChanger.getController();
        sceneChanger.changeScene();
    }
}

