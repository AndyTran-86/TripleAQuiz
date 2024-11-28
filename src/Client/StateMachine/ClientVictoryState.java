package Client.StateMachine;


import Client.Client;
import Client.GUI.MainFrameGUI;
import Responses.PlayerJoinedResponse;
import Responses.Response;
import Responses.VictoryResponse;
import Responses.VictoryType;

import javax.swing.*;
import java.io.IOException;

public class ClientVictoryState implements ClientState {
    Client client;
    MainFrameGUI guiMainFrame;

    public ClientVictoryState(Client client, MainFrameGUI guiMainFrame) {
        this.client = client;
        this.guiMainFrame = guiMainFrame;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        if (response instanceof VictoryResponse victoryResponse) {
            if (victoryResponse.getVictoryType() == VictoryType.WIN) {
                guiMainFrame.getOtherPlayerScoreLabels()[client.getCurrentRound()-2].setText(String.valueOf(client.getOpponentScorePreviousRound()));
            }
            guiMainFrame.showScoreBoardView();
            JOptionPane.showMessageDialog(guiMainFrame.getFrame(), "Victory response received");
        }

    }

    @Override
    public void updateGUI() {
        guiMainFrame.getSurrenderButton().setVisible(false);
        System.out.println("Updating GUI");
    }

    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
