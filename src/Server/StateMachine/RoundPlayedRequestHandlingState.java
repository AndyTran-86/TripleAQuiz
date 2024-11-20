package Server.StateMachine;

import Requests.Request;
import Responses.RoundPlayedResponse;
import Responses.RoundTurn;
import Server.ClientConnection;

import java.io.IOException;
import java.util.Map;

public class RoundPlayedRequestHandlingState implements ServerState {
    ClientConnection connection;

    public RoundPlayedRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        connection.out.writeObject(new RoundPlayedResponse(RoundTurn.OTHER_PLAYER_TURN, Map.of("Category2", 3)));
    }
}
