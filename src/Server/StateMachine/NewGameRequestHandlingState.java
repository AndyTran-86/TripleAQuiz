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
            ClientConnection playerJoining = gameInstanceManager.takePlayerFromLobby(clientID);

            playerJoining.setUsername(startNewGameRequest.getUsername());

            if (gameInstanceManager.gameInstanceOpenForNewPlayer()) {

                GameInstance gameInstance = gameInstanceManager.getCurrentOpenGameInstance();
                gameInstanceManager.putPlayerInOpenGameInstance(playerJoining);
                gameInstance.findCallingPlayer(clientID);
                playerJoining.out.writeObject(new NewGameResponse(gameInstance.getGameInstanceID(), RoundTurn.OTHER_PLAYER_TURN, gameInstanceManager.getAllCategories()));
                gameInstance.notifyPlayerJoined();


            } else {

                gameInstanceManager.startNewGameInstance();
                gameInstanceManager.putPlayerInOpenGameInstance(playerJoining);
                long gameInstanceID = gameInstanceManager.getCurrentOpenGameInstance().getGameInstanceID();
                playerJoining.out.writeObject(new NewGameResponse(gameInstanceID, RoundTurn.PLAYER_TURN, gameInstanceManager.getAllCategories()));
            }
        }
    }
}
