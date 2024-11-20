package Requests;

import java.io.Serializable;

public class StartNewGameRequest extends Request implements Serializable {
    long clientID;

    public StartNewGameRequest(long clientID) {
        this.clientID = clientID;
    }

    public long getClientID() {
        return clientID;
    }
}
