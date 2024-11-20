package Server.QuizDatabase;

import java.util.ArrayList;
import java.util.List;

public class All_Questions {
    private List<QuestionsByCategory> all_questions;

    public All_Questions() {
        all_questions = new ArrayList<>();
    }

    public void addCategory(QuestionsByCategory category){
        QuestionsByCategory q = category;
        all_questions.add(q);
    }

    public List<QuestionsByCategory> getAll_questions(){
        return all_questions;
    }
}
