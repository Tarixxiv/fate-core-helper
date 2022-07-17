package com.fatecorehelper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    AspectRandomizer aspectRandomizer = new AspectRandomizer();
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("here");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //stage.setTitle(aspectRandomizer.reservoirSampleFileLines("src/main/data/Skills"));
        ArrayList<String> aspects = aspectRandomizer.generateAspects();
        System.out.println(aspects);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}