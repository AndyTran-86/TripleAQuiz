package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    int port;
    ServerSocket serverSocket;
    GameInstanceManager gameInstanceManager;

    public Server(int port) {
        this.port = port;
        gameInstanceManager = new GameInstanceManager();
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Error when starting server");
        }
    }


    public void listenForConnectingClients() {
        try {
            while (true) {
                Socket clientCallingSocket = serverSocket.accept();
                ClientConnection clientConnection = new ClientConnection(clientCallingSocket, gameInstanceManager);
                new Thread(clientConnection).start();
            }
        } catch (IOException e) {
            System.out.println("Error when connecting client");
        }
    }

    @Override
    public void run() {
        listenForConnectingClients();
    }
}
