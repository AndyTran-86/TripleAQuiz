package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ClientConnection(Socket socket) {
        this.socket = socket;
    }

    public void processRequest() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Object obj = in.readObject();

            String message = (String) obj;
            System.out.println("Message received: " + message);
            out.writeObject("You are connected to the server...");

        } catch (IOException e ) {
            System.out.println("IO ex");
        } catch (ClassNotFoundException e) {
            System.out.println("Classcast");
        }
    }


    @Override
    public void run() {
        processRequest();
    }
}
