package Responses;

import java.io.Serializable;
import java.util.List;

public class ListeningResponse extends Response implements Serializable {
    long clientID;
    List<String> questions;

    public ListeningResponse(long clientID, List<String> questions) {
        this.clientID = clientID;
        this.questions = questions;
    }

    public long getClientID() {
        return clientID;
    }

    public List<String> getQuestions() {
        return questions;
    }
}
