package com.fatecorehelper.controller;

import com.fatecorehelper.controller.util.CharacterLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class CharacterLoaderController {

    public Button loadButton;
    public Button returnButton;
    public TextArea textArea;
    CharacterLoader characterLoader = new CharacterLoader();

    @FXML
    private void initialize() {
    }

    public void onLoadButtonClick(ActionEvent actionEvent) {
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/GeneratorView.fxml");
        String textAreaBuffer = textArea.getText();

        sceneChanger.changeScene();
        GeneratorController generatorController = (GeneratorController) sceneChanger.getController();
        generatorController.setCharacter(characterLoader.load(textAreaBuffer));
    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/GeneratorView.fxml");
        sceneChanger.changeScene();
    }

    public void setTextAreaText(String textAreaText) {
        textArea.setText(textAreaText);
    }
}
