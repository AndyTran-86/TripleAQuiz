package Server;

import Client.Client;
import Responses.*;
import Server.QuizDatabase.Api_Client;
import Server.QuizDatabase.Category;
import Server.QuizDatabase.Question;
import Server.QuizDatabase.QuestionsByCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInstance {
    private static long gameInstanceIDIncrementor = 1;
    private final long gameInstanceID;
    private final GameInstanceManager gameInstanceManager;
    private final Map<ClientConnection, Integer> players;
    private boolean categoriesReady;
    private ClientConnection callingPlayer;
    private ClientConnection nonCallingPlayer;
    private final int maxRounds;
    private int currentRoundPerPlayer;

    public GameInstance(GameInstanceManager gameInstanceManager) {
        this.gameInstanceID = gameInstanceIDIncrementor++;
        this.gameInstanceManager = gameInstanceManager;
        this.players = new HashMap<>();
        maxRounds = 8;
        currentRoundPerPlayer = 0;
        categoriesReady = false;;
    }

    public long getGameInstanceID() {
        return gameInstanceID;
    }

    public void addPlayer(ClientConnection clientConnection) {
        this.players.put(clientConnection, 0);
    }

    public boolean isFull() {
        return players.size() > 1;
    }

    public void notifyPlayerJoined(String playerName) throws IOException {
        nonCallingPlayer.out.writeObject(new PlayerJoinedResponse(playerName));
    }

    public void findCallingPlayer(long clientID) {
        for (ClientConnection player : players.keySet()) {
            if (player.clientID == clientID) {
                callingPlayer = player;
            } else {
                nonCallingPlayer = player;
            }
        }
    }

    public void addRoundToCounter() {
        currentRoundPerPlayer++;
    }

    public void updateGameScore(List<Integer> result) {
        int totalScoreBeforeRound = players.get(callingPlayer);
        int scoreThisRound = result.stream().reduce(0, Integer::sum);
        players.replace(callingPlayer, totalScoreBeforeRound, totalScoreBeforeRound+scoreThisRound);
    }

    public Map<ClientConnection, Integer> getPlayers() {
        return players;
    }

    public boolean finalRoundPlayed() {
        return (currentRoundPerPlayer/2) == maxRounds;
    }

    public void notifyRoundPlayed(List<Integer> result, Category selectedCategory, List<Question> answeredQuestions) throws IOException {
        callingPlayer.out.writeObject(new RoundPlayedResponse(RoundTurn.OTHER_PLAYER_TURN, result, selectedCategory, answeredQuestions));
        nonCallingPlayer.out.writeObject(new RoundPlayedResponse(RoundTurn.PLAYER_TURN, result, selectedCategory, answeredQuestions));
    }


    public void notifyGameOverResult() throws IOException {
        if (!finalRoundPlayed()) {
            throw new IllegalCallerException("Final round has not been played yet");
        }

        if (players.get(callingPlayer) > players.get(nonCallingPlayer)) {
            callingPlayer.out.writeObject(new VictoryResponse(VictoryType.WIN));
            nonCallingPlayer.out.writeObject(new DefeatResponse(DefeatType.LOSS));
        }
        else if (players.get(callingPlayer) < players.get(nonCallingPlayer)) {
            callingPlayer.out.writeObject(new DefeatResponse(DefeatType.LOSS));
            nonCallingPlayer.out.writeObject(new VictoryResponse(VictoryType.WIN));
        } else {
            callingPlayer.out.writeObject(new VictoryResponse(VictoryType.DRAW));
            nonCallingPlayer.out.writeObject(new VictoryResponse(VictoryType.DRAW));
        }
    }

    public void notifySurrender() throws IOException {
        callingPlayer.out.writeObject(new DefeatResponse(DefeatType.SURRENDER));
        nonCallingPlayer.out.writeObject(new VictoryResponse(VictoryType.SURRENDER));
    }



    public void setCategoriesReady() {
        this.categoriesReady = true;
    }

    public boolean categoriesReady() {
        return categoriesReady;
    }
}
