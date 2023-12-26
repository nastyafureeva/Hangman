package client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class GameClient extends Thread {
    private List<OnMessageReceivedListener> listeners = new ArrayList<>();


    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    String messageRes;

    public GameClient() {
        try {
            this.socket = new Socket("localhost", 8000);
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

//    public void run() {
//        try {
//            while (true) {
//                this.messageRes = this.br.readLine();
//                System.out.println("Received from server: " + messageRes);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public void sendMessageToServerAsync(String message) {
//        Task<Void> task = new Task<>() {
//            @Override
//            protected Void call() throws Exception {
//                bw.write(message + System.lineSeparator());
//                bw.flush();
//                return null;
//            }
//        };
//
//        task.setOnSucceeded(event -> {
//            try {
//                updateUIBasedOnResponse(messageRes);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            // Обновляем пользовательский интерфейс после отправки сообщения на сервер
//            // Это может включать обновление интерфейса на основе ответа от сервера
//            // Например:
//            // updateUIBasedOnResponse(messageRes);
//        });
//
//        task.setOnFailed(event -> {
//            // Обрабатываем ошибку, если что-то пошло не так
//            task.getException().printStackTrace();
//            // Или можно обновить интерфейс, сообщив пользователю о проблеме
//        });
//
//        new Thread(task).start();

        try {
            this.bw.write(message + System.lineSeparator());
            this.bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addMessageReceivedListener(OnMessageReceivedListener listener) {
        listeners.add(listener);
    }

    public void removeMessageReceivedListener(OnMessageReceivedListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(String message) {
        for (OnMessageReceivedListener listener : listeners) {
            listener.onMessageReceived(message);
        }
    }

    public void run() {
        try {
            while (true) {
                this.messageRes = this.br.readLine();
                System.out.println("Received from server: " + messageRes);
                notifyListeners(this.messageRes); // Уведомляем всех слушателей о новом сообщении
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



//    private void updateUIBasedOnResponse(String messageRes) throws FileNotFoundException {
//        Platform.runLater(() -> {
//            try {
//                HelloController h = new HelloController();
//                h.ksld(messageRes);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//
//
//        });
//
//
//
//    }

