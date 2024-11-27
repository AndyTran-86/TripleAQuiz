package Responses;

import Server.QuizDatabase.Category;
import Server.QuizDatabase.Question;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RespondingAnswersResponse extends Response implements Serializable {
    RoundTurn turnToPlay;
    List<Integer> result;
    Category selectedCategory;
    List<Question> answeredQuestions;

    public RespondingAnswersResponse(List<Integer> result) {
        this.result = result;
    }

    public List<Integer> getResult() {
        return result;
    }

}
