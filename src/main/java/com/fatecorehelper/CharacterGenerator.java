package com.fatecorehelper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CharacterGenerator extends Application {
    AspectRandomizer aspectRandomizer = new AspectRandomizer();
    SkillPointDistributor skillPointDistributor = new SkillPointDistributor();

    public CharacterGenerator() throws FileNotFoundException {
    }

    @Override
    public void start(Stage stage) throws IOException {
        ArrayList<String> aspects = aspectRandomizer.generateAspects();
        System.out.println(aspects);
        skillPointDistributor.distributeSkillPoints();
        skillPointDistributor.printPyramid();
        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        int pyramidHeight = skillPointDistributor.skillPyramid.get(0).size();
        for (int i = 0; i < pyramidHeight; i++) {
            Label label = new Label("+" + (pyramidHeight - i));
            grid.add(label,0,i);
        }
        for (int i = 0; i < skillPointDistributor.skillPyramid.size(); i++) {
            for (int j = 0; j < pyramidHeight; j++) {
                HBox hbox = new HBox(8);
                String text = "";
                if (skillPointDistributor.skillPyramid.get(i).size() > pyramidHeight - j - 1){
                    text = skillPointDistributor.skillPyramid.get(i).get(pyramidHeight - j - 1);
                }
                hbox.getChildren().addAll(new CheckBox(), new TextField(text));
                grid.add(hbox,i + 1,j);
            }
            grid.add(new Label("SP left : " + skillPointDistributor.skillPointsLeft),0,pyramidHeight);
        }
        Scene scene = new Scene(grid, 1000,250);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}