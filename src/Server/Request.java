package Server;

import java.io.Serializable;

public class Request implements Serializable {
    QuizQuestion quizQuestion;
    int state;

    public Request(QuizQuestion quizQuestion, int state) {
        this.quizQuestion = quizQuestion;
        this.state = state;
    }
}
