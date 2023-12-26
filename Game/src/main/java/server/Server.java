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
    List<Room> rooms = new ArrayList<>();

    public Server() {
        try {
            this.serverSocket = new ServerSocket(8000);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public void run() {
        try {
            while(true) {
                this.serverConnections.add(new ServerConnection(this.serverSocket.accept(), this));
            }
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }
    public void sendMessage(String message) {
        Iterator var2 = this.serverConnections.iterator();

        while(var2.hasNext()) {
            ServerConnection serverConnection = (ServerConnection)var2.next();
            serverConnection.send(message);
        }

    }
public String getNamesOfRooms(){
        String message = "";
        for (Room s:rooms){
            message = message + s.name + ":" ;
        }
        return message;
}

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}