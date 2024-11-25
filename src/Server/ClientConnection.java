package Server;

import Requests.ListeningRequest;
import Requests.RoundPlayedRequest;
import Requests.StartNewGameRequest;
import Requests.SurrenderRequest;
import Server.StateMachine.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {
    Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public long clientID;
    static long clientIDIncrementor = 1;
    GameInstanceManager gameInstanceManager;
    public String username;

    ServerState state;
    ServerState handleListeningRequestState;
    ServerState handleNewGameRequestState;
    ServerState handleRoundPlayedRequestState;
    ServerState handleSurrenderRequestState;

    public ClientConnection(Socket socket, GameInstanceManager gameInstanceManager) {
        this.socket = socket;
        clientID = clientIDIncrementor++;
        this.gameInstanceManager = gameInstanceManager;
        handleListeningRequestState = new ListeningRequestHandlingState(this, this.gameInstanceManager);
        handleNewGameRequestState = new NewGameRequestHandlingState(this, this.gameInstanceManager);
        handleRoundPlayedRequestState = new RoundPlayedRequestHandlingState(this, this.gameInstanceManager);
        handleSurrenderRequestState = new SurrenderRequestHandlingState(this, this.gameInstanceManager);
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



        } catch (IOException e ) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        processRequest();
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
