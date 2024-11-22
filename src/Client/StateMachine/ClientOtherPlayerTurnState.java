package Client.StateMachine;

import Client.ClientGUI;
import Client.Client;
import Responses.PlayerJoinedResponse;
import Responses.Response;

import javax.swing.*;
import java.io.IOException;

public class ClientOtherPlayerTurnState  implements ClientState {
    Client client;
    ClientGUI gui;

    public ClientOtherPlayerTurnState(Client client, ClientGUI gui) {
        this.client = client;
        this.gui = gui;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        JOptionPane.showMessageDialog(gui.frame, "Round played response received with OTHER PLAYER TURN");
    }

    @Override
    public void updateGUI() {
        System.out.println("Updating GUI");
    }


    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
