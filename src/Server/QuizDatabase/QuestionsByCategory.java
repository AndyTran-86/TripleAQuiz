package Server.QuizDatabase;

import java.util.List;

public record QuestionsByCategory(Integer response_code, List<Question> results) {
}
