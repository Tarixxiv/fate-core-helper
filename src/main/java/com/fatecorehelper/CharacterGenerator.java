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
import java.io.IOException;
import java.util.ArrayList;

public class CharacterGenerator extends Application {
    AspectRandomizer aspectRandomizer = new AspectRandomizer();
    SkillPointDistributor skillPointDistributor = new SkillPointDistributor();
    Label skillPointsLeftLabel = new Label();
    ArrayList<CheckBox> aspectCheckboxes = new ArrayList<>();
    ArrayList<TextField> aspectFields = new ArrayList<>();
    ArrayList<SkillColumn> skillGrid = new ArrayList<>();
    TextField skillPointsInput = new TextField(String.valueOf(skillPointDistributor.defaultSkillPoints));
    TextField maxPyramidHeightInput = new TextField(String.valueOf(skillPointDistributor.maxPyramidHeight));
    ArrayList<String> disabledAspectTextFieldInput = new ArrayList<>();


    public CharacterGenerator() {
    }

    private void generatePyramid() throws IOException {
        skillPointDistributor.distributeSkillPoints(skillGrid, Integer.parseInt(skillPointsInput.getText()));
        for (int i = 0; i < skillPointDistributor.pyramidWidth; i++) {
            if (!skillGrid.get(i).isDisabled()){
                skillGrid.get(i).parseSkills(skillPointDistributor.skillPyramid.get(i));
            }
        }
    }

    public void generateCharacter() throws IOException {
        ArrayList<String> aspects = aspectRandomizer.generateAspects(disabledAspectTextFieldInput);
        for (int i = 0; i < aspectCheckboxes.size(); i++) {
            if (!aspectCheckboxes.get(i).isSelected()){
                aspectFields.get(i).setText(aspects.get(i));
            }
        }
        generatePyramid();
        skillPointsLeftLabel.setText("SP left after generation : " + skillPointDistributor.skillPointsLeft);
    }

    VBox createAspectFieldsAndCheckboxes(){
        VBox output = new VBox();
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
            output.getChildren().add(hbox);
        }
        return output;
    }

    VBox createSkillFieldsAndCheckBoxes() {
        VBox output = new VBox();
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
            ArrayList<TextField> textFieldsColumn = new ArrayList<>();
            CheckBox checkBox = new CheckBox();
            SkillColumn skillColumn = new SkillColumn(textFieldsColumn,checkBox);
            skillGrid.add(skillColumn);
            grid.add(checkBox,i + 1,0);
            for (int j = 0; j < maxPyramidHeight; j++) {
                HBox hbox = new HBox(8);
                TextField textField = new TextField();
                textFieldsColumn.add(textField);
                hbox.getChildren().addAll(textField);
                grid.add(hbox,i + 1,j + 1);
            }
        }
        output.getChildren().add(grid);
        output.getChildren().add(skillPointsLeftLabel);
        return output;
    }

    VBox createSkillPointsVbox(){
        VBox output = new VBox();
        output.getChildren().add(new Label("Skill points:"));
        skillPointsInput.setMaxWidth(40);
        output.getChildren().add(skillPointsInput);
        output.setPadding(new Insets(0,40,0,0));
        return output;
    }

    VBox createMaxPyramidHeightVbox(){
        VBox output = new VBox();
        output.getChildren().add(new Label("Max pyramid height:"));
        maxPyramidHeightInput.setMaxWidth(40);
        output.getChildren().add(maxPyramidHeightInput);
        return output;
    }

    HBox createPropertiesFields(){
        HBox output = new HBox();
        output.getChildren().addAll(createSkillPointsVbox(),createMaxPyramidHeightVbox());
        return output;
    }

    @Override
    public void start(Stage stage) throws IOException {
        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(createAspectFieldsAndCheckboxes(),
                createSkillFieldsAndCheckBoxes(),createPropertiesFields());
        generateCharacter();
        Button button = new Button("Generate");
        button.setOnAction(event -> {
            try {
                generateCharacter();
            } catch (IOException e) {
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