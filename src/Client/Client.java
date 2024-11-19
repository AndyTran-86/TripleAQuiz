package Client;

import Requests.ListeningRequest;
import Responses.*;
import Server.QuizQuestion;
import Server.Request;
import Server.Response;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {

    ObjectOutputStream out;
    ObjectInputStream in;
    ClientGUI gui;
    int port;
    InetAddress ip;
    String username;


    public Client(int port, String username) {
        this.port = port;
        ip = InetAddress.getLoopbackAddress();
        this.username = username;
    }

    private void connectToServer() {
        try(Socket socket = new Socket(ip, port)) {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


            out.writeObject(new ListeningRequest(username));

            Object unknownResponseFromServer;

            while ((unknownResponseFromServer = in.readObject()) != null) {
                switch (unknownResponseFromServer) {
                    case ListeningResponse listeningResponse -> JOptionPane.showMessageDialog(gui.frame, "Listening connection established");
                    case NewGameResponse newGameResponse -> JOptionPane.showMessageDialog(gui.frame, "New game response received");
                    case RoundPlayedResponse roundPlayedResponse -> JOptionPane.showMessageDialog(gui.frame, "Round played response received");
                    case VictoryResponse victoryResponse -> JOptionPane.showMessageDialog(gui.frame, "Victory response received");
                    case DefeatResponse defeatResponse -> JOptionPane.showMessageDialog(gui.frame, "Defeat response received");
                    default -> JOptionPane.showMessageDialog(gui.frame, "Unknown response received");
                }


            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
         gui = new ClientGUI();
         gui.init();
         connectToServer();
    }
}
