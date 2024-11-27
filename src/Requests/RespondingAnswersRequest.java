package Requests;

import Server.QuizDatabase.Question;
import Server.QuizDatabase.Category;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespondingAnswersRequest extends Request implements Serializable {
    long clientID;
    long gameInstanceID;
    List<Integer> result;
    Category selectedCategory;
    List<Question> answeredQuestions;

    public RespondingAnswersRequest(long clientID, long gameInstanceID, List<Integer> result) {
        this.clientID = clientID;
        this.gameInstanceID = gameInstanceID;
        this.result = result;

    }

    public long getClientID() {
        return clientID;
    }

    public long getGameInstanceID() {
        return gameInstanceID;
    }

    public List<Integer> getResult() {
        return result;
    }

}
