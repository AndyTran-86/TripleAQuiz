package Client.StateMachine;

import Responses.Response;

import java.io.IOException;

public interface ClientState {
    void handleResponse(Response response) throws IOException, ClassNotFoundException;
    void updateGUI();
}
