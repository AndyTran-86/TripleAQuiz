package Server.StateMachine;

import Requests.Request;
import Server.ClientConnection;

import java.io.IOException;

public class ListeningRequestHandlingState implements ServerState {
    ClientConnection connection;

    public ListeningRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        System.out.println("Handling listening request");
    }
}
