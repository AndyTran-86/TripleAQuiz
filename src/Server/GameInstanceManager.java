package Server;

import Server.QuizDatabase.QuestionsByCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInstanceManager {
    Map<Long, ClientConnection> clientsInLobbyByID;
    Map<String, List<String>> questionsByCategory;
    protected Map<Long, GameInstance> gameInstancesMapByID;
    GameInstance currentOpenGameInstance;
    boolean gameInstanceOpenForNewPlayer;

    public GameInstanceManager() {
        clientsInLobbyByID = new HashMap<>();
        questionsByCategory = new HashMap<>();
        gameInstancesMapByID = new HashMap<>();
        gameInstanceOpenForNewPlayer = false;
    }



    public GameInstance getCurrentOpenGameInstance() {
        return currentOpenGameInstance;
    }


    public void startNewGameInstance() {
        currentOpenGameInstance = new GameInstance();
        currentOpenGameInstance.startApiRequest();
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
        for (ClientConnection players : clientsInLobbyByID.values()) {
            // TODO add to lobby with id as key then null the object and remove from the map
        }

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

    public boolean categoriesReady() {
        return currentOpenGameInstance.categoriesReady();
    }

    public List<QuestionsByCategory> getQuestions() {
        return currentOpenGameInstance.getQuestions();
    }
}
