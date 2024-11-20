package Client.StateMachine;

import Client.ClientGUI;
import Client.Client;
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
        JOptionPane.showMessageDialog(gui.frame, "New game response received");
    }

    @Override
    public void updateGUI() {
        System.out.println("Updating GUI");
    }
}
