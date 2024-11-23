package Server.QuizDatabase;

import java.io.Serializable;
import java.util.List;

public record Category(String name, List<Question> questions) implements Serializable {
}
