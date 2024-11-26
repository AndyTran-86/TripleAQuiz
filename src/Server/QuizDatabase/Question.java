package Server.QuizDatabase;

import java.io.Serializable;
import java.util.List;

public record Question(String type,
                       String difficulty,
                       String category,
                       String question,
                       String correct_answer,
                       List<String> incorrect_answers) implements Serializable {
}
