package Server.StateMachine;

import Requests.Request;
import Responses.NewGameResponse;
import Server.ClientConnection;

import java.io.IOException;

public class NewGameRequestHandlingState implements ServerState {
    ClientConnection connection;

    public NewGameRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        connection.out.writeObject(new NewGameResponse());
    }
}
