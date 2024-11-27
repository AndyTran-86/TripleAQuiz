package Client.StateMachine;


import Client.Client;
import Client.GUI.MainFrameGUI;
import Responses.PlayerJoinedResponse;
import Responses.Response;
import Responses.RoundPlayedResponse;

import javax.swing.*;
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
            client.updateRoundCounter();
            client.setRespondingTurn(true);
            guiMainFrame.getOtherPlayerScoreLabels()[client.getCurrentRound()-1].setText(String.valueOf(roundPlayedResponse.getResult().stream().reduce(0, Integer::sum)));
            client.getQuestionData().setSelectedCategoryFromOpponent(roundPlayedResponse.getSelectedCategory(), roundPlayedResponse.getAnsweredQuestions());
            client.getQuestionData().setThreeRandomCategories();
            guiMainFrame.setCategoryBoardNames(client.getQuestionData().getThreeRandomCategories());
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
        guiMainFrame.setOtherPlayerUserName(response.getUsername());

    }
}
