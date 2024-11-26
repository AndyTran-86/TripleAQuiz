package Server;

import Server.QuizDatabase.Api_Client;
import Server.QuizDatabase.Category;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Runnable{
    int port;
    ServerSocket serverSocket;
    GameInstanceManager gameInstanceManager;
    Api_Client apiClient;
    List<Category> allCategories;

    public Server(int port) {
        this.port = port;
        apiClient = new Api_Client();
        getAllQuestions();
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

    private void getAllQuestions() {
        allCategories = apiClient.getNewCategories(8);
        gameInstanceManager = new GameInstanceManager(allCategories);
    }

    @Override
    public void run() {
        listenForConnectingClients();
    }
}
