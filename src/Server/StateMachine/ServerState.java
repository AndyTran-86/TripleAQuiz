package Server.StateMachine;

import Requests.Request;

import java.io.IOException;

public interface ServerState {
    void handleRequest(Request request) throws IOException, ClassNotFoundException;
}
