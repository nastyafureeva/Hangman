//package client;
//
////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package client;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import javafx.scene.control.TextArea;
//
//public class MessageListener extends Thread {
//    private TextArea textArea;
//    private BufferedReader br;
//
//    public MessageListener(TextArea textArea, BufferedReader br) {
//        this.textArea = textArea;
//        this.br = br;
//    }
//
//    public void run() {
//        while(true) {
//            try {
//                String message = this.br.readLine();
//                this.textArea.appendText(message);
//            } catch (IOException var3) {
//                throw new RuntimeException(var3);
//            }
//        }
//    }
//}
