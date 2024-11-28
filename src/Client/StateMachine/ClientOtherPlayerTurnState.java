package Client.StateMachine;


import Client.Client;
import Client.GUI.MainFrameGUI;
import Responses.PlayerJoinedResponse;
import Responses.RespondingAnswersResponse;
import Responses.Response;
import Responses.RoundPlayedResponse;

import javax.swing.*;
import java.io.IOException;

public class ClientOtherPlayerTurnState  implements ClientState {
    Client client;
    MainFrameGUI guiMainFrame;

    public ClientOtherPlayerTurnState(Client client, MainFrameGUI guiMainFrame) {
        this.client = client;
        this.guiMainFrame = guiMainFrame;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        if (response instanceof RoundPlayedResponse roundPlayedResponse) {
            client.updateRoundCounter();
        }

        if (response instanceof RespondingAnswersResponse respondingAnswersResponse) {
            client.setOpponentScorePreviousRound(respondingAnswersResponse.getResult().stream().reduce(0, Integer::sum));
//            guiMainFrame.getOtherPlayerScoreLabels()[client.getCurrentRound()-1].setText(String.valueOf(respondingAnswersResponse.getResult().stream().reduce(0, Integer::sum)));
            client.updateRoundCounter();

        }
    }

    @Override
    public void updateGUI() {
        guiMainFrame.getSurrenderButton().setVisible(true);
        guiMainFrame.disablePlayButton();
        guiMainFrame.setPlayerTurnLabelToOtherPlayer();
        guiMainFrame.showScoreBoardView();
    }


    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        guiMainFrame.setOtherPlayerUserName(response.getUsername());
    }
}
