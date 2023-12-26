package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerConnection extends Thread {
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private GameModel gameModel;
    Server server;

    public ServerConnection(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.gameModel = new GameModel();
            this.server = server;
        } catch (IOException var4) {
            throw new RuntimeException(var4);
        }
        this.setDaemon(true);
        this.start();
    }

    public void run() {
        try {
            while (true) {
                String clientMessage = this.br.readLine();
                // gameModel.buttonLetter(clientMessage);
                // System.out.println(gameModel.buttonLetter(clientMessage));
                String s =processClientMessage(clientMessage);
                System.out.println("got" + clientMessage);
                System.out.println("Sending- "+s);
                this.send(s);
            }

        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }


    public void send(String message) {
        try {
            this.bw.write(message + System.lineSeparator());
            this.bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String processClientMessage(String message) throws IOException {
        String[]info = message.split(":");
        if (info[0].equals("myWord")) {
            return gameModel.res + ":clear";
        } else if (info[0].equals("roomname")) {
          server.rooms.add(new Room(this,null,info[1]));
          return server.getNamesOfRooms();
        } else if (info[0].equals("getrooms")) {
            return server.getNamesOfRooms();
        }
            return gameModel.buttonLetter(message);
    }
    public void sendToServer(String message) {
        try {
            this.bw.write(message + System.lineSeparator());
            this.bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}