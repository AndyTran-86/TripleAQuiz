package Server.StateMachine;

import Requests.Request;
import Requests.SurrenderRequest;
import Responses.DefeatResponse;
import Server.ClientConnection;

import java.io.IOException;

public class SurrenderRequestHandlingState implements ServerState {
    ClientConnection connection;

    public SurrenderRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        connection.out.writeObject(new DefeatResponse());
    }
}
