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

    default List<QuestionsByCategory> pickRandomCategories(List<QuestionsByCategory> questions) {
        List<QuestionsByCategory> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(questions.size());
            result.add(questions.get(index));
            questions.remove(index);
        }
        return result;
    }
}
