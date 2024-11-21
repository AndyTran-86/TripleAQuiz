package Client.StateMachine;

import Client.ClientGUI;
import Client.Client;
import Responses.NewGameResponse;
import Responses.Response;

import javax.swing.*;
import java.io.IOException;

public class ClientNewGameState  implements ClientState {
    Client client;
    ClientGUI gui;

    public ClientNewGameState(Client client, ClientGUI gui) {
        this.client = client;
        this.gui = gui;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        if (response instanceof NewGameResponse newGameResponse) {
            switch (newGameResponse.getTurnToPlay()) {
                case PLAYER_TURN -> JOptionPane.showMessageDialog(gui.frame, "New Game response received with THIS PLAYER TURN");
                case OTHER_PLAYER_TURN -> JOptionPane.showMessageDialog(gui.frame, "New Game response received with OTHER PLAYER TURN");
            }
        }
    }

    @Override
    public void updateGUI() {
        System.out.println("Updating GUI");
    }
}
