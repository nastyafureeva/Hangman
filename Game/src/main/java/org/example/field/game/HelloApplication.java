package org.example.field.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("firstscene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hangman");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}