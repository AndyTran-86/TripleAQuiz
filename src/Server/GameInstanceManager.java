package Server;

import Server.QuizDatabase.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInstanceManager {
    Map<Long, ClientConnection> clientsInLobbyByID;
    Map<String, List<String>> questionsByCategory;
    protected Map<Long, GameInstance> gameInstancesMapByID;
    GameInstance currentOpenGameInstance;
    boolean gameInstanceOpenForNewPlayer;
    private List<Category> allCategories;
    int numRounds;

    public GameInstanceManager(List<Category> allCategories, int numRounds) {
        clientsInLobbyByID = new HashMap<>();
        questionsByCategory = new HashMap<>();
        gameInstancesMapByID = new HashMap<>();
        this.allCategories = allCategories;
        gameInstanceOpenForNewPlayer = false;
        this.numRounds = numRounds;
    }


    public GameInstance getCurrentOpenGameInstance() {
        return currentOpenGameInstance;
    }

    public void startNewGameInstance() {
        currentOpenGameInstance = new GameInstance(this);
        gameInstancesMapByID.put(currentOpenGameInstance.getGameInstanceID(), currentOpenGameInstance);
        gameInstanceOpenForNewPlayer = true;
    }

    public void putPlayerInOpenGameInstance(ClientConnection clientConnection) {
        currentOpenGameInstance.addPlayer(clientConnection);
        if (currentOpenGameInstance.isFull()) {
            gameInstanceOpenForNewPlayer = false;
        }
    }

    public void terminateGameInstance(long gameInstanceID) {
        GameInstance instanceToTerminate = gameInstancesMapByID.get(gameInstanceID);
        for (ClientConnection player : instanceToTerminate.getPlayers().keySet()) {
            putPlayerInLobby(player);
        }
        gameInstancesMapByID.remove(gameInstanceID);
    }

    public void putPlayerInLobby(ClientConnection clientConnectionToJoin) {
        clientsInLobbyByID.put(clientConnectionToJoin.clientID, clientConnectionToJoin);
    }

    public ClientConnection takePlayerFromLobby(long clientID) {
        ClientConnection clientConnection = clientsInLobbyByID.get(clientID);
        clientsInLobbyByID.remove(clientID);
        return clientConnection;
    }


    public GameInstance getGameInstanceByID(long gameInstanceID) {
        return gameInstancesMapByID.get(gameInstanceID);
    }

    public boolean gameInstanceOpenForNewPlayer() {
        return gameInstanceOpenForNewPlayer;
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public int getNumRounds() {
        return numRounds;
    }
}
