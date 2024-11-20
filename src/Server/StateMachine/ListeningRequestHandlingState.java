package Server.StateMachine;

import Requests.Request;
import Responses.ListeningResponse;
import Server.ClientConnection;

import java.io.IOException;
import java.util.List;

public class ListeningRequestHandlingState implements ServerState {
    ClientConnection connection;

    public ListeningRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        //TODO add actual ID generator in clientConnection
        //TODO get actual Question/Category object once its available
        connection.out.writeObject(new ListeningResponse(12, List.of("Question1", "Question2", "Question3", "Question4")));
    }
}
