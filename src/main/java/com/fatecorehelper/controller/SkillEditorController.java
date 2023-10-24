package com.fatecorehelper.controller;

import com.fatecorehelper.generator.business.FileParser;
import com.fatecorehelper.generator.business.SkillEditor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import java.util.ArrayList;


public class SkillEditorController {
    @FXML
    Button resetButton;
    @FXML
    VBox vBox;
    @FXML
    Button returnButton;
    @FXML
    TextArea textArea;
    SkillEditor skillEditor = new SkillEditor();
    FileParser fileParser = new FileParser();
    String skillsPath = "src/main/data/Skills";

    @FXML
    private void initialize() {
        initialiseTextArea();
    }

    private void initialiseTextArea(){
        ArrayList<String> parsedFile = fileParser.parseFileLinesToArray(skillsPath);
        textArea.setText(String.join("\n",parsedFile));
    }

    public void onReturnButtonClick(ActionEvent actionEvent) {
        skillEditor.saveToFile(textArea.getText(),skillsPath);
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/GeneratorView.fxml");
        sceneChanger.changeScene();
    }


    public void onrResetButtonClick(ActionEvent actionEvent) {
        ArrayList<String> parsedDefaultSkills = fileParser.parseResourceLinesToArray("DefaultSkills");
        skillEditor.saveToFile(String.join("\n",parsedDefaultSkills),skillsPath);
        initialiseTextArea();
    }
}
