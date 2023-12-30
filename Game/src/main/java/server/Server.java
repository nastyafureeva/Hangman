package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private List<ServerConnection> serverConnections = new LinkedList();
    private ServerSocket serverSocket;
    Map<Integer, Room> rooms = new HashMap<>();
    int count;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(8000);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
        count = -1;
    }

    public void run() {
        try {
            while (true) {
                this.serverConnections.add(new ServerConnection(this.serverSocket.accept(), this));
            }
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public void sendMessage(String message) {
        Iterator var2 = this.serverConnections.iterator();

        while (var2.hasNext()) {
            ServerConnection serverConnection = (ServerConnection) var2.next();
            serverConnection.send(message);
        }

    }

    public String getNamesOfRooms() {
        String message = "";
        for (int i = 0; i <= count; i++) {
            String s = rooms.get(i).name;
            message = message + s + ":";
        }
        return message;
    }

    public String connectToRoom(ServerConnection serverConnection, String name) {
        int i = this.getIDofRoom(name);
        if (rooms.get(i).gamer1 == null) {
            rooms.get(i).gamer1 = serverConnection;
            return "connected1:" + i;
        } else if (rooms.get(i).gamer2 == null) {
            rooms.get(i).gamer2 = serverConnection;
            return "connected2:" + i;
        }
        return "notconnected";
    }

    public int getIDofRoom(String name) {
        for (int i = 0; i <= count; i++) {
            String s = rooms.get(i).name;
            if (s.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}