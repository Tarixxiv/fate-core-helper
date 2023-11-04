package com.fatecorehelper.controller;

import com.fatecorehelper.controller.util.CharacterLoader;
import com.fatecorehelper.controller.util.CharacterSaver;
import com.fatecorehelper.controller.util.GeneratorVBoxCreator;
import com.fatecorehelper.controller.util.SkillColumn;
import com.fatecorehelper.generator.business.AspectRandomizer;
import com.fatecorehelper.generator.business.SkillPointDistributor;
import com.fatecorehelper.model.CharacterDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

import static com.fatecorehelper.FateCoreHelperApp.characterBufferPath;

public class GeneratorController {
    public int defaultSkillPoints = 20;
    public final int defaultMaxSkillGridHeight = 6;
    public final int skillGridWidth = 5;
    @FXML
    VBox characterVBox;
    @FXML
    TextField skillPointsTextField;
    @FXML
    TextField skillCapTextField;
    @FXML
    Label skillPointsLeftLabel;
    AspectRandomizer aspectRandomizer = new AspectRandomizer();
    SkillPointDistributor skillPointDistributor = new SkillPointDistributor(defaultMaxSkillGridHeight, skillGridWidth);
    CharacterSaver characterSaver = new CharacterSaver();
    CharacterLoader characterLoader = new CharacterLoader();
    ArrayList<CheckBox> aspectCheckboxes;
    ArrayList<TextField> aspectFields;
    ArrayList<SkillColumn> skillGrid;
    ArrayList<String> disabledAspectTextFieldInput;


    private void fillGeneratedCharacterSkills() throws Exception {
        skillPointDistributor.distributeSkillPoints(skillGrid, Integer.parseInt(skillPointsTextField.getText()),
                Integer.parseInt(skillCapTextField.getText()));
        for (int i = 0; i < skillGridWidth; i++) {
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
        fillGeneratedCharacterSkills();
        skillPointsLeftLabel.setText("SP left after generation : " + skillPointDistributor.getSkillPointsLeft());
    }

    private void fillGeneratedCharacterData() throws Exception {
        fillCharacterAspects();
        fillCharacterSkills();
    }

    @FXML
    private void initialize() {
        skillPointsTextField.setText(String.valueOf(defaultSkillPoints));
        skillCapTextField.setText(String.valueOf(defaultMaxSkillGridHeight));
        GeneratorVBoxCreator generatorVBoxCreator = new GeneratorVBoxCreator(defaultMaxSkillGridHeight, skillGridWidth,aspectRandomizer.getAspectCount());
        characterVBox.getChildren().addAll(generatorVBoxCreator.createAspectFieldsAndCheckboxes(),generatorVBoxCreator.createSkillFieldsAndCheckBoxes());
        disabledAspectTextFieldInput = generatorVBoxCreator.getDisabledAspectTextFieldInput();
        aspectFields = generatorVBoxCreator.getAspectFields();
        aspectCheckboxes = generatorVBoxCreator.getAspectCheckboxes();
        skillGrid = generatorVBoxCreator.getSkillGrid();

        setTextFields(characterLoader.loadFromFile(characterBufferPath));
    }

    public void setTextFields(CharacterDTO characterDTO){
        for (int i = 0; i < characterDTO.aspects.size(); i++) {
            aspectFields.get(i).setText(characterDTO.aspects.get(i));
        }
        for (int i = 0; i < skillGrid.size(); i++) {
            skillGrid.get(i).fillSkills(characterDTO.skillGrid.get(i));
        }
    }

    @FXML
    public void onGenerateButtonClick() {
        try {
            fillGeneratedCharacterData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onEditSkillsButtonClick(ActionEvent actionEvent) {
        characterSaver.setCharacter(createCharacter());
        characterSaver.saveToFile(characterBufferPath);
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/SkillEditorView.fxml");
        sceneChanger.changeScene();
    }

    public void onClipboardButtonClick() {
        characterSaver.setCharacter(createCharacter());
        String output = characterSaver.parseCharacter();
        System.out.println(output);
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(output);
        clipboard.setContent(content);
    }

    public CharacterDTO createCharacter(){
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.aspects = new ArrayList<>(aspectFields.stream().map(TextField::getText).toList()) ;
        characterDTO.skillGrid = new ArrayList<>(skillGrid.stream().map(SkillColumn::getNonBlankTextFieldsText).toList());
        return characterDTO;
    }

    public void loadButtonClick(ActionEvent actionEvent) {
        CharacterDTO characterDTO = createCharacter();
        characterSaver.setCharacter(characterDTO);
        characterSaver.saveToFile(characterBufferPath);
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/CharacterLoaderView.fxml");
        sceneChanger.changeScene();
    }
}

