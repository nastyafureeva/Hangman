package client;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

public class GameClient extends Thread {
    private HelloController helloController = new HelloController(this);

    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    String messageRes;


    public GameClient() {
        try {
            this.socket = new Socket("localhost", 6666);
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }
    public void run() {
        try {
            while (true) {
                this.messageRes = this.br.readLine();
                System.out.println("Received from server: " + messageRes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageToServerAsync(String message) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                bw.write(message + System.lineSeparator());
                bw.flush();
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            updateUIBasedOnResponse(messageRes);
        });

        task.setOnFailed(event -> {
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    private void updateUIBasedOnResponse(String messageRes) {
        Platform.runLater(() -> {
            helloController.ksld(messageRes);
        });
    }



}
