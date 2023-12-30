package server;

import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    ServerConnection gamer1;
    ServerConnection gamer2;
    String name;
    String roomWord;
     int correct;
     List<String> myLetters;
    List<String> answer;
    String res;
    String openedLetter;
    Map<Socket, Integer> mistakes = new HashMap<>();
    Map<Socket, Boolean> whoGO = new HashMap<>();

    public Room(ServerConnection gamer1, ServerConnection gamer2, String name) throws FileNotFoundException {
        this.gamer1 = gamer1;
        this.gamer2 = gamer2;
        this.name = name;
    }
}


