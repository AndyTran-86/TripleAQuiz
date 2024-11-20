package Server.StateMachine;

import Requests.Request;
import Responses.NewGameResponse;
import Responses.RoundTurn;
import Server.ClientConnection;

import java.io.IOException;

public class NewGameRequestHandlingState implements ServerState {
    ClientConnection connection;

    public NewGameRequestHandlingState(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        //TODO get actual gameinstanceID and turnToPlay and put here once its available
        connection.out.writeObject(new NewGameResponse(15, RoundTurn.PLAYER_TURN));
    }
}
