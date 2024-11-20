package Server.StateMachine;

import Requests.Request;
import Server.ClientConnection;

import java.io.IOException;

public class RoundPlayedRequestHandlingState implements ServerState {
    ClientConnection connection;

    public RoundPlayedRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        System.out.println("Handling round played request");
    }
}
