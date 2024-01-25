package com.fatecorehelper.controller;

import com.fatecorehelper.generator.business.CharacterLoader;
import com.fatecorehelper.generator.business.CharacterSaver;
import com.fatecorehelper.model.CharacterDTO;
import com.fatecorehelper.model.Cache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.Optional;



public class CharacterLoaderController {
    public Button loadButton;
    public Button returnButton;
    public TextArea textArea;
    CharacterLoader characterLoader = new CharacterLoader();
    CharacterSaver characterSaver = new CharacterSaver();

    @FXML
    private void initialize() {
        Cache cache = Cache.gerInstance();
        cache.characterDTO.ifPresent(characterDTO -> textArea.setText(characterSaver.parseCharacter(characterDTO)));

    }

    public void onLoadButtonClick(ActionEvent actionEvent) {
        CharacterDTO characterDTO = characterLoader.loadFromString(textArea.getText());
        Cache.gerInstance().characterDTO = Optional.of(characterDTO);
        onReturnButtonClick(actionEvent);
    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/GeneratorView.fxml");
        sceneChanger.changeScene();
    }

}
