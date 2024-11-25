package Client.StateMachine;


import Client.Client;
import Responses.NewGameResponse;
import Responses.PlayerJoinedResponse;
import Responses.Response;

import java.io.IOException;
import Client.GUI.MainFrameGUI;

public class ClientNewGameState  implements ClientState {
    Client client;
    MainFrameGUI guiMainFrame;

    public ClientNewGameState(Client client, MainFrameGUI guiMainFrame) {
        this.client = client;
        this.guiMainFrame = guiMainFrame;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        if (response instanceof NewGameResponse newGameResponse) {
            switch (newGameResponse.getTurnToPlay()) {
                case PLAYER_TURN -> {
                    client.setAllCategories(newGameResponse.getCategoriesToClient());
                    client.getQuestionData().setThreeRandomCategories();
                }

                case OTHER_PLAYER_TURN -> {
                    //TODO check if this is this needed
                    client.setAllCategories(newGameResponse.getCategoriesToClient());
                    client.getQuestionData().setThreeRandomCategories();
                }
            }
        }
    }

    @Override
    public void updateGUI() {
        guiMainFrame.setScoreBoardView();
    }

    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
