package Server.QuizDatabase;

import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Api_Client {
    private List<String> categoryNames;
    private List<Category> sorted_categories;
    private List<Boolean> fullCategories;
    private final String url_any50category = "https://opentdb.com/api.php?amount=50&type=multiple";
    private final HttpClient client;
    private final Gson gson;
    private final File temp;
    private int totalCategories;
    private int totalQuestionsPerCategory;

    public Api_Client(int totalCategories, int totalQuestionsPerCategory) {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.sorted_categories = new ArrayList<>();
        this.temp = new File("src/Server/QuizDatabase/questions.ser");
        this.fullCategories = new ArrayList<>();
        this.categoryNames = new ArrayList<>();
        setTotalCategoriesAndQuestions(totalCategories, totalQuestionsPerCategory);
    }

    private void setTotalCategoriesAndQuestions(int totalCategories, int totalQuestionsPerCategory) {
        if (totalCategories >= 1 && totalCategories <= 8)
            this.totalCategories = totalCategories + 2;
        else if (totalCategories < 1)
            this.totalCategories = 3;
        else if (totalCategories > 8)
            this.totalCategories = 10;

        if (totalQuestionsPerCategory == 2)
            this.totalQuestionsPerCategory = 2;
        else if (totalQuestionsPerCategory <= 1)
            this.totalQuestionsPerCategory = 1;
        else if (totalQuestionsPerCategory >= 3) {
            this.totalQuestionsPerCategory = 3;
        }
    }

    public QuestionsFromApi getQuestionFromApi() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url_any50category))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return deSerializeQuestionCategory(response.body());
    }

    public QuestionsFromApi deSerializeQuestionCategory(String s) throws Exception {
        QuestionsFromApi q = gson.fromJson(s, QuestionsFromApi.class);
        if (q.response_code() != 0)
            throw new Exception("Category retrieve error: " + q.response_code());
        return q;
    }

    public String decodeHtmlFromString(String s) {
        s = StringEscapeUtils.unescapeHtml4(s);
        return s;
    }

    public void decodeHtmlFromAllQuestions() {
        List<Category> decodedSorted = new ArrayList<>();

        for (Category c : sorted_categories) {
            List<Question> decodedQuestions = new ArrayList<>();
            for (Question q : c.questions()) {
                List<String> decodedIncorrect = new ArrayList<>();
                for (String s : q.incorrect_answers())
                    decodedIncorrect.add(decodeHtmlFromString(s));
                decodedQuestions.add(new Question(
                        decodeHtmlFromString(q.type()),
                        decodeHtmlFromString(q.difficulty()),
                        decodeHtmlFromString(q.category()),
                        decodeHtmlFromString(q.question()),
                        decodeHtmlFromString(q.correct_answer()),
                        decodedIncorrect));
            }
            decodedSorted.add(new Category(decodeHtmlFromString(c.name()), decodedQuestions));
        }
        sorted_categories = decodedSorted;
    }

    public List<Category> deSerializeAllQuestions() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/Server/QuizDatabase/questions.ser"))) {
            return (List<Category>) in.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void serializeAllQuestions() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/Server/QuizDatabase/questions.ser"))) {
            out.writeObject(sorted_categories);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> getNewCategories() {

        if (temp.exists()) {
            sorted_categories = deSerializeAllQuestions();
            decodeHtmlFromAllQuestions();
            System.out.println("Categories in database:");
            for (Category category : sorted_categories)
                System.out.println(category.name());
        }
        else {
            try {
                int sleep = 6500;

                while (sorted_categories.size() < totalCategories || !isAllFull()) {
                    QuestionsFromApi questions = getQuestionFromApi();
                    sortQuestionsToCategory(questions);

                    if (!isAllFull()) {
                        System.out.println("Received categories success. Not all categories full, sleeping.");
                        Thread.sleep(sleep);
                        System.out.println("Slept " + sleep + " ms.");
                    }
                }
                decodeHtmlFromAllQuestions();
                serializeAllQuestions();
                System.out.println("All questions saved." + "\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return (sorted_categories);
    }

    private void sortQuestionsToCategory(QuestionsFromApi q) {

            for (Question question : q.results()) {
                if (sorted_categories.size() == totalCategories && isAllFull()) {
                    break;
                }
                String cat = question.category();
                if (categoryNames.stream().anyMatch(str -> str.equals(cat))) {
                    int index = categoryNames.indexOf(question.category());
                    if (sorted_categories.get(index).questions().size() < totalQuestionsPerCategory) {
                        sorted_categories.get(index).questions().add(question);
                    } else {
                        fullCategories.set(index, true);
                    }
                } else if (sorted_categories.size() < totalCategories) {
                    sorted_categories.add(new Category(question.category(), new ArrayList<>()));
                    categoryNames.add(question.category());
                    fullCategories.add(false);
                    sorted_categories.getLast().questions().add(question);
                }
            }
    }

    private boolean isAllFull() {
        for (boolean b : fullCategories) {
            if (!b)
                return false;
        }
        return true;
    }
}



