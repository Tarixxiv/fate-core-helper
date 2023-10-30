package com.fatecorehelper.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {
    final ActionEvent actionEvent;
    final String newSceneURL;
    FXMLLoader loader;

    public SceneChanger(ActionEvent actionEvent, String newSceneURL) {
        this.actionEvent = actionEvent;
        this.newSceneURL = newSceneURL;
        loader = new FXMLLoader(getClass().getResource(newSceneURL));

    }

    public void changeScene() {
        try {
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getController(){
        return loader.getController();
    }
}
