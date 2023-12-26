package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Server {

    private List<ServerConnection> serverConnections = new LinkedList();
    private ServerSocket serverSocket;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(6666);
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public void run() {
        try {
            while(true) {
                this.serverConnections.add(new ServerConnection(this.serverSocket.accept()));
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


    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}