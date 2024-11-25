package Requests;

import Server.QuizDatabase.Question;
import Server.QuizDatabase.Category;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundPlayedRequest extends Request implements Serializable {
    long clientID;
    long gameInstanceID;
    List<Integer> result;
    Category selectedCategory;
    List<Question> answeredQuestions;

    public RoundPlayedRequest(long clientID, long gameInstanceID, List<Integer> result, Category selectedCategory, List<Question> answeredQuestions) {
        this.clientID = clientID;
        this.gameInstanceID = gameInstanceID;
        this.result = result;
        this.selectedCategory = selectedCategory;
        this.answeredQuestions = answeredQuestions;
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

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public List<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }
}
