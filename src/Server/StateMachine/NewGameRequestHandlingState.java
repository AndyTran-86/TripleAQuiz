package Server.StateMachine;

import Requests.Request;
import Requests.StartNewGameRequest;
import Responses.NewGameResponse;
import Responses.RoundTurn;
import Server.ClientConnection;
import Server.GameInstance;
import Server.GameInstanceManager;

import java.io.IOException;

public class NewGameRequestHandlingState implements ServerState {
    ClientConnection connection;
    GameInstanceManager gameInstanceManager;

    public NewGameRequestHandlingState(ClientConnection connection, GameInstanceManager gameInstanceManager) {
        this.connection = connection;
        this.gameInstanceManager = gameInstanceManager;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        if (request instanceof StartNewGameRequest startNewGameRequest) {

            long clientID = startNewGameRequest.getClientID();
            ClientConnection clientInLobby = gameInstanceManager.takePlayerFromLobby(clientID);

            if (gameInstanceManager.gameInstanceOpenForNewPlayer()) {
                long gameInstanceID = gameInstanceManager.getCurrentOpenGameInstance().getGameInstanceID();

                GameInstance gameInstance = gameInstanceManager.getGameInstanceByID(gameInstanceID);
                gameInstance.notifyPlayerJoined(clientInLobby.username);
                gameInstanceManager.putPlayerInOpenGameInstance(clientInLobby);
                clientInLobby.out.writeObject(new NewGameResponse(gameInstanceID, RoundTurn.OTHER_PLAYER_TURN, gameInstanceManager.getAllQuestions()));
            } else {
                gameInstanceManager.startNewGameInstance();
                long gameInstanceID = gameInstanceManager.getCurrentOpenGameInstance().getGameInstanceID();
                gameInstanceManager.putPlayerInOpenGameInstance(clientInLobby);
                clientInLobby.out.writeObject(new NewGameResponse(gameInstanceID, RoundTurn.PLAYER_TURN, gameInstanceManager.getAllQuestions()));
            }
        }
        //TODO get actual gameinstanceID and turnToPlay and put here once its available



    }
}
