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
    Label skillPointsLeftLabel = new Label();
    ArrayList<CheckBox> aspectCheckboxes = new ArrayList<>();
    ArrayList<TextField> aspectFields = new ArrayList<>();
    ArrayList<SkillColumn> skillColumns = new ArrayList<>();
    ArrayList<String> disabledAspectTextFieldInput = new ArrayList<>();

    ArrayList<String> disabledSkillTextFieldInput = new ArrayList<>();

    public CharacterGenerator() {
    }

    public void generateCharacter() throws FileNotFoundException {
        ArrayList<String> aspects = aspectRandomizer.generateAspects(disabledAspectTextFieldInput);
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
            checkBox.setOnAction(event -> {
                textField.setDisable(checkBox.isSelected());
                if (checkBox.isSelected()){
                    disabledAspectTextFieldInput.add(textField.getText());
                }else{
                    disabledAspectTextFieldInput.remove(textField.getText());
                }
            });
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
            grid.add(label,0,i + 1);
        }
        for (int i = 0; i < skillPointDistributor.pyramidWidth; i++) {
            ArrayList<TextField> skillColumn = new ArrayList<>();
            CheckBox checkBox = new CheckBox();
            SkillColumn columnCheckbox = new SkillColumn(skillColumn,checkBox);
            skillColumns.add(columnCheckbox);
            grid.add(checkBox,i + 1,0);
            for (int j = 0; j < maxPyramidHeight; j++) {
                HBox hbox = new HBox(8);
                String text = "";
                if (skillPointDistributor.skillPyramid.get(i).size() > maxPyramidHeight - j - 1){
                    text = skillPointDistributor.skillPyramid.get(i).get(maxPyramidHeight - j - 1);
                }
                TextField textField = new TextField(text);
                skillColumn.add(textField);
                hbox.getChildren().addAll(textField);
                grid.add(hbox,i + 1,j + 1);
            }
        }

/*        for (int i = 0; i < skillPointDistributor.pyramidWidth; i++) {
            for (int j = 0; j < maxPyramidHeight; j++) {
                String text = "";
                if (skillPointDistributor.skillPyramid.get(i).size() > maxPyramidHeight - j - 1){
                    text = skillPointDistributor.skillPyramid.get(i).get(maxPyramidHeight - j - 1);
                }

            }
        }*/

        vbox.getChildren().add(grid);
        skillPointsLeftLabel.setText("SP left after generation : " + skillPointDistributor.skillPointsLeft);
        vbox.getChildren().add(skillPointsLeftLabel);
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