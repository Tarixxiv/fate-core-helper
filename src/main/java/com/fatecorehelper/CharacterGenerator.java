package com.fatecorehelper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CharacterGenerator extends Application {
    AspectRandomizer aspectRandomizer = new AspectRandomizer();
    SkillPointDistributor skillPointDistributor = new SkillPointDistributor();
    ArrayList<CheckBox> aspectCheckboxes = new ArrayList<>();
    ArrayList<TextField> aspectFields = new ArrayList<>();

    public CharacterGenerator() {
    }

    public void generateCharacter() throws FileNotFoundException {
        ArrayList<String> aspects = aspectRandomizer.generateAspects();
        for (int i = 0; i < aspectCheckboxes.size(); i++) {
            if (!aspectCheckboxes.get(i).isSelected()){
                aspectFields.get(i).setText(aspects.get(i));
            }
        }
    }

    void createAspectFieldsAndCheckboxes(VBox vbox){
        for (int i = 0; i < aspectRandomizer.getAspectCount(); i++) {
            HBox hbox = new HBox(8);
            TextField textField = new TextField();
            textField.setPrefWidth(400);
            CheckBox checkBox = new CheckBox();
            checkBox.setOnAction(event -> textField.setDisable(checkBox.isSelected()));
            hbox.getChildren().addAll(checkBox,textField);
            aspectCheckboxes.add(checkBox);
            aspectFields.add(textField);
            vbox.getChildren().add(hbox);
        }
    }

    void createSkillFieldsAndCheckBoxes(VBox vbox) throws IOException {
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
    }

    @Override
    public void start(Stage stage) throws IOException {
        VBox vbox = new VBox(8);
        createAspectFieldsAndCheckboxes(vbox);
        generateCharacter();
        createSkillFieldsAndCheckBoxes(vbox);

        Button button = new Button("Generate");
        button.setOnAction(event -> {
            try {
                generateCharacter();
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