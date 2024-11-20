package Server.StateMachine;

import Requests.Request;
import Responses.ListeningResponse;
import Server.ClientConnection;

import java.io.IOException;

public class ListeningRequestHandlingState implements ServerState {
    ClientConnection connection;

    public ListeningRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        connection.out.writeObject(new ListeningResponse());
    }
}
