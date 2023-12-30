package server;

public class ServerListener {

        public   Object sharedLock;
        public  String message;
        public ServerListener(){
                this.sharedLock = new Object();
                this.message = "";
        }

}
