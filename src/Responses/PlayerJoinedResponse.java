package Responses;

import java.io.Serializable;

public class PlayerJoinedResponse extends Response implements Serializable {
    String username;

    public PlayerJoinedResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
