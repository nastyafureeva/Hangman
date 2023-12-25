package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Firstscene {
    Parent root;
    Stage stage;


    public void switchToCreateRoom(ActionEvent event) throws IOException {
        System.out.println("1");
         root = FXMLLoader.load(getClass().getResource("/client/view/gamescene.fxml"));
        System.out.println("1");
         stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
