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
    private GameClient gameClient;
    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void switchToOne(ActionEvent event) throws IOException {
        System.out.println("1");
        Parent root = FXMLLoader.load(getClass().getResource("/client/view/gamescene.fxml"));
         stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void switchToCreateRoom(ActionEvent event) throws IOException {
        System.out.println("1");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/roomscene.fxml"));
        //root = FXMLLoader.load(getClass().getResource("/client/view/roomscene.fxml"));
        Parent root = fxmlLoader.load();
//        RoomController helloController = fxmlLoader.getController();
//        helloController.setGameClient(this.gameClient);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/view/roomscene.fxml"));
//        Parent root = loader.load();
//        RoomController roomController = loader.getController();
//        roomController.setGameClient(gameClient);
//
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.show();

    }

}
