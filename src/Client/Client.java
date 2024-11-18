package Client;

import java.io.IOException;
import java.net.InetAddress;

public class Client implements Runnable {


    ClientGUI gui;
    int port;
    InetAddress ip;


    public Client(int port) {
        this.port = port;
        ip = InetAddress.getLoopbackAddress();
    }

    private void connectToServer() {
        gui.setLabelText("Trying connecting to server.");
    }

    @Override
    public void run() {
         gui = new ClientGUI();
         connectToServer();
    }
}
