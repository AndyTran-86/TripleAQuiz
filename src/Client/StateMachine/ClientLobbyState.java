package Client.StateMachine;

import Client.ClientGUI;
import Client.Client;
import Responses.ListeningResponse;
import Responses.Response;

import javax.swing.*;
import java.io.IOException;

public class ClientLobbyState implements ClientState {
    Client client;
    ClientGUI gui;

    public ClientLobbyState(Client client, ClientGUI gui) {
        this.client = client;
        this.gui = gui;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        if (response instanceof ListeningResponse listeningResponse) {
            long id = listeningResponse.getClientID();
            client.setClientID(id);
        }
        JOptionPane.showMessageDialog(gui.frame, "Listening connection established with clientID: " + client.getClientID());
    }

    @Override
    public void updateGUI() {
        System.out.println("Updating GUI");
    }
}
