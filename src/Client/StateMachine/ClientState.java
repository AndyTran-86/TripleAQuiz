package Client.StateMachine;

import Responses.PlayerJoinedResponse;
import Responses.Response;

import java.io.IOException;

public interface ClientState {
    void handleResponse(Response response) throws IOException, ClassNotFoundException;
    void updateGUI();
    void handlePlayerJoined(PlayerJoinedResponse response);
}
