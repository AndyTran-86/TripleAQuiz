package Server;

public class Main {
    public static void main(String[] args) {
    startServer();
}

    static void startServer() {
        Server server = new Server(5555);
        new Thread(server).start();
    }
}
