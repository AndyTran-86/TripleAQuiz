package Requests;

import java.io.Serializable;

public class ListeningRequest extends Request implements Serializable {
    String username;

    public ListeningRequest(String username) {
        this.username = username;
    }
}
