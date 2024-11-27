package Server.StateMachine;

import Requests.Request;
import Requests.RespondingAnswersRequest;
import Requests.RoundPlayedRequest;
import Responses.RoundPlayedResponse;
import Responses.RoundTurn;
import Server.ClientConnection;
import Server.GameInstance;
import Server.GameInstanceManager;

import java.io.IOException;
import java.util.Map;

public class RespondingAnswersRequestHandlingState implements ServerState {
    ClientConnection connection;
    GameInstanceManager gameInstanceManager;

    public RespondingAnswersRequestHandlingState(ClientConnection connection, GameInstanceManager gameInstanceManager) {
        this.connection = connection;
        this.gameInstanceManager = gameInstanceManager;
    }

    @Override
    public void handleRequest(Request request) throws IOException, ClassNotFoundException {
        if (request instanceof RespondingAnswersRequest respondingAnswersRequest) {
            GameInstance gameInstance = gameInstanceManager.getGameInstanceByID(respondingAnswersRequest.getGameInstanceID());

            gameInstance.findCallingPlayer(respondingAnswersRequest.getClientID());
//            gameInstance.addRoundToCounter();
            gameInstance.updateGameScore(respondingAnswersRequest.getResult());

            gameInstance.notifyRespondedAnsweredQuestions(respondingAnswersRequest.getResult());
        }
    }
}
