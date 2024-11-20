package Server.StateMachine;

import Requests.Request;
import Server.ClientConnection;

import java.io.IOException;

public class SurrenderRequestHandlingState implements ServerState {
    ClientConnection connection;

    public SurrenderRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        System.out.println("Handling surrender request");
    }
}
