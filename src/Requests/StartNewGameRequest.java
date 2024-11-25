package Requests;

import java.io.Serializable;

public class StartNewGameRequest extends Request implements Serializable {
    long clientID;
    String username;

    public StartNewGameRequest(long clientID, String username) {
        this.clientID = clientID;
        this.username = username;
    }

    public long getClientID() {
        return clientID;
    }

    public String getUsername() {
        return username;
    }
}
