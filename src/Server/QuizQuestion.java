package Server;

import java.io.Serializable;

public class QuizQuestion implements Serializable {
    private String question;
    private String[] options = new String[3];
    private int selectedAnswer;
    private int rightAnswer;

    public QuizQuestion(String question, String[] options, int rightAnswer) {
        this.question = question;
        this.options = options;
        if (rightAnswer >= 1 && rightAnswer <= 3)
            this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public void selectAnswer(int selectedAnswer) {
        if (selectedAnswer >= 1 && selectedAnswer <= 3)
            this.selectedAnswer = selectedAnswer;
    }
}
