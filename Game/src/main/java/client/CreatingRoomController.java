package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class CreatingRoomController {
    @FXML
    private TextField roomNameTextField;

    @FXML
    private CheckBox privateCheckBox;
    GameClient gameClient;
    private RoomController roomController;


    public void setRoomController(RoomController roomController) {
        this.roomController = roomController;
    }

    public void create(ActionEvent actionEvent) {
        String roomName = roomNameTextField.getText();
        gameClient.sendMessageToServerAsync("roomname:" + roomName);
        closeDialog();
        if (roomController != null) {
            roomController.fetchRoomsAndShow();
        }
    }

    public void initialize() {
        this.gameClient = new GameClient();
        this.gameClient.start();

    }

    private void closeDialog() {
        Stage stage = (Stage) roomNameTextField.getScene().getWindow();
        stage.close();
    }

}
