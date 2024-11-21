package Server.QuizDatabase;

import java.util.List;
import java.util.Objects;

public final class All_Categories {
    private List<Category> trivia_categories;

    public All_Categories(List<Category> trivia_categories) {
        this.trivia_categories = trivia_categories;
    }

    public List<Category> trivia_categories() {
        return trivia_categories;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (All_Categories) obj;
        return Objects.equals(this.trivia_categories, that.trivia_categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trivia_categories);
    }

    @Override
    public String toString() {
        return "All_Categories[" +
                "trivia_categories=" + trivia_categories + ']';
    }

}
