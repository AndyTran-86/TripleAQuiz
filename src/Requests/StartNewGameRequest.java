package Requests;

import java.io.Serializable;

public class StartNewGameRequest extends Request implements Serializable {
    long clientID;
    String username;

    public StartNewGameRequest(long clientID) {
        this.clientID = clientID;
    }

    public long getClientID() {
        return clientID;
    }

    public String getUsername() {
        return username;
    }
}
