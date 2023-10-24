package com.fatecorehelper.controller;

import com.fatecorehelper.controller.util.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SkillEditorController {
    @FXML
    VBox vBox;
    @FXML
    Button returnButton;
    @FXML
    TextArea textArea;


    private void initializeTextArea() throws IOException {
        Scanner s = new Scanner(new File("src/main/data/Skills"), StandardCharsets.UTF_8);
        textArea.setText(s.useDelimiter("\\Z").next());
        s.close();
    }

    private void saveToFile(){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("src/main/data/Skills"),StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);
            writer.write(textArea.getText());
            writer.close();
            osw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() throws Exception {
        initializeTextArea();
    }

    public void onReturnButtonClick(ActionEvent actionEvent) throws IOException {
        saveToFile();
        SceneChanger sceneChanger = new SceneChanger(actionEvent,"fxml/GeneratorView.fxml");
        sceneChanger.changeScene();
    }
}
