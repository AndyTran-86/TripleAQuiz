package Server.QuizDatabase;

import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Api_Client implements Runnable{
    private Random rand = new Random();
    private All_Questions all_questions;
    private final String categories_url = "https://opentdb.com/api_category.php";
    private final String url_a = "https://opentdb.com/api.php?amount=3&category=";
    private final String url_b = "&type=multiple";
    private final HttpClient client;
    private final Gson gson;

    public Api_Client() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.all_questions = new All_Questions();
    }

    public List<QuestionsByCategory> getAll_questions() {
        return all_questions.getAll_questions();
    }

    public String getAllCategories() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(categories_url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getQuestionsByCategoryId(int categoryId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url_a + categoryId + url_b))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


    public All_Categories deSerializeCategories(String s) throws MalformedURLException {
        return gson.fromJson(s, All_Categories.class);
    }

    public QuestionsByCategory deSerializeQuestionCategory(String s) throws MalformedURLException {
        return gson.fromJson(s, QuestionsByCategory.class);
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
        try {
            String stringCategories = getAllCategories();

            All_Categories allCategories = deSerializeCategories(stringCategories);

            //Bara 1 Api-request per 5 s / ip är tillåtet
            //Max 50 frågor per request
            //Bara en kategori per request

            for (int i = 0; i < 9; i++) {
                int index = rand.nextInt(allCategories.trivia_categories().size());
                int id = allCategories.trivia_categories().get(index).id();
                String questionCategory = getQuestionsByCategoryId(id);
                QuestionsByCategory category = deSerializeQuestionCategory(questionCategory);
                if (category.results() == null)
                    System.out.println("its null");
                System.out.println(category.results().getFirst().question());
                all_questions.addCategory(category);
            }

            decodeHtmlFromAllQuestions(all_questions.getAll_questions());

            for (QuestionsByCategory q : all_questions.getAll_questions())
                for (Question question : q.results())
                    System.out.println(question.question());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}



