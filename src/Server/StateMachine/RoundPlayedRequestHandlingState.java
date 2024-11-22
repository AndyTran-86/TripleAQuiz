package Server.StateMachine;

import Requests.Request;
import Responses.RoundPlayedResponse;
import Responses.RoundTurn;
import Server.ClientConnection;
import Server.GameInstanceManager;

import java.io.IOException;
import java.util.Map;

public class RoundPlayedRequestHandlingState implements ServerState {
    ClientConnection connection;
    GameInstanceManager gameInstanceManager;

    public RoundPlayedRequestHandlingState(ClientConnection connection, GameInstanceManager gameInstanceManager) {
        this.connection = connection;
        this.gameInstanceManager = gameInstanceManager;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        connection.out.writeObject(new RoundPlayedResponse(RoundTurn.OTHER_PLAYER_TURN, Map.of("Category2", 3)));
    }
}
