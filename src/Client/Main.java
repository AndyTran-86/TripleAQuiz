package Client;

public class Main {
    public static void main(String[] args) {
        startNewClient();
    }

    static void startNewClient() {
        Client client = new Client();
        new Thread(client).start();
    }
}
