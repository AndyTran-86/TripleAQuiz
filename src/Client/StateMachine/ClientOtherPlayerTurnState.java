package Client.StateMachine;

import Client.ClientGUI;
import Client.Client;
import Responses.Response;

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
        System.out.println("Handling " + response.getClass());
    }

    @Override
    public void updateGUI() {
        System.out.println("Updating GUI");
    }
}
