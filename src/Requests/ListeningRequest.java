package Requests;

import java.io.Serializable;

public class ListeningRequest extends Request implements Serializable {

    public ListeningRequest() {
    }

    public String getUsername() {
        return username;
    }
}
