package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ServerConnection extends Thread {
    public Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private GameModel gameModel;
    private GameModelFor2 gameModelFor2;
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
                String clientMessage = "";
                try {

                    clientMessage = this.br.readLine();
                    String s = processClientMessage(clientMessage);
                    System.out.println("got " + clientMessage);
                    System.out.println("Sending- " + s);
                    this.send(s);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Восстанавливаем прерванный статус
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


    private String processClientMessage(String message) throws IOException, InterruptedException {
        if (!message.equals(null)) {
            String[] info = message.split(":");
            if (info[0].equals("1G")) {
                return gameModel.buttonLetter(info[1]);
            } else if (info[0].equals("2G")) {
                return info[1] + ":" + gameModelFor2.buttonLetter(info[2], this);
            } else if (info[0].equals("myWord")) {
                return gameModel.res + ":clear:" + gameModel.myWord + ":0";
            } else if (info[0].equals("roomname")) {
                server.count++;
                server.rooms.put(server.count, new Room(null, null, info[1]));
                return "successed";
            } else if (info[0].equals("getrooms")) {
                return server.getNamesOfRooms();
            } else if (info[0].equals("connectroom")) {
                this.gameModelFor2 = new GameModelFor2(server);
                gameModelFor2.idRoom = server.getIDofRoom(info[1]);
                return server.connectToRoom(this, info[1]);
            } else if (info[0].equals("joingame1")) {
                while (true) {
                    if (this.server.rooms.get(Integer.parseInt(info[1])).gamer2 != null) {
                        return "connectedtoscene2:" + Integer.parseInt(info[1]);
                    }
                }
            } else if (info[0].equals("MyWordfor2")) {
                // String s = gameModelFor2.res + ":clear:" + gameModelFor2.myWord + ":" + gameModelFor2.mistakes.get(this.socket);
                this.gameModelFor2.setMistakes();
                return server.rooms.get(gameModelFor2.idRoom).res + ":clear:" + server.rooms.get(gameModelFor2.idRoom).roomWord + ":" + server.rooms.get(gameModelFor2.idRoom).mistakes.get(this.socket);
            } else if (info[0].equals("whoami")) {
                if (server.rooms.get(this.gameModelFor2.idRoom).gamer2.equals(this)) {
                    return "2";
                }
                return "1";
            } else if (info[1].equals("YOUGO")) {
                synchronized (ServerListener.sharedLock) {
                    System.out.println("УВЕДОМЛЯЮ");
                    ServerListener.message = info[0] + ":" + server.rooms.get(gameModelFor2.idRoom).res + ":" + server.rooms.get(gameModelFor2.idRoom).openedLetter + ":" + gameModelFor2.getMistakes();
                    ServerListener.sharedLock.notify(); // Уведомление других потоков о изменении состояния
                }
            } else if (info[1].equals("waiting")) {
                synchronized (ServerListener.sharedLock) {
                    System.out.println("waiting " + gameModelFor2.server.rooms.get(gameModelFor2.idRoom).whoGO.get(socket));
                    while (!gameModelFor2.server.rooms.get(gameModelFor2.idRoom).whoGO.get(socket)) {
                        System.out.println(gameModelFor2.server.rooms.get(gameModelFor2.idRoom).whoGO.get(socket));
                        try {
                            ServerListener.sharedLock.wait();
                            return ServerListener.message;
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            } else if (info[1].equals("doWHOGO")) {
                gameModelFor2.doWHOGO(this);
                return server.rooms.get(gameModelFor2.idRoom).res + ":clear:" + server.rooms.get(gameModelFor2.idRoom).roomWord + ":" + server.rooms.get(gameModelFor2.idRoom).mistakes.get(this.socket);
            } else if (info[1].equals("YOULOSTTOO")) {
                synchronized (ServerListener.sharedLock) {
                    System.out.println("УВЕДОМЛЯЮКОНЕЦ");
                    ServerListener.message = info[0] + ":" + server.rooms.get(gameModelFor2.idRoom).res + ":" + server.rooms.get(gameModelFor2.idRoom).openedLetter + ":" + gameModelFor2.getMistakes();
                    ServerListener.sharedLock.notify(); // Уведомление других потоков о изменении состояния
                }


            }
        }
        return "null";
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