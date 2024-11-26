package Server.QuizDatabase;

import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Api_Client {
    private StringBuilder addedCategories;
    List<String> categoryNames;
    private List<Category> all_categories;
    private List<Category> sorted_categories;
    private List<Boolean> fullCategories;

    private final String categories_url = "https://opentdb.com/api_category.php";
    private final String url_a = "https://opentdb.com/api.php?amount=50&category=";
    private final String url_b = "&type=multiple";
    private final String url_any50category = "https://opentdb.com/api.php?amount=50&type=multiple";
    private final HttpClient client;
    private final Gson gson;
    private final File temp;

    public Api_Client() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.sorted_categories = new ArrayList<>();
        this.addedCategories = new StringBuilder();
        this.temp = new File("src/Server/QuizDatabase/questions.ser");
        this.fullCategories = new ArrayList<>();
        this.categoryNames = new ArrayList<>();
    }

    public List<Category> getAll_categories() {
        return all_categories;
    }

    public QuestionsFromApi getQuestionFromApi() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url_any50category))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return deSerializeQuestionCategory(response.body());
    }

    public QuestionsFromApi getQuestionsByCategoryId(int categoryId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url_a + categoryId + url_b))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return deSerializeQuestionCategory(response.body());
    }

    public All_Categories deSerializeCategories(String s) throws MalformedURLException {
        return gson.fromJson(s, All_Categories.class);
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

    private void serializeValidCategories(List<Category> validCategories) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("validIds.ser", true))) {
            for (Category c : validCategories) {
                out.writeObject(c);
            }
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

                while (sorted_categories.size() < 8 || !isAllFull()) {
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
                if (sorted_categories.size() == 8 && isAllFull()) {
                    break;
                }
                if (addedCategories.toString().contains(question.category())) {
                    int index = categoryNames.indexOf(question.category());
                    if (sorted_categories.get(index).questions().size() < 3) {
                        sorted_categories.get(index).questions().add(question);
                    } else {
                        fullCategories.set(index, true);
                    }
                } else if (sorted_categories.size() < 8) {
                    sorted_categories.add(new Category(question.category(), new ArrayList<>()));
                    categoryNames.add(question.category());
                    addedCategories.append(question.category());
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

    private Category convertToCategoryObject(QuestionsFromApi original) {
        List<Question> questions = new ArrayList<>();
        String name = original.results().getFirst().category();
        questions.addAll(original.results());
        return new Category(name, questions);
    }

    private List<Category> deSerializeValidCategories() {
        List<Category> ids = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("validIds.ser"))) {
            while (true) {
                try {
                    Category c = (Category) in.readObject();
                    ids.add(c);
                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ids;
    }
}



