package Responses;

import java.io.Serializable;
import java.util.Map;

public class RoundPlayedResponse extends Response implements Serializable {
    RoundTurn turnToPlay;
    Map<String,Integer> result;

    public RoundPlayedResponse(RoundTurn turnToPlay, Map<String,Integer> result) {
        this.turnToPlay = turnToPlay;
        this.result = result;
    }

    public RoundTurn getTurnToPlay() {
        return turnToPlay;
    }

    public Map<String, Integer> getResult() {
        return result;
    }
}
