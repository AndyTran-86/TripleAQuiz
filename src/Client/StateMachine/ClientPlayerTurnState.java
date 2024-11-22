package Client.StateMachine;

import Client.ClientGUI;
import Client.Client;
import Responses.PlayerJoinedResponse;
import Responses.Response;

import javax.swing.*;
import java.io.IOException;

public class ClientPlayerTurnState implements ClientState {
    Client client;
    ClientGUI gui;

    public ClientPlayerTurnState(Client client, ClientGUI gui) {
        this.client = client;
        this.gui = gui;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        JOptionPane.showMessageDialog(gui.frame, "Round played response received with THIS PLAYER TURN");
    }

    @Override
    public void updateGUI() {
        System.out.println("Updating GUI");
    }

    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        String opponentUserName = response.getUsername();
        JOptionPane.showMessageDialog(gui.frame, opponentUserName + " joined the game");
        //TODO add getUserName to data in client, then update gui with it?
    }
}
