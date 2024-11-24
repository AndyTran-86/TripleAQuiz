package Client;

import Server.QuizDatabase.Category;
import Server.QuizDatabase.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientQuestionData implements Serializable {
    private Random random;
    private List<Integer> resultsPerRound;
    private List<Category> allCategories;
    private List<Category> remainingCategories;
    private Category selectedCategory;
    private List<Category> threeRandomCategories;
    private int questionsPlayed;
    private List<Question> selectedCategoryQuestions;

    public ClientQuestionData() {
        this.resultsPerRound = new ArrayList<>();
        this.remainingCategories = new ArrayList<>();
        this.allCategories = new ArrayList<>();
        this.threeRandomCategories = new ArrayList<>();
        this.selectedCategoryQuestions = new ArrayList<>();
        this.random = new Random();
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public List<Integer> getResultsPerRound() {
        return resultsPerRound;
    }

    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
        this.remainingCategories = allCategories;
    }

    public boolean checkAnswer(String answer) {
        String correctAnswer = selectedCategoryQuestions.get(questionsPlayed).correct_answer();
        if (correctAnswer.equals(answer)) {
            resultsPerRound.add(1);
        } else {
            resultsPerRound.add(0);
        }
        questionsPlayed++;
        return (correctAnswer.equals(answer));
    }

    public void selectCategory(Category category) {
        this.selectedCategory = category;
        this.remainingCategories.remove(category);
        setSelectedCategoryQuestions();
    }

    public void setSelectedCategoryQuestions() {
        this.questionsPlayed = 0;
        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(selectedCategory.questions().size());
            selectedCategoryQuestions.add(selectedCategory.questions().remove(randomIndex));
        }
    }

    public void setSelectedCategoryFromOpponent(Category category, List<Question> questions) {
        this.questionsPlayed = 0;
        this.remainingCategories.remove(category);
        this.selectedCategoryQuestions = questions;
    }

    public List<Question> getSelectedCategoryQuestions() {
        return selectedCategoryQuestions;
    }

    public void setThreeRandomCategories() {
        if (!threeRandomCategories.isEmpty())
            threeRandomCategories.clear();
        for(int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(allCategories.size());
            threeRandomCategories.add(remainingCategories.get(randomIndex));
        }
    }

}
