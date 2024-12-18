package Server;

import Responses.*;
import Server.QuizDatabase.Category;
import Server.QuizDatabase.Question;

import java.io.IOException;
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
        maxRounds = gameInstanceManager.getNumRounds();
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

    public void notifyPlayerJoined() throws IOException {
        nonCallingPlayer.out.writeObject(new PlayerJoinedResponse(callingPlayer.username));
        callingPlayer.out.writeObject(new PlayerJoinedResponse(nonCallingPlayer.username));
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
//        System.out.println("Server: score: " + players);
    }

    public Map<ClientConnection, Integer> getPlayers() {
        return players;
    }

    public boolean finalRoundPlayed() {
        return (currentRoundPerPlayer/2) >= maxRounds;
    }

    public void printCurrentRound() {
        System.out.println((currentRoundPerPlayer));
    }

    public void notifyRoundPlayed(List<Integer> result, Category selectedCategory, List<Question> answeredQuestions) throws IOException {
        System.out.println(result);
        System.out.println(selectedCategory);
        System.out.println(answeredQuestions);
        nonCallingPlayer.out.writeObject(new RoundPlayedResponse(RoundTurn.PLAYER_TURN, result, selectedCategory, answeredQuestions));
        callingPlayer.out.writeObject(new RoundPlayedResponse(RoundTurn.OTHER_PLAYER_TURN, result, selectedCategory, answeredQuestions));
    }


    public void notifyGameOverResult() throws IOException {
        if (!finalRoundPlayed()) {
            throw new IllegalCallerException("Final round has not been played yet");
        }

        if (players.get(callingPlayer) > players.get(nonCallingPlayer)) {
            callingPlayer.out.writeObject(new VictoryResponse(VictoryType.WIN, players.get(callingPlayer), players.get(nonCallingPlayer)));
            nonCallingPlayer.out.writeObject(new DefeatResponse(DefeatType.LOSS, players.get(nonCallingPlayer), players.get(callingPlayer)));
        }
        else if (players.get(callingPlayer) < players.get(nonCallingPlayer)) {
            callingPlayer.out.writeObject(new DefeatResponse(DefeatType.LOSS, players.get(callingPlayer), players.get(nonCallingPlayer)));
            nonCallingPlayer.out.writeObject(new VictoryResponse(VictoryType.WIN, players.get(nonCallingPlayer), players.get(callingPlayer)));
        } else {
            callingPlayer.out.writeObject(new VictoryResponse(VictoryType.DRAW, players.get(callingPlayer), players.get(nonCallingPlayer)));
            nonCallingPlayer.out.writeObject(new VictoryResponse(VictoryType.DRAW, players.get(nonCallingPlayer), players.get(callingPlayer)));
        }
    }

    public void notifyRespondedAnsweredQuestions(List<Integer> result) throws IOException {
        nonCallingPlayer.out.writeObject(new RespondingAnswersResponse(result));
    }

    public void notifySurrender() throws IOException {
        callingPlayer.out.writeObject(new DefeatResponse(DefeatType.SURRENDER, players.get(callingPlayer), players.get(nonCallingPlayer)));
        nonCallingPlayer.out.writeObject(new VictoryResponse(VictoryType.SURRENDER, players.get(nonCallingPlayer), players.get(callingPlayer)));
    }



    public void setCategoriesReady() {
        this.categoriesReady = true;
    }

    public boolean categoriesReady() {
        return categoriesReady;
    }
}
