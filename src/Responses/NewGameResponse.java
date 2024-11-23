package Responses;

import Server.QuizDatabase.Category;

import java.io.Serializable;
import java.util.List;

public class NewGameResponse extends Response implements Serializable {
    long gameInstanceID;
    RoundTurn turnToPlay;
    private List<Category> categoriesToClient;

    public NewGameResponse(long gameInstanceID, RoundTurn turnToPlay, List<Category> questionsToClient) {
        this.gameInstanceID = gameInstanceID;
        this.categoriesToClient = questionsToClient;
        this.turnToPlay = turnToPlay;
    }

    public List<Category> getCategoriesToClient() {
        return categoriesToClient;
    }

    public long getGameInstanceID() {
        return gameInstanceID;
    }

    public RoundTurn getTurnToPlay() {
        return turnToPlay;
    }
}
