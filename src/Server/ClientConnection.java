package Server;

import Requests.ListeningRequest;
import Requests.RoundPlayedRequest;
import Requests.StartNewGameRequest;
import Requests.SurrenderRequest;
import Responses.ListeningResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {
    Database database;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ClientConnection(Socket socket) {
        database = new Database();
        this.socket = socket;
    }

    public void processRequest() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Object unknownRequestFromClient = in.readObject();

            switch (unknownRequestFromClient) {
                case ListeningRequest listeningRequest -> out.writeObject(new ListeningResponse());
                case StartNewGameRequest startNewGameRequest -> out.writeObject(new StartNewGameRequest());
                case RoundPlayedRequest roundPlayedRequest -> out.writeObject(new RoundPlayedRequest());
                case SurrenderRequest surrenderRequest -> out.writeObject(new SurrenderRequest());
                default -> throw new UnsupportedOperationException("Unknown request");
            }

//            out.writeObject(new Response(null, "You are connected."));
//
//            Request fromClient;
//
//            while ((fromClient = (Request) in.readObject()) != null) {
//                if (fromClient.quizQuestion == null)
//                    out.writeObject(new Response(database.quizQuestions.getFirst(), null));
//            }


        } catch (IOException e ) {
            System.out.println("IO ex");
        } catch (ClassNotFoundException e) {
            System.out.println("Classcast ex");
        }
    }


    @Override
    public void run() {
        processRequest();
    }
}
