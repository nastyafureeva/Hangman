package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static javafx.application.Application.launch;

public class RoomController {
    @FXML
    private ListView<String> roomListView;
    @FXML
    private Pane mainPane;
    private Stage dialogStage;
    private boolean isInitialized = false;


    GameClient gameClient;
    OnMessageReceivedListener roomListener;
    OnMessageReceivedListener roomListenerFor1;

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;  // Передача gameClient извне

        if (!isInitialized) {
            initialize();  // Явный вызов initialize, если контроллер еще не был инициализирован
            isInitialized = true;
        }

    }


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
        roomListener = this::showRooms;
        this.gameClient.addMessageReceivedListener(roomListener);
//        this.gameClient.removeMessageReceivedListener(t);
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
        this.gameClient.removeMessageReceivedListener(roomListener);
        this.roomListener = this::readerConnecting;
        this.gameClient.addMessageReceivedListener(roomListener);
//        this.gameClient.removeMessageReceivedListener(roomListener);

    }

    private void readerConnecting(String message) {
        String[] info = message.split(":");
        this.gameClient.idRoom = Integer.parseInt(info[1]);
        if (message.equals("connected1:" + this.gameClient.idRoom)) {
            this.gameClient.idGamer = 1;
            Platform.runLater(() -> {
                switchToGameWaiting();
            });
        } else if (message.equals("connected2:" + this.gameClient.idRoom)) {
            this.gameClient.idGamer = 2;
            Platform.runLater(() -> {
                switchToGameScene2();
            });
        }
    }

    private void switchToGameWaiting() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/waiting.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameClient.sendMessageToServerAsync("joingame1:" + this.gameClient.idRoom);
        this.roomListenerFor1 = this::switchToScene2FromGamer1;
        gameClient.addMessageReceivedListener(roomListenerFor1);
//            String [] info = message.split(":");
//            if (info[0].equals("connectedtoscene2")) {
//                Stage stage = (Stage) roomListView.getScene().getWindow();
//                dialogStage.close();
//                System.out.println("im here");
//                switchToGameScene2();
//            }
//        });

    }

    public void switchToScene2FromGamer1(String message) {
        String[] info = message.split(":");
        if (info[0].equals("connectedtoscene2")) {
            System.out.println("im here");
            Platform.runLater(() -> {
                Stage stage = (Stage) dialogStage.getScene().getWindow();
                stage.close();
                System.out.println("im here");
                switchToGameScene2();
            });
        }

    }

    private void switchToGameScene2() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/gamescene.fxml"));
            Parent root = fxmlLoader.load();
            ClientController clientController = fxmlLoader.getController();
            gameClient.removeMessageReceivedListener(roomListener);
            clientController.setGameClient(gameClient);
            mainPane.getChildren().setAll(root);
            if (roomListenerFor1 != null){
                gameClient.removeMessageReceivedListener(roomListenerFor1);
            }

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
