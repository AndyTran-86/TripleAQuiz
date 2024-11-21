package Server.QuizDatabase;

import java.io.Serializable;

public record Category(Integer id, String name) implements Serializable {
}
