package Client.StateMachine;


import Client.Client;
import Client.GUI.MainFrameGUI;
import Responses.PlayerJoinedResponse;
import Responses.Response;

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
        JOptionPane.showMessageDialog(null, "Round played response received with THIS PLAYER TURN");
    }

    @Override
    public void updateGUI() {
        System.out.println("Updating GUI");
        guiMainFrame.enablePlayButton();
        guiMainFrame.setPlayerTurnLabelToPlayer();
        guiMainFrame.setScoreBoardView();
    }

    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        guiMainFrame.setOtherPlayerUserName(response.getUsername());

    }
}
