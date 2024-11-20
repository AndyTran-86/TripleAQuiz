package Server;

import Requests.ListeningRequest;
import Requests.RoundPlayedRequest;
import Requests.StartNewGameRequest;
import Requests.SurrenderRequest;
import Responses.ListeningResponse;
import Responses.NewGameResponse;
import Responses.RoundPlayedResponse;
import Server.StateMachine.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {
    Database database;
    Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream in;

    ServerState state;
    ServerState handleListeningRequestState;
    ServerState handleNewGameRequestState;
    ServerState handleRoundPlayedRequestState;
    ServerState handleSurrenderRequestState;

    public ClientConnection(Socket socket) {
        database = new Database();
        this.socket = socket;
        handleListeningRequestState = new ListeningRequestHandlingState(this);
        handleNewGameRequestState = new NewGameRequestHandlingState(this);
        handleRoundPlayedRequestState = new RoundPlayedRequestHandlingState(this);
        handleSurrenderRequestState = new SurrenderRequestHandlingState(this);
    }

    public void processRequest() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Object unknownRequestFromClient = in.readObject();

            switch (unknownRequestFromClient) {
                case ListeningRequest listeningRequest -> {
                    state = handleListeningRequestState;
                    state.handleRequest(listeningRequest);
                }
                case StartNewGameRequest startNewGameRequest -> {
                    state = handleNewGameRequestState;
                    state.handleRequest(startNewGameRequest);
                }
                case RoundPlayedRequest roundPlayedRequest -> {
                    state = handleRoundPlayedRequestState;
                    state.handleRequest(roundPlayedRequest);
                }
                case SurrenderRequest surrenderRequest -> {
                    state = handleSurrenderRequestState;
                    state.handleRequest(surrenderRequest);
                }
                default -> throw new UnsupportedOperationException("Unknown request!");
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
