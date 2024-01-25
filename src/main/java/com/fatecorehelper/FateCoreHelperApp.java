package com.fatecorehelper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FateCoreHelperApp extends Application {
    int stageHeight = 600;
    int stageWidth = 1000;
    public static int skillGridWidth = 6;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setHeight(stageHeight);
        stage.setWidth(stageWidth);
        FXMLLoader fxmlLoader = new FXMLLoader(FateCoreHelperApp.class.getResource("controller/fxml/GeneratorView.fxml"));
        VBox page  = fxmlLoader.load();
        Scene scene = new Scene(page);
        stage.setTitle("FateCoreHelperApp");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}