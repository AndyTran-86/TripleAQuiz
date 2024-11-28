package Responses;

import java.io.Serializable;

public class VictoryResponse extends Response implements Serializable {
    int playerScore;
    int otherPlayerScore;
    VictoryType victoryType;

    public VictoryResponse(VictoryType victoryType, int playerScore, int otherPlayerScore) {
        this.victoryType = victoryType;
        this.playerScore = playerScore;
        this.otherPlayerScore = otherPlayerScore;
    }

    public VictoryType getVictoryType() {
        return victoryType;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getOtherPlayerScore() {
        return otherPlayerScore;
    }
}
