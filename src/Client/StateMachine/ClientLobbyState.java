package Client.StateMachine;


import Client.Client;
import Client.GUI.MainFrameGUI;
import Responses.ListeningResponse;
import Responses.PlayerJoinedResponse;
import Responses.Response;


import java.io.IOException;

public class ClientLobbyState implements ClientState {
    Client client;
    MainFrameGUI guiMainFrame;

    public ClientLobbyState(Client client, MainFrameGUI guiMainFrame) {
        this.client = client;
        this.guiMainFrame = guiMainFrame;
    }


    @Override
    public void handleResponse(Response response) throws IOException, ClassNotFoundException {
        if (response instanceof ListeningResponse listeningResponse) {
            long id = listeningResponse.getClientID();
            client.setClientID(id);

        }
    }

    @Override
    public void updateGUI() {
        guiMainFrame.showLobbyView();
    }

    @Override
    public void handlePlayerJoined(PlayerJoinedResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
