package Client.StateMachine;

import Client.ClientGUI;
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
        guiMainFrame.setPlayerTurn();
        guiMainFrame.setScoreBoardView();
    }

    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        guiMainFrame.setOtherPlayerLabel(response.getUsername());
        JOptionPane.showMessageDialog(null, response.getUsername() + " joined the game");
        //TODO add getUserName to data in client, then update gui with it?
    }
}
