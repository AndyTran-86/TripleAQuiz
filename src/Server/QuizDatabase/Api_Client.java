package Server.QuizDatabase;

import Server.GameInstance;
import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Api_Client implements Runnable {
    private StringBuilder addedCategories;
    private List<QuestionsByCategory> all_questions;
    private final String categories_url = "https://opentdb.com/api_category.php";
    private final String url_a = "https://opentdb.com/api.php?amount=50&category=";
    private final String url_b = "&type=multiple";
    private final String url_any50category = "https://opentdb.com/api.php?amount=50&type=multiple";
    private final HttpClient client;
    private final Gson gson;
    private GameInstance gameInstance;

    public Api_Client(GameInstance gameInstance) {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.all_questions = new ArrayList<>();
        this.addedCategories = new StringBuilder();
        this.gameInstance = gameInstance;
    }

    public List<QuestionsByCategory> getAll_questions() {
        return all_questions;
    }

    public void notifyCategoriesReady() {
        gameInstance.setCategoriesReady();
    }

    public All_Categories getAllCategories() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(categories_url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return deSerializeCategories(response.body());
    }

    public QuestionsByCategory getQuestionByCategory() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url_any50category))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return deSerializeQuestionCategory(response.body());
    }

    public QuestionsByCategory getQuestionsByCategoryId(int categoryId) throws Exception {
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

    public QuestionsByCategory deSerializeQuestionCategory(String s) throws Exception {
        QuestionsByCategory q = gson.fromJson(s, QuestionsByCategory.class);
        if (q.response_code() != 0)
            throw new Exception("Category retrieve error: " + q.response_code());
        return q;
    }

    public String decodeHtmlFromString(String s) {
        s = StringEscapeUtils.unescapeHtml4(s);
        return s;
    }

    public void decodeHtmlFromAllQuestions(List<QuestionsByCategory> allQuestions) {
        for (QuestionsByCategory category : allQuestions) {
            if (category.results() != null) {
                for (Question q : category.results()) {
                    q.setCategory(decodeHtmlFromString(q.category()));
                    q.setType(decodeHtmlFromString(q.type()));
                    q.setDifficulty(decodeHtmlFromString(q.difficulty()));
                    q.setQuestion(decodeHtmlFromString(q.question()));
                    q.setCorrect_answer(decodeHtmlFromString(q.correct_answer()));
                    List<String> decodedIncorrect = new ArrayList<>();
                    for (String s : q.incorrect_answers()) {
                        decodedIncorrect.add(decodeHtmlFromString(s));
                    }
                    q.setIncorrect_answers(decodedIncorrect);
                }
            }
        }
    }

    @Override
    public void run() {
        getNewCategories(3);
        notifyCategoriesReady();
        System.out.println("notified first categories ready");
        getNewCategories(5);
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

    private void getNewCategories(int amountNewCategories) {
        try {
            int sleep = 6500;
            int counter = 0;

            while (counter < amountNewCategories) {
                QuestionsByCategory category = getQuestionByCategory();
                if (!addedCategories.toString().contains(category.results().getFirst().category())) {
                    all_questions.add(category);
                    System.out.println("Added category: " + category.results().getFirst().category());
                    Thread.sleep(sleep);
                    System.out.println("Slept: " + sleep + " ms");
                    counter++;
                    addedCategories.append(category.results().getFirst().category());
                } else {
                    System.out.println("Category already in database");
                    Thread.sleep(sleep);
                    System.out.println("Slept: " + sleep + " ms");
                }
            }
            decodeHtmlFromAllQuestions(all_questions);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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



