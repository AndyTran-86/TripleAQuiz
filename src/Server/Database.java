package Server;

import java.util.ArrayList;
import java.util.List;

public class Database {
    List<QuizQuestion> quizQuestions = new ArrayList<>();

    public Database() {
        addNewQuiz("What is the capital of France?",
                new String[]{"Marseille", "Paris", "Stockholm"},
                2);
    }

    private void addNewQuiz(String question, String[] options, int rightAnswer) {
        quizQuestions.add(new QuizQuestion(question, options, rightAnswer));
    }
}
