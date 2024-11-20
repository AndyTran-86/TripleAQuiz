package Responses;

import java.io.Serializable;

public class VictoryResponse extends Response implements Serializable {
    VictoryType victoryType;

    public VictoryResponse(VictoryType victoryType) {
        this.victoryType = victoryType;
    }

    public VictoryType getVictoryType() {
        return victoryType;
    }
}
