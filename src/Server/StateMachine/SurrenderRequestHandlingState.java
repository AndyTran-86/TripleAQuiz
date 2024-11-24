package Server.StateMachine;

import Requests.Request;
import Requests.SurrenderRequest;
import Responses.DefeatResponse;
import Responses.DefeatType;
import Server.ClientConnection;
import Server.GameInstance;
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
        if (request instanceof SurrenderRequest surrenderRequest) {
            GameInstance gameInstance = gameInstanceManager.getGameInstanceByID(surrenderRequest.getGameInstanceID());
            gameInstance.findCallingPlayer(surrenderRequest.getClientID());
            gameInstance.notifySurrender();
        }
    }
}
