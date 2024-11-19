package Server;

import java.io.Serializable;

public class Response implements Serializable {
    private QuizQuestion quizQuestion;
    private String message;

    public Response(QuizQuestion quizQuestion, String message) {
        this.quizQuestion = quizQuestion;
        this.message = message;
    }

    public QuizQuestion getQuizQuestion() {
        return quizQuestion;
    }

    public String getMessage() {
        return message;
    }
}
