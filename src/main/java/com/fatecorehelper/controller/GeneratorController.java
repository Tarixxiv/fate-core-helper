package com.fatecorehelper.controller;

import com.fatecorehelper.controller.util.SkillGrid;
import com.fatecorehelper.generator.business.*;
import com.fatecorehelper.controller.util.GeneratorVBoxCreator;
import com.fatecorehelper.model.CharacterDTO;
import com.fatecorehelper.model.Cache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Optional;

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
    @FXML
    Label errorLabel;
    AspectRandomizer aspectRandomizer = new AspectRandomizer();
    CharacterSaver characterSaver = new CharacterSaver();
    ArrayList<CheckBox> aspectCheckboxes;
    ArrayList<TextField> aspectFields;
    SkillGrid skillGrid;

    private void fillCharacterSkills() {
        try{
            int maxPyramidHeight = Integer.parseInt(skillCapTextField.getText());
            int skillPoints = Integer.parseInt(skillPointsTextField.getText());
            if (maxPyramidHeight > defaultMaxSkillGridHeight + 1){
                throw new Exception("too low defaultMaxPyramidHeight");
            }
            SkillDistributor skillDistributor = new SkillDistributor(skillGridWidth,
                    maxPyramidHeight,
                    new SkillShuffler(skillGrid.getDisabledSkillTextFieldInput()));
            ArrayList<ArrayList<String>> skillPyramid = skillDistributor
                    .distributeSkillPoints(
                    skillGrid.getDisabledSkillColumnIndexes(),
                            skillPoints - skillGrid.countDisabledSkillPoints());
            skillGrid.setSkillGridFromArray(skillPyramid);

            skillPointsLeftLabel.setText("SP left after generation : " +
                    (skillPoints - skillGrid.countSpentSkillPoints()));
        } catch (Exception e) {
            skillPointsLeftLabel.setText(e.getMessage());
        }
    }

    private void fillCharacterAspects() {
        ArrayList<String> disabledAspectTextFieldInput = new ArrayList<>();
        for (int i = 0; i < aspectCheckboxes.size(); i++) {
            if (!aspectCheckboxes.get(i).isSelected()){
                disabledAspectTextFieldInput.add(aspectFields.get(i).getText());
            }
        }
        ArrayList<String> aspects = aspectRandomizer.generateAspects(disabledAspectTextFieldInput);
        for (int i = 0; i < aspectCheckboxes.size(); i++) {
            if (!aspectCheckboxes.get(i).isSelected()){
                aspectFields.get(i).setText(aspects.get(i));
            }
        }
    }

    private void fillGeneratedCharacterTextFields(){
        fillCharacterAspects();
        fillCharacterSkills();
    }

    @FXML
    private void initialize() {
        skillPointsTextField.setText(String.valueOf(defaultSkillPoints));
        skillCapTextField.setText(String.valueOf(defaultMaxSkillGridHeight));
        GeneratorVBoxCreator generatorVBoxCreator = new GeneratorVBoxCreator(
                defaultMaxSkillGridHeight, skillGridWidth,aspectRandomizer.getAspectCount());
        characterVBox.getChildren().addAll(
                generatorVBoxCreator.createAspectFieldsAndCheckboxes(),
                generatorVBoxCreator.createSkillFieldsAndCheckBoxes());
        aspectFields = generatorVBoxCreator.getAspectFields();
        aspectCheckboxes = generatorVBoxCreator.getAspectCheckboxes();
        skillGrid = new SkillGrid(generatorVBoxCreator.getSkillGrid());
        loadCache();
    }

    public void setTextFields(CharacterDTO characterDTO){
        for (int i = 0; i < characterDTO.aspects.size(); i++) {
            aspectFields.get(i).setText(characterDTO.aspects.get(i));
        }
        skillGrid.setSkillGridFromArray(characterDTO.skillGrid);
    }

    @FXML
    public void onGenerateButtonClick() {
        try {
            fillGeneratedCharacterTextFields();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onEditSkillsButtonClick(ActionEvent actionEvent){
        fillCache();
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/SkillEditorView.fxml");
        sceneChanger.changeScene();
    }

    public void onClipboardButtonClick() {
        String output = characterSaver.parseCharacter(createCharacter());
        System.out.println(output);
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(output);
        clipboard.setContent(content);
    }

    public CharacterDTO createCharacter(){
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.aspects = new ArrayList<>(aspectFields.stream().map(TextField::getText).toList());
        characterDTO.skillGrid = skillGrid.getSkillGridArray();
        return characterDTO;
    }

    public void loadButtonClick(ActionEvent actionEvent) {
        fillCache();
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/CharacterLoaderView.fxml");
        sceneChanger.changeScene();
    }

    private ArrayList<Integer> getDisabledAspectIndexes(){
        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < aspectCheckboxes.size(); i++) {
            if (aspectCheckboxes.get(i).isSelected()){
                output.add(i);
            }
        }
        return output;
    }

    private void disableAspectFields(ArrayList<Integer> indexes){
        for (int i:
             indexes) {
            aspectFields.get(i).setDisable(true);
            aspectCheckboxes.get(i).setSelected(true);
        }
    }

    private void setSkillPointsTextFieldText(Integer value){
        skillPointsTextField.setText(value.toString());
    }

    private void setSkillCapTextFieldText(Integer value){
        skillCapTextField.setText(value.toString());
    }

    private void loadCache(){
        Cache cache = Cache.gerInstance();
        cache.characterDTO.ifPresent(this::setTextFields);
        cache.disabledAspectIndexes.ifPresent(skillGrid::setDisabledSkillColumns);
        cache.disabledAspectIndexes.ifPresent(this::disableAspectFields);
        cache.skillPoints.ifPresent(this::setSkillPointsTextFieldText);
        cache.skillCap.ifPresent(this::setSkillCapTextFieldText);

    }

    private void fillCache(){
        Cache cache = Cache.gerInstance();
        cache.characterDTO = Optional.of(createCharacter());
        cache.disabledSkillColumnIndexes = Optional.of(skillGrid.getDisabledSkillColumnIndexes());
        cache.disabledAspectIndexes = Optional.of(getDisabledAspectIndexes());
        cache.skillPoints = Optional.of(Integer.parseInt(skillPointsTextField.getText()));
        cache.skillCap = Optional.of(Integer.parseInt(skillCapTextField.getText()));
    }
}

