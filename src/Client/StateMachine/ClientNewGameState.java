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
            guiMainFrame.resetScoreBoard();
            client.setGameOver(false);
            client.getQuestionData().setQuestionsPlayed(0);
            client.setCurrentRound(0);
            client.setGameInstanceID(newGameResponse.getGameInstanceID());
            client.setAllCategories(newGameResponse.getCategoriesToClient());
            client.getQuestionData().setThreeRandomCategories();
            client.updateRoundCounter();
            client.updateRoundCounter();
        }
    }

    @Override
    public void updateGUI() {

        guiMainFrame.getSurrenderButton().setVisible(true);
        guiMainFrame.showScoreBoardView();
        guiMainFrame.getFrame().repaint();
        guiMainFrame.getFrame().revalidate();
    }

    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
