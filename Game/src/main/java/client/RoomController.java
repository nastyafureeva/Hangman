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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static javafx.application.Application.launch;

public class RoomController {
    @FXML
    private ListView<String> roomListView;
    @FXML
    private Pane mainPane;
    private Stage dialogStage;

    GameClient gameClient;

    public RoomController() throws IOException {
    }

    public void fetchRoomsAndShow() {
        OnMessageReceivedListener t = this::showRooms;
        this.gameClient.sendMessageToServerAsync("getrooms:");
        this.gameClient.addMessageReceivedListener(t);
        this.gameClient.removeMessageReceivedListener(t);
    }

    //    public void delete(){
//        this.gameClient.removeMessageReceivedListener(this::showRooms);
//    }
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
        this.gameClient.sendMessageToServerAsync("getrooms:");
        this.gameClient.addMessageReceivedListener(this::showRooms);
        this.gameClient.removeMessageReceivedListener(this::showRooms);
        System.out.println(this.gameClient.listeners);
        this.dialogStage = new Stage();
        roomListView.setOnMouseClicked(event -> {
            String selectedRoom = roomListView.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
             connectToRoom(selectedRoom);
            }
        });


    }

    public void connectToRoom(String roomName) {
        this.gameClient.sendMessageToServerAsync("connectroom:" + roomName);
        System.out.println("tbid" + gameClient.messageRes);
        this.gameClient.addMessageReceivedListener(this::readerConnecting);

    }

    private void readerConnecting(String message) {
        if (message.equals("connected1")) {
            Platform.runLater(() -> {
                switchToGameScene2();
            });

        }
    }

    private void switchToGameScene2() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/waiting.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void showRooms(String message) {
        System.out.println("prishlo" + message);
        if (!message.equals(null) && !message.equals("successed")) {
            Platform.runLater(() -> {
                String[] names = message.split(":");
                List<String> rooms = Arrays.asList(names);
                System.out.println("rooms " + rooms);
                this.roomListView.getItems().setAll(rooms);

            });
        }
    }


}
