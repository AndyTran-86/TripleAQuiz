package Server;

import Server.QuizDatabase.Api_Client;
import Server.QuizDatabase.Category;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

public class Server implements Runnable{
    int port;
    ServerSocket serverSocket;
    GameInstanceManager gameInstanceManager;
    Api_Client apiClient;
    List<Category> allCategories;
    int numRounds;
    int numQuestions;

    public Server(int port) {
        this.port = port;
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/Server/Config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        numRounds = Integer.parseInt(properties.getProperty("numRounds",""));
        numQuestions = Integer.parseInt(properties.getProperty("numQuestions",""));

        apiClient = new Api_Client(numRounds, numQuestions);
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
        allCategories = apiClient.getNewCategories();
        gameInstanceManager = new GameInstanceManager(allCategories, numRounds);
    }

    @Override
    public void run() {
        listenForConnectingClients();
    }
}
