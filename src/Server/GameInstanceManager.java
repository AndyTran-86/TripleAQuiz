package Server;

import Client.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInstanceManager {
    Map<Long, ClientConnection> clientsInLobbyByID;
    Map<String, List<String>> questionsByCategory;
    Map<Long, GameInstance> gameInstancesByID;
    GameInstance currentOpenGameInstance;
    boolean gameInstanceOpenForNewPlayer;

    public GameInstanceManager() {
        clientsInLobbyByID = new HashMap<>();
        questionsByCategory = new HashMap<>();
        gameInstancesByID = new HashMap<>();
        gameInstanceOpenForNewPlayer = false;
    }



    public GameInstance getCurrentOpenGameInstance() {
        return currentOpenGameInstance;
    }

    public void startNewGameInstance() {
        currentOpenGameInstance = new GameInstance();
        //TODO Add gameinstance to map with long id
    }

    public void putPlayerInOpenGameInstance(ClientConnection clientConnectionToJoin) {
        //TODO remove player from lobby and add to gameinstance
    }

    public void terminateGameInstance(long gameInstanceID) {
        GameInstance instanceToTerminate = gameInstancesByID.get(gameInstanceID);
        for (ClientConnection players : clientsInLobbyByID.values()) {
            // TODO add to lobby with id as key then null the object and remove from the map
        }

    }

    private void putPlayerInLobby(ClientConnection clientConnectionToJoin) {
        //TODO remove player from lobby and add to gameinstance
    }


    public GameInstance getGameInstanceByID(long gameInstanceID) {
        return gameInstancesByID.get(gameInstanceID);
    }

    public boolean gameInstanceOpenForNewPlayer() {
        return gameInstanceOpenForNewPlayer;
    }
}
