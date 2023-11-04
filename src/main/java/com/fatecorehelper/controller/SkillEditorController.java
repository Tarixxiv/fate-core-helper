package com.fatecorehelper.controller;

import com.fatecorehelper.generator.business.FileReader;
import com.fatecorehelper.generator.business.FileWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class SkillEditorController {
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
    String skillsPath = "src/main/data/Skills";

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
        fileWriter.saveToFile(textArea.getText(),skillsPath);
        onReturnNoSaveButtonButtonClick(actionEvent);
    }

    public void onrResetButtonClick() {
        ArrayList<String> parsedDefaultSkills = fileReader.parseResourceLinesToArray("DefaultSkills");
        fileWriter.saveToFile(String.join("\n",parsedDefaultSkills),skillsPath);
        initialiseTextArea();
    }

    public void onReturnNoSaveButtonButtonClick(ActionEvent actionEvent) {
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/GeneratorView.fxml");
        sceneChanger.changeScene();
    }
}
