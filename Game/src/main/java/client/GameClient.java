package client;

import java.io.*;
import java.net.Socket;

public class GameClient extends Thread {
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
//    public void run() {
//        while(true) {
//            try {
//                System.out.println(messageRes);
//               this.messageRes = br.readLine();
//                System.out.println(messageRes);
//            } catch (IOException var3) {
//                throw new RuntimeException(var3);
//            }
//        }
//    }
//
//    public void sendMessageToServer(String message) {
//        System.out.println(message);
//        // реализация отправки сообщения на сервер
//        try {
//            bw.write(message);
//            bw.flush();
//        } catch (IOException e) {
//            // Обработка ошибок
//        }
//    }
    public void run() {
        try {
            while (true) {
                String receivedMessage = this.br.readLine();
                System.out.println("Received from server: " + receivedMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // В методе sendMessageToServer убирается закрытие соединения
    public void sendMessageToServer(String message) {
        try {
            this.bw.write(message + System.lineSeparator());
            this.bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
