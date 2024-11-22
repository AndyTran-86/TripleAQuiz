package Server.QuizDatabase;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public final class Question implements Serializable {
    private String type;
    private String difficulty;
    private String category;
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    public Question(String type,
                    String difficulty,
                    String category,
                    String question,
                    String correct_answer,
                    List<String> incorrect_answers) {
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public void setIncorrect_answers(List<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    public String type() {
        return type;
    }

    public String difficulty() {
        return difficulty;
    }

    public String category() {
        return category;
    }

    public String question() {
        return question;
    }

    public String correct_answer() {
        return correct_answer;
    }

    public List<String> incorrect_answers() {
        return incorrect_answers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Question) obj;
        return Objects.equals(this.type, that.type) &&
                Objects.equals(this.difficulty, that.difficulty) &&
                Objects.equals(this.category, that.category) &&
                Objects.equals(this.question, that.question) &&
                Objects.equals(this.correct_answer, that.correct_answer) &&
                Objects.equals(this.incorrect_answers, that.incorrect_answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, difficulty, category, question, correct_answer, incorrect_answers);
    }

    @Override
    public String toString() {
        return "Question[" +
                "type=" + type + ", " +
                "difficulty=" + difficulty + ", " +
                "category=" + category + ", " +
                "question=" + question + ", " +
                "correct_answer=" + correct_answer + ", " +
                "incorrect_answers=" + incorrect_answers + ']';
    }
}
