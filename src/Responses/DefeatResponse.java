package Responses;

import java.io.Serializable;

public class DefeatResponse extends Response implements Serializable {
    int playerScore;
    int otherPlayerScore;

    DefeatType defeatType;
    public DefeatResponse(DefeatType defeatType, int playerScore, int otherPlayerScore) {
        this.defeatType = defeatType;
        this.playerScore = playerScore;
        this.otherPlayerScore = otherPlayerScore;
    }

    public DefeatType getDefeatType() {
        return defeatType;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getOtherPlayerScore() {
        return otherPlayerScore;
    }
}
