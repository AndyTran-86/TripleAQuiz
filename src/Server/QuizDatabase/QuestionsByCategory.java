package Server.QuizDatabase;

import java.io.Serializable;
import java.util.List;

public record QuestionsByCategory(Integer response_code, List<Question> results) implements Serializable {
}
