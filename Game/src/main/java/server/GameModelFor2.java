package server;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameModelFor2 extends GameModel {

    public int idRoom;
    Server server;
//    String openedLetter;


    public GameModelFor2(Server server) throws FileNotFoundException {
        this.server = server;
        server.rooms.get(idRoom).openedLetter = "openedLetter";
        server.rooms.get(idRoom).correct = 0;
        if (server.rooms.get(idRoom).roomWord == null) {
            String myWord = word.getWord();
            server.rooms.get(idRoom).roomWord = myWord;
            getRes(myWord);
        } else {
            String myWord = server.rooms.get(idRoom).roomWord;
            getRes(myWord);

            // whoGO.put(server.rooms.get(idRoom).gamer2.socket, true);
        }
//        System.out.println(this.myWord);
//        whoGO.put(server.rooms.get(idRoom).gamer1.socket, true);
//        whoGO.put(server.rooms.get(idRoom).gamer2.socket, true);

    }

    public String getMistakes() {
        return "" + (server.rooms.get(idRoom).mistakes.get(server.rooms.get(idRoom).gamer1.socket) + server.rooms.get(idRoom).mistakes.get(server.rooms.get(idRoom).gamer2.socket));
    }

    public String getRes(String word) {
        server.rooms.get(idRoom).myLetters = Arrays.asList(word.split(""));
        server.rooms.get(idRoom).answer = Arrays.asList(new String[server.rooms.get(idRoom).myLetters.size() * 2]);
        for (int i = 0; i < server.rooms.get(idRoom).myLetters.size() * 2; i++) {
            if (i % 2 == 0) {
                server.rooms.get(idRoom).answer.set(i, "_");
            } else {
                server.rooms.get(idRoom).answer.set(i, " ");
            }
        }
        return server.rooms.get(idRoom).res = String.join("", server.rooms.get(idRoom).answer);
    }

    public void doWHOGO(ServerConnection serverConnection) {
        server.rooms.get(idRoom).whoGO.put(server.rooms.get(idRoom).gamer1.socket, true);
        server.rooms.get(idRoom).whoGO.put(server.rooms.get(idRoom).gamer2.socket, false);
    }

    public void setMistakes() {
        server.rooms.get(idRoom).mistakes.put(server.rooms.get(idRoom).gamer1.socket, 0);
        server.rooms.get(idRoom).mistakes.put(server.rooms.get(idRoom).gamer2.socket, 0);
    }


    public String buttonLetter(String letter, ServerConnection serverConnection) {
        System.out.println(letter);
        server.rooms.get(idRoom).openedLetter = server.rooms.get(idRoom).openedLetter + ":" + letter;
        String messageLetter = "";
        System.out.println(letter);
        if (server.rooms.get(idRoom).myLetters.contains(letter)) {
            for (int i = 0; i < server.rooms.get(idRoom).myLetters.size(); i++) {
                String l = server.rooms.get(idRoom).myLetters.get(i);
                if (l.equals(letter)) {
                    server.rooms.get(idRoom).correct++;
                    server.rooms.get(idRoom).answer.set(i * 2, letter);
                }
            }
            server.rooms.get(idRoom).res = String.join("", server.rooms.get(idRoom).answer);
            if (   server.rooms.get(idRoom).correct ==    server.rooms.get(idRoom).roomWord.length()) {
                return server.rooms.get(idRoom).res + ":win:" + server.rooms.get(idRoom).roomWord + ":" + server.rooms.get(idRoom).mistakes.get(serverConnection.socket);
            }
            return server.rooms.get(idRoom).res + ":clear:" + server.rooms.get(idRoom).roomWord + ":" + server.rooms.get(idRoom).mistakes.get(serverConnection.socket);
        } else {
            int countM = server.rooms.get(idRoom).mistakes.get(serverConnection.socket) + 1;
            server.rooms.get(idRoom).mistakes.replace(serverConnection.socket, countM);
            //mistakes.put(serverConnection.socket, countM);
            System.out.println("mistakes" + countM);
            countM = server.rooms.get(idRoom).mistakes.get(server.rooms.get(idRoom).gamer1.socket) + server.rooms.get(idRoom).mistakes.get(server.rooms.get(idRoom).gamer2.socket);
            // score.setText("Количество очков: " + (100 - 12.5 * mistakes));
            if (countM == 1) messageLetter = server.rooms.get(idRoom).res + ":base1:" + server.rooms.get(idRoom).roomWord + ":" + server.rooms.get(idRoom).mistakes.get(serverConnection.socket);
            else if (countM == 2)
                messageLetter = server.rooms.get(idRoom).res + ":base2:" + server.rooms.get(idRoom).roomWord + ":" + server.rooms.get(idRoom).mistakes.get(serverConnection.socket);
            else if (countM == 3)
                messageLetter = server.rooms.get(idRoom).res + ":base3:" + server.rooms.get(idRoom).roomWord + ":" + server.rooms.get(idRoom).mistakes.get(serverConnection.socket);
            else if (countM == 4) messageLetter = server.rooms.get(idRoom).res + ":pole:" + server.rooms.get(idRoom).roomWord + ":" + server.rooms.get(idRoom).mistakes.get(serverConnection.socket);
            else if (countM == 5) messageLetter = server.rooms.get(idRoom).res + ":rod:" + server.rooms.get(idRoom).roomWord + ":" + server.rooms.get(idRoom).mistakes.get(serverConnection.socket);
            else if (countM == 6)
                messageLetter = server.rooms.get(idRoom).res + ":rope1:" + server.rooms.get(idRoom).roomWord + ":" + server.rooms.get(idRoom).mistakes.get(serverConnection.socket);
            else if (countM == 7)
                messageLetter = server.rooms.get(idRoom).res + ":rope2:" + server.rooms.get(idRoom).roomWord + ":" + server.rooms.get(idRoom).mistakes.get(serverConnection.socket);
            else if (countM == 8) {
                messageLetter = server.rooms.get(idRoom).res + ":lost:" + server.rooms.get(idRoom).roomWord + ":" + countM;
            }
            if (server.rooms.get(idRoom).gamer1.socket.equals(serverConnection.socket)) {
                System.out.println("11111");
                server.rooms.get(idRoom).whoGO.put(serverConnection.socket, false);
                server.rooms.get(idRoom).whoGO.put(server.rooms.get(idRoom).gamer2.socket, true);

            } else {
                System.out.println("2222");
                server.rooms.get(idRoom).whoGO.put(serverConnection.socket, false);
                server.rooms.get(idRoom).whoGO.put(server.rooms.get(idRoom).gamer1.socket, true);
            }
            System.out.println(server.rooms.get(idRoom).whoGO.get(serverConnection.socket));
            System.out.println(server.rooms.get(idRoom).whoGO.get(server.rooms.get(idRoom).gamer1.socket));
            System.out.println(server.rooms.get(idRoom).whoGO.get(server.rooms.get(idRoom).gamer2.socket));
            System.out.println("OL" + server.rooms.get(idRoom).openedLetter);
        }

        return messageLetter;
    }

}
