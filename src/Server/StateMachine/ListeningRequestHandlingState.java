package Server.StateMachine;

import Requests.Request;
import Responses.ListeningResponse;
import Server.ClientConnection;
import Server.GameInstanceManager;

import java.io.IOException;
import java.util.List;

public class ListeningRequestHandlingState implements ServerState {
    ClientConnection connection;
    GameInstanceManager gameInstanceManager;

    public ListeningRequestHandlingState(ClientConnection connection, GameInstanceManager gameInstanceManager) {
        this.connection = connection;
        this.gameInstanceManager = gameInstanceManager;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        gameInstanceManager.putPlayerInLobby(connection);
        //TODO get actual Question/Category object once its available
        connection.out.writeObject(new ListeningResponse(connection.clientID, List.of("Question1", "Question2", "Question3", "Question4")));
    }
}
