package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    private Pane man;
    @FXML
    private Pane base1;
    @FXML
    private Pane base2;
    @FXML
    private Pane base3;
    @FXML
    private Pane pole;
    @FXML
    private Pane rod;
    @FXML
    private Pane rope1;
    @FXML
    private Pane rope2;
    @FXML
    private Text text;
    @FXML
    private Pane buttons;
    @FXML
    private Text winStatus;
    @FXML
    private Text realWord;
    @FXML
    private Text score;

    GameClient gameClient;

    public HelloController(GameClient gameClient) {
        this.gameClient = gameClient;

    }


        public void onClick(ActionEvent event) {
        String letter = ((Button) event.getSource()).getText();
        ((Button) event.getSource()).setDisable(true);
        gameClient.sendMessageToServerAsync(letter);
       // System.out.println(gameClient.messageRes);
    }
    public void ksld(String message){
        String[] info = message.split(":");
        text.setText(info[0]);
        System.out.println("rjvfylfk" + info[1]);
        if (info[1] == "win") {
            winStatus.setText("Победа!");
            buttons.setDisable(true);
        } else if (info[1].equals("base1")) base1.setVisible(true);
        else if (info[1].equals("base2")) base2.setVisible(true);
        else if (info[1].equals("base3")) base3.setVisible(true);
        else if (info[1].equals("pole")) pole.setVisible(true);
        else if (info[1].equals("rod")) rod.setVisible(true);
        else if (info[1].equals("rope1")) rope1.setVisible(true);
        else if (info[1].equals("rope2")) rope2.setVisible(true);
        else if (info[1].equals("lost")) {
            rope2.setVisible(false);
            man.setVisible(true);
            winStatus.setText("Ты проиграл!");
            realWord.setText("Загаданное слово: " + info[2]);
            buttons.setDisable(true);

        }
        text.setText(info[0]);
    }

    public void newGame() {
        for (int i = 0; i < 33; i++) {
            buttons.getChildren().get(i).setDisable(false);
        }
        initialize();
    }

    public void initialize() {
        //HelloController helloController = new HelloController(new GameClient());
        this.gameClient = new GameClient();
      this.gameClient.start();
        gameClient.sendMessageToServerAsync("myWord");
        base1.setVisible(false);
        base2.setVisible(false);
        base3.setVisible(false);
        pole.setVisible(false);
        rod.setVisible(false);
        rope1.setVisible(false);
        rope2.setVisible(false);
        man.setVisible(false);
        System.out.println(gameClient.messageRes);
        text.setText(gameClient.messageRes);
        winStatus.setText("");
        realWord.setText("");
        buttons.setDisable(false);
        score.setText("Количество очков: 100");
    }

}