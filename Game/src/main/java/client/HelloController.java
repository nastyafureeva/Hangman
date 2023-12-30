package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    private Pane mainPane;
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
    @FXML
    private Text motion;

    GameClient gameClient;
    OnMessageReceivedListener helloListener;
    final int c = 1;


    public HelloController() {
    }

    public void getVisibleF() {
        Platform.runLater(() -> {
            buttons.setVisible(false);
            motion.setVisible(false);
        });
    }

    public void getVisibleT() {
        Platform.runLater(() -> {
            buttons.setVisible(true);
            motion.setVisible(true);
        });
    }


    public void onClick(ActionEvent event) {
        String letter = ((Button) event.getSource()).getText();
        ((Button) event.getSource()).setDisable(true);
        if (gameClient.idRoom == -1) {
            this.gameClient.sendMessageToServerAsync("1G:" + letter);
            gameClient.addMessageReceivedListener(this::reader);
            System.out.println("1G IMHERE");
            reader(gameClient.messageRes);
        } else {
            this.gameClient.sendMessageToServerAsync("2G:" + gameClient.idGamer + ":" + letter);
            this.helloListener = this::readerFor2;
            this.gameClient.addMessageReceivedListener(helloListener);
            System.out.println("2G IMHERE");
//            readerFor2(gameClient.messageRes);
        }
    }


    public String wichOne() {
        if (gameClient.idGamer == 1) {
            return "2";
        }
        return "1";
    }

    public void readerFor2(String message) {
        Platform.runLater(() -> {
            System.out.println("messageReader2 " + message);
            String[] info = message.split(":");
            gameClient.removeMessageReceivedListener(helloListener);
            if (info[0].equals(String.valueOf(gameClient.idGamer))) {
                System.out.println(info[1]);
                text.setText(info[1]);
                score.setText("Количество очков: " + (100 - Integer.parseInt(info[info.length - 1]) * 12.5));
                System.out.println("readerFor2 " + info[1]);
                if (info[2].equals("win")) {
                    winStatus.setText("Победа!");
                    buttons.setDisable(true);
                } else if (info[2].equals("base1")) {
                    base1.setVisible(true);
                    getVisibleF();
                    gameClient.sendMessageToServerAsync(wichOne() + ":YOUGO");
                    gameClient.sendMessageToServerAsync("" + gameClient.idGamer + ":waiting:" + gameClient.idRoom);
                    // this.helloListener = this::gaming;
                    gameClient.addMessageReceivedListener(this::gaming);
                } else if (info[2].equals("base2")) {
                    base2.setVisible(true);
                    getVisibleF();
                    // gameClient.removeMessageReceivedListener(helloListener);
                    gameClient.sendMessageToServerAsync(wichOne() + ":YOUGO");
                    gameClient.sendMessageToServerAsync("" + gameClient.idGamer + ":waiting:" + gameClient.idRoom);
//                    this.helloListener = this::gaming;
//                    gameClient.addMessageReceivedListener(helloListener);
                } else if (info[2].equals("base3")) {
                    base3.setVisible(true);
                    getVisibleF();
                    //gameClient.removeMessageReceivedListener(helloListener);
                    gameClient.sendMessageToServerAsync(wichOne() + ":YOUGO");
                    gameClient.sendMessageToServerAsync("" + gameClient.idGamer + ":waiting:" + gameClient.idRoom);
//                    this.helloListener = this::gaming;
//                    gameClient.addMessageReceivedListener(helloListener);
                } else if (info[2].equals("pole")) {
                    pole.setVisible(true);
                    getVisibleF();
                    // gameClient.removeMessageReceivedListener(helloListener);
                    gameClient.sendMessageToServerAsync(wichOne() + ":YOUGO");
                    gameClient.sendMessageToServerAsync("" + gameClient.idGamer + ":waiting:" + gameClient.idRoom);
//                    this.helloListener = this::gaming;
//                    gameClient.addMessageReceivedListener(helloListener);
                } else if (info[2].equals("rod")) {
                    rod.setVisible(true);
                    getVisibleF();
                    //   gameClient.removeMessageReceivedListener(helloListener);
                    gameClient.sendMessageToServerAsync(wichOne() + ":YOUGO");
                    gameClient.sendMessageToServerAsync("" + gameClient.idGamer + ":waiting:" + gameClient.idRoom);
//                    this.helloListener = this::gaming;
//                    gameClient.addMessageReceivedListener(helloListener);
                } else if (info[2].equals("rope1")) {
                    rope1.setVisible(true);
                    getVisibleF();
                    // gameClient.removeMessageReceivedListener(helloListener);
                    gameClient.sendMessageToServerAsync(wichOne() + ":YOUGO");
                    gameClient.sendMessageToServerAsync("" + gameClient.idGamer + ":waiting:" + gameClient.idRoom);
//                    this.helloListener = this::gaming;
//                    gameClient.addMessageReceivedListener(helloListener);
                } else if (info[2].equals("rope2")) {
                    rope2.setVisible(true);
                    getVisibleF();
                    //  gameClient.removeMessageReceivedListener(helloListener);
                    gameClient.sendMessageToServerAsync(wichOne() + ":YOUGO");
                    gameClient.sendMessageToServerAsync("" + gameClient.idGamer + ":waiting:" + gameClient.idRoom);
//                    this.helloListener = this::gaming;
//                    gameClient.addMessageReceivedListener(helloListener);
                } else if (info[2].equals("lost")) {
                    rope2.setVisible(false);
                    man.setVisible(true);
                    gameClient.sendMessageToServerAsync(wichOne() + ":YOUGO");
                    winStatus.setText("Ты проиграл!");
                    realWord.setText("Загаданное слово: " + info[info.length - 1]);
                    buttons.setDisable(true);

                }

            }
        });


    }

    public void updateScene(String message) {
        String[] info = message.split(":");
        boolean flag;

//        if (info[0].equals("lost")) {
//            flag = true;
//            String[] data = new String[info.length - 1];
//            for (int i = 0; i <= info.length - 1; i++) {
//                data[i] = info[i + 1];
//            }
//            info = data;
//        } else {
//            flag = false;
//        }
        if (info[2].equals("openedLetter")) {
            for (int i = 2; i < info.length; i++) {
                String buttonText = info[i];
                for (Node node : buttons.getChildren()) {
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        if (button.getText().equals(buttonText)) {
                            button.setDisable(true);
                            break;
                        }
                    }
                }
            }
        }

        int count;
