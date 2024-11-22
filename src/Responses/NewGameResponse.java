package Responses;

import Server.QuizDatabase.QuestionsByCategory;

import java.io.Serializable;
import java.util.List;

public class NewGameResponse extends Response implements Serializable {
    long gameInstanceID;
    RoundTurn turnToPlay;
    private List<QuestionsByCategory> questionsToClient;

    public NewGameResponse(long gameInstanceID, RoundTurn turnToPlay, List<QuestionsByCategory> questionsToClient) {
        this.gameInstanceID = gameInstanceID;
        this.questionsToClient = questionsToClient;
        this.turnToPlay = turnToPlay;
    }

    public List<QuestionsByCategory> getQuestionsToClient() {
        return questionsToClient;
    }

    public long getGameInstanceID() {
        return gameInstanceID;
    }

    public RoundTurn getTurnToPlay() {
        return turnToPlay;
    }
}
