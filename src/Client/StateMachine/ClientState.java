package Client.StateMachine;

import Responses.PlayerJoinedResponse;
import Responses.Response;
import Server.QuizDatabase.QuestionsByCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface ClientState {
    void handleResponse(Response response) throws IOException, ClassNotFoundException;
    void updateGUI();
    void handlePlayerJoined(PlayerJoinedResponse response);
}
