package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {
    //    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
//        System.out.println(this.getClass().getResource("/client/view/firstscene.fxml"));
//
//        stage = primaryStage;
//        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/client/view/firstscene.fxml"));
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        System.out.println("Client");
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("/client/view/firstscene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hangman");
        stage.setScene(scene);
        stage.show();
    }
//    public static Stage getStage() {
//        return stage;
//    }
//
//    public static void setScene(Scene scene) {
//        stage.setScene(scene);
//    }

    public static void main(String[] args) {
        launch();
    }
}