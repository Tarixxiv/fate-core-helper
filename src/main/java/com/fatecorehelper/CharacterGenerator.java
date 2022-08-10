package com.fatecorehelper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CharacterGenerator extends Application {
    AspectRandomizer aspectRandomizer = new AspectRandomizer();
    SkillPointDistributor skillPointDistributor = new SkillPointDistributor();

    ArrayList<CheckBox> aspectCheckboxes = new ArrayList<>();
    ArrayList<TextField> aspectFields = new ArrayList<>();

    public CharacterGenerator() {
    }

    public void regenerate() throws FileNotFoundException {
        ArrayList<String> aspects = aspectRandomizer.generateAspects();
        for (int i = 0; i < aspectCheckboxes.size(); i++) {
            if (!aspectCheckboxes.get(i).isSelected()){
                aspectFields.get(i).setText(aspects.get(i));
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        VBox vbox = new VBox(8);
        ArrayList<String> aspects = aspectRandomizer.generateAspects();
        for (String aspect:
             aspects) {
            HBox hbox = new HBox(8);
            TextField textField = new TextField(aspect);
            textField.setPrefWidth(400);
            CheckBox checkBox = new CheckBox();
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()){
                    textField.setDisable(true);
                }else{
                    textField.setDisable(false);
                }
            });
            hbox.getChildren().addAll(checkBox,textField);
            aspectCheckboxes.add(checkBox);
            aspectFields.add(textField);
            vbox.getChildren().add(hbox);
        }
        skillPointDistributor.distributeSkillPoints();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        int maxPyramidHeight = skillPointDistributor.maxPyramidHeight;
        for (int i = 0; i < maxPyramidHeight; i++) {
            Label label = new Label("+" + (maxPyramidHeight - i));
            grid.add(label,0,i);
        }
        for (int i = 0; i < skillPointDistributor.skillPyramid.size(); i++) {
            for (int j = 0; j < maxPyramidHeight; j++) {
                HBox hbox = new HBox(8);
                String text = "";
                if (skillPointDistributor.skillPyramid.get(i).size() > maxPyramidHeight - j - 1){
                    text = skillPointDistributor.skillPyramid.get(i).get(maxPyramidHeight - j - 1);
                }
                hbox.getChildren().addAll(new CheckBox(), new TextField(text));
                grid.add(hbox,i + 1,j);
            }

        }
        vbox.getChildren().add(grid);
        vbox.getChildren().add(new Label("SP left after generation : " + skillPointDistributor.skillPointsLeft));
        Button button = new Button("Generate");
        button.setOnAction(event -> {
            try {
                regenerate();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        vbox.getChildren().add(button);
        vbox.setPadding(new Insets(30));
        Scene scene = new Scene(vbox, 1000,600);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}