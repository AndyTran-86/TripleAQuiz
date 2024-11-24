package Requests;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RoundPlayedRequest extends Request implements Serializable {
    long clientID;
    long gameInstanceID;
    Map<String,Integer> result;

    public RoundPlayedRequest(long clientID, long gameInstanceID, Map<String,Integer> result) {
        this.clientID = clientID;
        this.gameInstanceID = gameInstanceID;
        this.result = result;
    }

    public long getClientID() {
        return clientID;
    }

    public long getGameInstanceID() {
        return gameInstanceID;
    }

    public Map<String, Integer> getResult() {
        return result;
    }
}
