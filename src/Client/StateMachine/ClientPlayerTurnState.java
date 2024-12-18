package Client.StateMachine;


import Client.Client;
import Client.GUI.MainFrameGUI;
import Responses.PlayerJoinedResponse;
import Responses.Response;
import Responses.RoundPlayedResponse;

import java.io.IOException;

public class ClientPlayerTurnState implements ClientState {
    Client client;
    MainFrameGUI guiMainFrame;

    public ClientPlayerTurnState(Client client, MainFrameGUI guiMainFrame) {
        this.client = client;
        this.guiMainFrame = guiMainFrame;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        if (response instanceof RoundPlayedResponse roundPlayedResponse) {
            System.out.println("Current round: " + client.getCurrentRound());
            client.updateRoundCounter();
            client.setRespondingTurn(true);
            if (client.getCurrentRound()>1) {
                guiMainFrame.getOtherPlayerScoreLabels()[client.getCurrentRound()-2].setText(String.valueOf(client.getOpponentScorePreviousRound()));
            }
            guiMainFrame.getOtherPlayerScoreLabels()[client.getCurrentRound()-1].setText(String.valueOf(roundPlayedResponse.getResult().stream().reduce(0, Integer::sum)));
            client.getQuestionData().setSelectedCategoryFromOpponent(roundPlayedResponse.getSelectedCategory());
            client.getQuestionData().setThreeRandomCategories();
            guiMainFrame.setCategoryBoardNames(client.getQuestionData().getThreeRandomCategories());
            System.out.println("handled round played response");
        }

    }

    @Override
    public void updateGUI() {
        guiMainFrame.getSurrenderButton().setVisible(true);
        guiMainFrame.enablePlayButton();
        guiMainFrame.setPlayerTurnLabelToPlayer();
        guiMainFrame.showScoreBoardView();
    }

    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        client.setAwaitingPlayer(false);
        guiMainFrame.setOtherPlayerUserName(response.getUsername());

    }
}
