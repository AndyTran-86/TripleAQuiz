package Server.QuizDatabase;


public class TestMain {
    public static void main(String[] args) {
        Api_Client apiClient = new Api_Client();
        new Thread(apiClient).start();
    }
}
