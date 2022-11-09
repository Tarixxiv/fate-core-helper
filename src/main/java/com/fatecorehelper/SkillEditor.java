package com.fatecorehelper;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SkillEditor {
    VBox vbox = new VBox();
    Scene generatorScene;
    Scene scene;
    Stage stage;
    SkillEditor(Stage newStage, Scene newGeneratorScene){
        generatorScene = newGeneratorScene;
        stage = newStage;
        vbox.getChildren().add(createReturnButton());
        scene = new Scene(vbox,newGeneratorScene.getWidth(),newGeneratorScene.getHeight());

    }

    Button createReturnButton(){
        Button button = new Button("Return");
        button.setOnAction(event-> stage.setScene(generatorScene));
        return button;
    }

    Scene getScene(){
        return scene;
    }


//    private Task<String> fileLoaderTask(File fileToLoad){
//        //Create a task to load the file asynchronously
//        Task<String> loadFileTask = new Task<>() {
//            @Override
//            protected String call() throws Exception {
//                BufferedReader reader = new BufferedReader(new FileReader(fileToLoad));
//
//                //Use Files.lines() to calculate total lines - used for progress
//                long lineCount;
//                try (Stream<String> stream = Files.lines(fileToLoad.toPath())) {
//                    lineCount = stream.count();
//                }
//
//                //Load in all lines one by one into a StringBuilder separated by "\n" - compatible with TextArea
//                String line;
//                StringBuilder totalFile = new StringBuilder();
//                long linesLoaded = 0;
//                while((line = reader.readLine()) != null) {
//                    totalFile.append(line);
//                    totalFile.append("\n");
//                    updateProgress(++linesLoaded, lineCount);
//                }
//
//                return totalFile.toString();
//            }
//        };
//
//        //If successful, update the text area, display a success message and store the loaded file reference
//        loadFileTask.setOnSucceeded(workerStateEvent -> {
//            try {
//                textArea.setText(loadFileTask.get());
//                statusMessage.setText("File loaded: " + fileToLoad.getName());
//                loadedFileReference = fileToLoad;
//            } catch (InterruptedException | ExecutionException e) {
//                Logger.getLogger(getClass().getName()).log(SEVERE, null, e);
//                textArea.setText("Could not load file from:\n " + fileToLoad.getAbsolutePath());
//            }
//        });
//
//        //If unsuccessful, set text area with error message and status message to failed
//        loadFileTask.setOnFailed(workerStateEvent -> {
//            textArea.setText("Could not load file from:\n " + fileToLoad.getAbsolutePath());
//            statusMessage.setText("Failed to load file");
//        });
//
//        return loadFileTask;
//    }
}
