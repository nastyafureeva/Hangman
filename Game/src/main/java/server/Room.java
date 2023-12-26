package server;

public class Room {
    ServerConnection gamer1;
    ServerConnection gamer2;
    String name;

    public Room(ServerConnection gamer1, ServerConnection gamer2, String name) {
        this.gamer1 = gamer1;
        this.gamer2 = gamer2;
        this.name = name;
    }
}


