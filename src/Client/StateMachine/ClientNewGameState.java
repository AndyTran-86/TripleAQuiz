package Client.StateMachine;

import Client.ClientGUI;
import Client.Client;
import Responses.NewGameResponse;
import Responses.PlayerJoinedResponse;
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
                case PLAYER_TURN -> {
                    client.setAllCategories(newGameResponse.getCategoriesToClient());
                    //Lite arbete att utföra - Anton
                    //gui.getCategorySelectionBoard().setCategorySelectionboard(pickRandomCategories(categories));
                    gui.setMainPanel(gui.getCategorySelectionBoard().getCategoryMainPanel());
                    updateGUI();
                }

                case OTHER_PLAYER_TURN -> JOptionPane.showMessageDialog(gui.frame, "New Game response received with OTHER PLAYER TURN - in gameInstance: " + newGameResponse.getGameInstanceID());
            }
        }
    }

    @Override
    public void updateGUI() {
        gui.updateGUI();
        System.out.println("Updating GUI");
    }

    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
