package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import server.Words;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

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


//    private int mistakes;
//    private int correct;
//    private Words word = new Words();
//    private String myWord;
//    private List<String> myLetters;
//    private List<String> answer;
    GameClient gameClient;

    public HelloController() throws FileNotFoundException {

    }

    public void onClick(ActionEvent event) {
        String letter = ((Button) event.getSource()).getText();
        ((Button) event.getSource()).setDisable(true);
        gameClient.sendMessageToServer(letter);

//        if (myLetters.contains(letter)) {
//            for (int i = 0; i < myLetters.size(); i++) {
//                String l = myLetters.get(i);
//                if (l.equals(letter)) {
//                    correct++;
//                    answer.set(i * 2, letter);
//                }
//            }
//            String res = String.join("", answer);
//            text.setText(res);
//            if (correct == myWord.length()) {
//                winStatus.setText("Победа!");
//                buttons.setDisable(true);
//            }
//        } else {
//            mistakes++;
//            score.setText("Количество очков: " + (100 - 12.5 * mistakes));
//            if (mistakes == 1) base1.setVisible(true);
//            else if (mistakes == 2) base2.setVisible(true);
//            else if (mistakes == 3) base3.setVisible(true);
//            else if (mistakes == 4) pole.setVisible(true);
//            else if (mistakes == 5) rod.setVisible(true);
//            else if (mistakes == 6) rope1.setVisible(true);
//            else if (mistakes == 7) rope2.setVisible(true);
//            else if (mistakes == 8) {
//                rope2.setVisible(false);
//                man.setVisible(true);
//                winStatus.setText("Ты проиграл!");
//                realWord.setText("Загаданное слово: " + myWord);
//                buttons.setDisable(true);
//            }
//        } gameClient.messageRes.split(":")
        System.out.println(gameClient.messageRes);
        String[] info = new String[] {"ererw","base1"};
        text.setText(info[0]);
        if (info[1] == "win") {
            winStatus.setText("Победа!");
            buttons.setDisable(true);
        } else if (info[1] == "base1") base1.setVisible(true);
        else if (info[1] == "base2") base2.setVisible(true);
        else if (info[1] == "base3") base3.setVisible(true);
        else if (info[1] == "pole") pole.setVisible(true);
        else if (info[1] == "rod") rod.setVisible(true);
        else if (info[1] == "rope1") rope1.setVisible(true);
        else if (info[1] == "rope2") rope2.setVisible(true);
        else if (info[1] == "lost") {
               rope2.setVisible(false);
               man.setVisible(true);
               winStatus.setText("Ты проиграл!");
               realWord.setText("Загаданное слово: " + info[2]);
               buttons.setDisable(true);

        }


    }

    public void newGame() {
        for (int i = 0; i < 33; i++) {
            buttons.getChildren().get(i).setDisable(false);
        }
        initialize();
    }

    public void initialize() {
        this.gameClient = new GameClient();
        gameClient.start();;
        base1.setVisible(false);
        base2.setVisible(false);
        base3.setVisible(false);
        pole.setVisible(false);
        rod.setVisible(false);
        rope1.setVisible(false);
        rope2.setVisible(false);
        man.setVisible(false);
        gameClient.sendMessageToServer("myWord");
//        mistakes = 0;
//        correct = 0;
//        myWord = word.getWord();
//        myLetters = Arrays.asList(myWord.split(""));
//        answer = Arrays.asList(new String[myLetters.size() * 2]);
//        for (int i = 0; i < myLetters.size() * 2; i++) {
//            if (i % 2 == 0) {
//                answer.set(i, "_");
//            } else {
//                answer.set(i, " ");
//            }
//        }
//        String res = String.join("", answer);
        text.setText(gameClient.messageRes);
        winStatus.setText("");
        realWord.setText("");
        buttons.setDisable(false);
        score.setText("Количество очков: 100");
    }

}