//package client;
//
//import javafx.concurrent.Task;
//import javafx.application.Platform;
//import javafx.concurrent.Task;
//
//public class GameClientAsync extends Thread {
//    // ... (остальной код остаётся без изменений)
//
//    public void sendMessageToServerAsync(String message) {
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
//    }
//}
