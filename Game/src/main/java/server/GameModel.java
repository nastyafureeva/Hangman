package server;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;


public class GameModel {
    private int mistakes;
    private int correct;
    private Words word = new Words();
     String myWord;
    private List<String> myLetters;
    List<String> answer;
    String res;

    public GameModel() throws FileNotFoundException {
        mistakes = 0;
        correct = 0;
        myWord = word.getWord();
        myLetters = Arrays.asList(myWord.split(""));
        answer = Arrays.asList(new String[myLetters.size() * 2]);
        for (int i = 0; i < myLetters.size() * 2; i++) {
            if (i % 2 == 0) {
                answer.set(i, "_");
            } else {
                answer.set(i, " ");
            }
        }
         res = String.join("", answer);

    }
   public String buttonLetter(String letter) {
        String messageLetter = null;
       if (myLetters.contains(letter)) {
           for (int i = 0; i < myLetters.size(); i++) {
               String l = myLetters.get(i);
               if (l.equals(letter)) {
                   correct++;
                   answer.set(i * 2, letter);
               }
           }
            res = String.join("", answer);
//           text.setText(res);
           if (correct == myWord.length()) {
               messageLetter = res + ":win";
              
           }
       } else {
           mistakes++;
          // score.setText("Количество очков: " + (100 - 12.5 * mistakes));
           if (mistakes == 1) messageLetter = "base1";
           else if (mistakes == 2) messageLetter = "base2";
           else if (mistakes == 3) messageLetter = "base3";
           else if (mistakes == 4) messageLetter = "pole";
           else if (mistakes == 5) messageLetter = "rod";
           else if (mistakes == 6) messageLetter = "rope1";
           else if (mistakes == 7) messageLetter = "rope2"; 
           else if (mistakes == 8) {
               messageLetter = "lost:"+myWord;
//               rope2.setVisible(false);
//               man.setVisible(true);
//               winStatus.setText("Ты проиграл!");
//               realWord.setText("Загаданное слово: " + myWord);
//               buttons.setDisable(true);
           }
       }
       return messageLetter;
   }
}
