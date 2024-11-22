package Server;

import Responses.PlayerJoinedResponse;
import Server.QuizDatabase.Api_Client;
import Server.QuizDatabase.Question;
import Server.QuizDatabase.QuestionsByCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameInstance {
    private static long gameInstanceIDIncrementor = 1;
    private final long gameInstanceID;
    private final List<ClientConnection> players;
    private boolean categoriesReady;

    public GameInstance() {
        this.gameInstanceID = gameInstanceIDIncrementor++;
        this.players = new ArrayList<>();
        categoriesReady = false;;
    }

    public long getGameInstanceID() {
        return gameInstanceID;
    }

    public void addPlayer(ClientConnection clientConnection) {
        this.players.add(clientConnection);
    }

    public boolean isFull() {
        return players.size() > 1;
    }

    public boolean isFirstPlayer() {
        return players.size() == 1;
    }

    public void notifyPlayerJoined(String playerName) throws IOException {
        for (ClientConnection clientConnection : players) {
            clientConnection.out.writeObject(new PlayerJoinedResponse(playerName));
        }
    }

    public void setCategoriesReady() {
        this.categoriesReady = true;
    }

    public boolean categoriesReady() {
        return categoriesReady;
    }
}
