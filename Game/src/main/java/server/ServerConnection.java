//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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

    public ServerConnection(Socket socket) {
        try {
            this.socket = socket;
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.gameModel = new GameModel();
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
                System.out.println(clientMessage);
                this.send("Response from server");
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


    private void processClientMessage(String message) throws IOException {
        if (message == "meWord") {
            try {
                bw.write(gameModel.myWord);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
