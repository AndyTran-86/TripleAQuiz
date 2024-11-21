package Server;

import java.util.ArrayList;
import java.util.List;

public class GameInstance {
    private static long gameInstanceIDIncrementor = 1;
    private final long gameInstanceID;
    private final List<ClientConnection> players;

    public GameInstance() {
        this.gameInstanceID = gameInstanceIDIncrementor++;
        this.players = new ArrayList<>();
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

}
