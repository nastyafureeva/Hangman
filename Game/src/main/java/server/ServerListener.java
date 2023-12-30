package server;

public class ServerListener {

        public static final Object sharedLock = new Object();
        public static String message = "";
        public static final Object endLock = new Object();

}
