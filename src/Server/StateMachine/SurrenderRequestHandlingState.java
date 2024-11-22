package Server.StateMachine;

import Requests.Request;
import Requests.SurrenderRequest;
import Responses.DefeatResponse;
import Responses.DefeatType;
import Server.ClientConnection;
import Server.GameInstanceManager;

import java.io.IOException;

public class SurrenderRequestHandlingState implements ServerState {
    ClientConnection connection;
    GameInstanceManager gameInstanceManager;

    public SurrenderRequestHandlingState(ClientConnection connection, GameInstanceManager gameInstanceManager) {
        this.connection = connection;
        this.gameInstanceManager = gameInstanceManager;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        connection.out.writeObject(new DefeatResponse(DefeatType.SURRENDER));
    }
}
