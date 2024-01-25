package com.fatecorehelper.controller;

import com.fatecorehelper.generator.business.FileReader;
import com.fatecorehelper.generator.business.FileWriter;
import com.fatecorehelper.model.Cache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SkillEditorController {
    public Button returnNoSaveButton;
    @FXML
    Button resetButton;
    @FXML
    VBox vBox;
    @FXML
    Button returnButton;
    @FXML
    TextArea textArea;
    FileWriter fileWriter = new FileWriter();
    FileReader fileReader = new FileReader();
    final static String skillsPath = "src/main/data/Skills";

    @FXML
    private void initialize() {
        initialiseTextArea();
    }

    private void initialiseTextArea(){
        ArrayList<String> parsedFile;
        try{
            parsedFile = fileReader.parseFileLinesToArray(skillsPath);
        } catch (FileNotFoundException e) {
            parsedFile = new ArrayList<>(List.of("FileNotFound"));
        }

        textArea.setText(String.join("\n",parsedFile));
    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
        Cache.gerInstance().skills = Optional.of(new ArrayList<>(
                List.of(textArea.getText().split("\n"))
        ));
        try{
            fileWriter.saveToFile(textArea.getText(),skillsPath);
        }
        catch (IOException ignored){

        }
        onReturnNoSaveButtonButtonClick(actionEvent);
    }

    public void onResetButtonClick() {
        ArrayList<String> parsedDefaultSkills = fileReader.parseResourceLinesToArray("DefaultSkills");
        try {
            fileWriter.saveToFile(String.join("\n",parsedDefaultSkills),skillsPath);
        } catch (IOException ignored) {}
        initialiseTextArea();
    }

    public void onReturnNoSaveButtonButtonClick(ActionEvent actionEvent) {
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/GeneratorView.fxml");
        sceneChanger.changeScene();
    }
}
