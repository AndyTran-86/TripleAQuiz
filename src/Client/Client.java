package Client;

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


    public Client(int port) {
        this.port = port;
        ip = InetAddress.getLoopbackAddress();
    }

    private void connectToServer() {
        try(Socket socket = new Socket(ip, port)) {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Response fromServer;

            while ((fromServer = (Response) in.readObject()) != null) {
                if (fromServer.getQuizQuestion() == null) {
                    JOptionPane.showMessageDialog(null, fromServer.getMessage());
                    out.writeObject(new Request(null, 0));
                }
                else {
                    QuizQuestion question = fromServer.getQuizQuestion();
                    JOptionPane.showMessageDialog(null, question.getQuestion());
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
