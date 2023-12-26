package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static javafx.application.Application.launch;

public class RoomController {
    @FXML
    private ListView<String> roomListView;
    private Stage dialogStage;

    GameClient gameClient;
    public RoomController(){
    }
    public void fetchRoomsAndShow() {
        this.gameClient.sendMessageToServerAsync("getrooms:");
        this.gameClient.addMessageReceivedListener(this::showRooms);

    }
    public void createRoom(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/view/createroomscene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 300);
        dialogStage.setScene(scene);
        dialogStage.show();

        CreatingRoomController creatingRoomController = loader.getController();
        creatingRoomController.setRoomController(this);
    }

//        public void createRoom(ActionEvent event) throws Exception {
//        dialogStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/client/view/createroomscene.fxml"));
//        Scene scene = new Scene(root, 400, 300);
//        dialogStage.setScene(scene);
//        dialogStage.show();
//    }

    public void initialize() {
        this.gameClient = new GameClient();
        this.gameClient.start();
        gameClient.sendMessageToServerAsync("getrooms:");
        gameClient.addMessageReceivedListener(this::showRooms);
        this.dialogStage = new Stage();
    }

    public void showRooms(String message){
        System.out.println("prishlo" + message);
        if (!message.equals(null)) {
            Platform.runLater(() -> {
                String[] names = message.split(":");
                System.out.println(names[0]);
                List<String> rooms = Arrays.asList(names);
                System.out.println("rooms " + rooms);
               this.roomListView.getItems().setAll(rooms);

            });
        }
    }


}
