package Server.StateMachine;

import Requests.Request;
import Responses.RoundPlayedResponse;
import Server.ClientConnection;

import java.io.IOException;

public class RoundPlayedRequestHandlingState implements ServerState {
    ClientConnection connection;

    public RoundPlayedRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        connection.out.writeObject(new RoundPlayedResponse());
    }
}
