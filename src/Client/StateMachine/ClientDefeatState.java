package Client.StateMachine;


import Client.Client;
import Client.GUI.MainFrameGUI;
import Responses.DefeatResponse;
import Responses.DefeatType;
import Responses.PlayerJoinedResponse;
import Responses.Response;

import javax.swing.*;
import java.io.IOException;

public class ClientDefeatState implements ClientState {
    Client client;
    MainFrameGUI guiMainFrame;

    public ClientDefeatState(Client client, MainFrameGUI guiMainFrame) {
        this.client = client;
        this.guiMainFrame = guiMainFrame;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        if (response instanceof DefeatResponse defeatResponse) {
            if (defeatResponse.getDefeatType() == DefeatType.LOSS) {
                guiMainFrame.getOtherPlayerScoreLabels()[client.getCurrentRound()-2].setText(String.valueOf(client.getOpponentScorePreviousRound()));
            }
            guiMainFrame.getPlayerTurnLabel().setText("            DEFEAT");
            guiMainFrame.getFinalScorePlayer().setText("5");
            guiMainFrame.getFinalScoreOtherPlayer().setText("4");
            guiMainFrame.showScoreBoardView();
//            JOptionPane.showMessageDialog(guiMainFrame.getFrame(), "Defeat response received");

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
