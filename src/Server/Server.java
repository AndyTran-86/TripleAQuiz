package Server;

import Server.QuizDatabase.Api_Client;
import Server.QuizDatabase.QuestionsByCategory;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Runnable{
    int port;
    ServerSocket serverSocket;
    GameInstanceManager gameInstanceManager;
    Api_Client apiClient;
    List<QuestionsByCategory> allQuestions;

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
        File temp = new File("src/Server/QuizDatabase/questions.ser");
        if (temp.exists()) {
            allQuestions = apiClient.deSerializeAllQuestions();
            System.out.println("Categories in database:");
            for (QuestionsByCategory category : allQuestions) {
                    System.out.println(category.results().getFirst().category());
            }
        }
        else
            allQuestions = apiClient.getNewCategories(8);
        gameInstanceManager = new GameInstanceManager(allQuestions);
    }

    @Override
    public void run() {
        listenForConnectingClients();
    }
}
