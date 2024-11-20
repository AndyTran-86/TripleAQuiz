package Requests;

import java.io.Serializable;

public class SurrenderRequest extends Request implements Serializable {
    long clientID;
    long gameInstanceID;

    public SurrenderRequest(long clientID, long gameInstanceID) {
        this.clientID = clientID;
        this.gameInstanceID = gameInstanceID;
    }

    public long getClientID() {
        return clientID;
    }

    public long getGameInstanceID() {
        return gameInstanceID;
    }
}
