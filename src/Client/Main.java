package Client;

public class Main {
    public static void main(String[] args) {
        startNewClient();
    }

    static void startNewClient() {
        Client client = new Client(5555, "testUsername");
        new Thread(client).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Client client2 = new Client(5555, "testUsername2");
        new Thread(client2).start();
    }
}
