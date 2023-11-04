package com.fatecorehelper.controller;

import com.fatecorehelper.generator.business.CharacterLoader;
import com.fatecorehelper.generator.business.CharacterSaver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import static com.fatecorehelper.FateCoreHelperApp.characterBufferPath;


public class CharacterLoaderController {
    public Button loadButton;
    public Button returnButton;
    public TextArea textArea;
    CharacterLoader characterLoader = new CharacterLoader();
    CharacterSaver characterSaver = new CharacterSaver();

    @FXML
    private void initialize() {
        textArea.setText(characterSaver.parseCharacter(characterLoader.loadFromFile(characterBufferPath)));
    }

    public void onLoadButtonClick(ActionEvent actionEvent) {
        characterSaver.saveToFile(characterLoader.loadFromString(textArea.getText()),characterBufferPath);
        onReturnButtonClick(actionEvent);
    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/GeneratorView.fxml");
        sceneChanger.changeScene();
    }

}
