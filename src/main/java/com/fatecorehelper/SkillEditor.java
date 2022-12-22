package com.fatecorehelper;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SkillEditor {
    VBox vbox = new VBox();
    Scene generatorScene;
    TextArea textArea;
    Scene scene;
    Stage stage;
    SkillEditor(Stage newStage, Scene newGeneratorScene) throws IOException {
        generatorScene = newGeneratorScene;
        stage = newStage;
        vbox.getChildren().addAll(createTextArea(),createReturnButton());
        vbox.setPadding(new Insets(30));
        scene = new Scene(vbox,newGeneratorScene.getWidth(),newGeneratorScene.getHeight());

    }

    TextArea createTextArea() throws IOException {
        Scanner s = new Scanner(new File("src/main/data/Skills"), StandardCharsets.UTF_8);
        textArea = new TextArea(s.useDelimiter("\\Z").next());
        s.close();
        return textArea;
    }

    Button createReturnButton(){
        Button button = new Button("Return");

        button.setOnAction(event-> {
            try {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("src/main/data/Skills"),StandardCharsets.UTF_8);
                BufferedWriter writer = new BufferedWriter(osw);
                writer.write(textArea.getText());
                writer.close();
                osw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(generatorScene);
        });

        return button;
    }
    Scene getScene(){
        return scene;
    }
}
