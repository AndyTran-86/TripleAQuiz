package Client;

import Server.QuizDatabase.Category;
import Server.QuizDatabase.Question;
import Server.QuizDatabase.QuestionsByCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientQuestionData implements Serializable {
    private  Random random;
    private List<Integer> resultsPerRound;
    private List<Category> allCategories;
    private List<Category> remainingCategories;
    private String selectedCategory;

    public ClientQuestionData() {
        this.resultsPerRound = new ArrayList<>();
        this.remainingCategories = new ArrayList<>();
        this.selectedCategory = "";
        this.random = new Random();
    }

    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public void chooseCategory(Category category) {
        this.selectedCategory = category.name();
    }

    public List<Category> getSelectedCategoryQuestions(String selectedCategory) {
        return null;
    }


    public List<Category> pickRandomCategories(List<Category> categories) {
        List<Category> result = new ArrayList<>();
        return result;
    }

}
