package Client;

import Server.QuizDatabase.Category;
import Server.QuizDatabase.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientQuestionData implements Serializable {
    private List<Integer> resultsPerRound;
    private List<Category> allCategories;
    private List<Category> remainingCategories;
    private Category selectedCategory;
    private List<Category> threeRandomCategories;
    private int questionsPlayed;
    private int maxQuestions;

    public ClientQuestionData() {
        this.resultsPerRound = new ArrayList<>();
        this.remainingCategories = new ArrayList<>();
        this.allCategories = new ArrayList<>();
        this.threeRandomCategories = new ArrayList<>();
        this.selectedCategory = new Category(null, null);
    }

    public int getMaxQuestions() {
        return maxQuestions;
    }

    public List<Question> getAnsweredQuestions() {
        return selectedCategory.questions();
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
        this.maxQuestions = allCategories.getFirst().questions().size();
        this.allCategories = allCategories;
        this.remainingCategories = allCategories;
    }

    public boolean checkAnswer(String answer) {
        String correctAnswer = selectedCategory.questions().get(questionsPlayed).correct_answer();
        if (correctAnswer.equals(answer)) {
            resultsPerRound.add(1);
        } else {
            resultsPerRound.add(0);
        }
        questionsPlayed++;
        return (correctAnswer.equals(answer));
    }

    public void selectCategory(String categoryName) {
        this.questionsPlayed = 0;
        for (Category c : threeRandomCategories) {
            if (c.name().equals(categoryName)) {
                this.selectedCategory = c;
                this.remainingCategories.remove(c);
                break;
                }
            }
    }

    public void setSelectedCategoryFromOpponent(Category category) {
        this.questionsPlayed = 0;
        for (Category c : allCategories) {
           if (c.name().equals(category.name())) {
               this.selectedCategory = c;
               if (remainingCategories.size() > 3)
                   remainingCategories.remove(c);
               break;
           }
       }
    }

    public Question getSelectedCategoryQuestion() {
        return selectedCategory.questions().get(questionsPlayed);
    }

    public void setThreeRandomCategories() {
        if (!threeRandomCategories.isEmpty())
            threeRandomCategories.clear();

        Collections.shuffle(remainingCategories);
        for (int i = 0; i < 3; i++)
            threeRandomCategories.add(remainingCategories.get(i));
    }

    public int getQuestionsPlayed() {
        return questionsPlayed;
    }

    public void setQuestionsPlayed(int questionsPlayed) {
        this.questionsPlayed = questionsPlayed;
    }
}