//        if (flag) {
//            count = Integer.parseInt(info[info.length - 2]);
//        } else {
            count = Integer.parseInt(info[info.length - 1]);
        // }
        if (count == 1) base1.setVisible(true);
        if (count == 2) {
            base1.setVisible(true);
            base2.setVisible(true);
        }
        if (count == 3) {
            base1.setVisible(true);
            base2.setVisible(true);
            base3.setVisible(true);
        }
        if (count == 4) {
            base1.setVisible(true);
            base2.setVisible(true);
            base3.setVisible(true);
            pole.setVisible(true);
        }
        if (count == 5) {
            base1.setVisible(true);
            base2.setVisible(true);
            base3.setVisible(true);
            pole.setVisible(true);
            rod.setVisible(true);
        }
        if (count == 6) {
            base1.setVisible(true);
            base2.setVisible(true);
            base3.setVisible(true);
            pole.setVisible(true);
            rod.setVisible(true);
            rope1.setVisible(true);
        }
        if (count == 7) {
            base1.setVisible(true);
            base2.setVisible(true);
            base3.setVisible(true);
            pole.setVisible(true);
            rod.setVisible(true);
            rope2.setVisible(true);
        }
        if (count == 8) {
            base1.setVisible(true);
            base2.setVisible(true);
            base3.setVisible(true);
            pole.setVisible(true);
            rod.setVisible(true);
            rope2.setVisible(true);
            rope2.setVisible(false);
            man.setVisible(true);
            winStatus.setText("Ты проиграл!");
            realWord.setText("Загаданное слово: " + info[3]);
            buttons.setDisable(true);

        }

        Platform.runLater(() -> {
            text.setText(info[1]);
        });
//        gameClient.removeMessageReceivedListener(helloListener);

    }

    public void reader(String message) {
        Platform.runLater(() -> {
            String[] info = message.split(":");
            text.setText(info[0]);
            score.setText("Количество очков: " + (100 - Integer.parseInt(info[3]) * 12.5));
            System.out.println("rjvfylfk" + info[1]);
            if (info[1].equals("win")) {
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

        });


    }

    public void gaming(String message) {
        System.out.println("gaming");
        System.out.println(message);
        String[] info = message.split(":");
        if (info[0].equals("2G")) {
            readerFor2(message);
        }
        if (info[0].equals(String.valueOf(gameClient.idGamer))) {
            getVisibleT();
            if (info[2].equals("openedLetter")) {
                System.out.println("updeting");
                updateScene(message);
            }
        }
    }

    public void newGame() {
        for (int i = 0; i < 33; i++) {
            buttons.getChildren().get(i).setDisable(false);
        }
        initialize();
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
        System.out.println("rere" + gameClient);
        this.gameClient.sendMessageToServerAsync("MyWordfor2");
        initialize();
        // sendRorWaiting();
    }

    public void initialize() {
        System.out.println("init " + this.gameClient);

        if (this.gameClient == null) {
            System.out.println(gameClient);
            gameClient = new GameClient();
            this.gameClient.start();
            this.gameClient.sendMessageToServerAsync("myWord");
            motion.setVisible(false);
        } else {
            if (this.gameClient.idGamer == 2) {
                this.gameClient.sendMessageToServerAsync("2:doWHOGO");
            }
            this.gameClient.sendMessageToServerAsync("MyWordfor2");

        }
        System.out.println(this.gameClient);
        base1.setVisible(false);
        base2.setVisible(false);
        base3.setVisible(false);
        pole.setVisible(false);
        rod.setVisible(false);
        rope1.setVisible(false);
        rope2.setVisible(false);
        man.setVisible(false);
        Platform.runLater(() -> {
            String[] info = this.gameClient.messageRes.split(":");
            text.setText(info[0]);
            System.out.println("итого" + this.gameClient);
        });
        winStatus.setText("");
        realWord.setText("");
        buttons.setDisable(false);
        score.setText("Количество очков: 100");
        if (this.gameClient.idGamer == 2) {
            this.gameClient.sendMessageToServerAsync("2:doWHOGO");
            sendRorWaiting2();
        }


    }

    public void sendRorWaiting2() {
        if (this.gameClient.idGamer == 2) {
            System.out.println("FLAG WAIITING");
            helloListener = this::gaming;
            this.gameClient.sendMessageToServerAsync("2:waiting:" + gameClient.idRoom);
            this.gameClient.addMessageReceivedListener(helloListener);
            getVisibleF();
        }
    }

    public void sendRorWaiting1() {
        if (this.gameClient.idGamer == 1) {
            getVisibleT();
            System.out.println("FLAG WAIITING");
            this.gameClient.sendMessageToServerAsync("1:waiting:" + gameClient.idRoom);
            gameClient.addMessageReceivedListener(this::gaming);
        }
    }

}