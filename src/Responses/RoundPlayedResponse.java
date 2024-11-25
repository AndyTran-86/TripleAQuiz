package Responses;

import Server.QuizDatabase.Category;
import Server.QuizDatabase.Question;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RoundPlayedResponse extends Response implements Serializable {
    RoundTurn turnToPlay;
    List<Integer> result;
    Category selectedCategory;
    List<Question> answeredQuestions;

    public RoundPlayedResponse(RoundTurn turnToPlay, List<Integer> result, Category selectedCategory, List<Question> answeredQuestions) {
        this.turnToPlay = turnToPlay;
        this.result = result;
    }

    public RoundTurn getTurnToPlay() {
        return turnToPlay;
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
