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
    private int maxQuestions, maxRounds;

    public ClientQuestionData() {
        this.resultsPerRound = new ArrayList<>();
        this.remainingCategories = new ArrayList<>();
        this.allCategories = new ArrayList<>();
        this.threeRandomCategories = new ArrayList<>();
        this.selectedCategoryQuestions = new ArrayList<>();
        this.selectedCategory = new Category(null, null);
        this.random = new Random();
    }

    public int getMaxQuestions() {
        return maxQuestions;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public List<Question> getAnsweredQuestions() {
        return selectedCategoryQuestions;
    }

    public List<Category> getThreeRandomCategories() {
        return threeRandomCategories;
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public List<Integer> getResultsPerRound() {
        return resultsPerRound;
    }

    public void setAllCategories(List<Category> allCategories) {
        this.maxRounds = allCategories.size() - 2;
        this.maxQuestions = allCategories.getFirst().questions().size();
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

    public void selectCategory(String categoryName) {
      for (Category c : threeRandomCategories) {
            if (c.name().equals(categoryName)) {
                this.selectedCategory = c;
                this.remainingCategories.remove(c);
                break;
                }
            }
        setSelectedCategoryQuestions();
    }

    public void setSelectedCategoryQuestions() { //onödig???!
        this.questionsPlayed = 0;
        selectedCategoryQuestions.clear();
        for (int i = 0; i < maxQuestions; i++) {
            int randomIndex = random.nextInt(selectedCategory.questions().size());
            selectedCategoryQuestions.add(selectedCategory.questions().remove(randomIndex));
        }
    }

    public void setSelectedCategoryFromOpponent(Category category, List<Question> questions) {
        this.questionsPlayed = 0;
        this.remainingCategories.remove(category);
//        for (Category c : remainingCategories) {
//            if (c.name().equals(category.name())) {
//                remainingCategories.remove(c);
//                break;
//            }
//        }
        this.selectedCategoryQuestions = questions;
    }

    public Question getSelectedCategoryQuestion() {
        return selectedCategoryQuestions.get(questionsPlayed);
    }

    public void setThreeRandomCategories() {
        if (!threeRandomCategories.isEmpty())
            threeRandomCategories.clear();
        for(int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(allCategories.size());
            while (threeRandomCategories.contains(allCategories.get(randomIndex)))
                randomIndex = random.nextInt(allCategories.size());
            threeRandomCategories.add(remainingCategories.get(randomIndex));
        }
    }

    public int getQuestionsPlayed() {
        return questionsPlayed;
    }

    public void setQuestionsPlayed(int questionsPlayed) {
        this.questionsPlayed = questionsPlayed;
    }
}
