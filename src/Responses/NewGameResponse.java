package Responses;

import java.io.Serializable;

public class NewGameResponse extends Response implements Serializable {
    long gameInstanceID;
    RoundTurn turnToPlay;

    public NewGameResponse(long gameInstanceID, RoundTurn turnToPlay) {
        this.gameInstanceID = gameInstanceID;
        this.turnToPlay = turnToPlay;
    }

    public long getGameInstanceID() {
        return gameInstanceID;
    }

    public RoundTurn isTurnToPlay() {
        return turnToPlay;
    }
}
