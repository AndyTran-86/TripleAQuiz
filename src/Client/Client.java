package Client;

import Client.StateMachine.*;
import Requests.ListeningRequest;
import Responses.*;
import Server.QuizQuestion;
import Server.Request;
import Server.Response;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable {

    ObjectOutputStream out;
    ObjectInputStream in;
    ClientGUI gui;
    int port;
    InetAddress ip;
    String username;

    ClientState state;
    ClientState lobbyState;
    ClientState playerTurnState;
    ClientState otherPlayerTurnState;
    ClientState victoryState;
    ClientState defeatState;



    public Client(int port, String username) {
        this.port = port;
        this.username = username;

        ip = InetAddress.getLoopbackAddress();
        gui = new ClientGUI();

        lobbyState = new ClientLobbyState(this, gui);
        playerTurnState = new ClientPlayerTurnState(this, gui);
        otherPlayerTurnState = new ClientOtherPlayerTurnState(this, gui);
        victoryState = new ClientVictoryState(this, gui);
        defeatState = new ClientDefeatState(this, gui);


    }

    private void connectToServer() {
        try(Socket socket = new Socket(ip, port)) {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


            out.writeObject(new ListeningRequest(username));

            Object unknownResponseFromServer;

            while ((unknownResponseFromServer = in.readObject()) != null) {
                switch (unknownResponseFromServer) {
                    case ListeningResponse listeningResponse -> {
                        state = lobbyState;
                        state.handleResponse(listeningResponse);
                        state.updateGUI();
                    }
                    // TODO: Add logic in here to determine if its this players or other players turn
                    case NewGameResponse newGameResponse -> {
                        state = playerTurnState;
                        state.handleResponse(newGameResponse);
                        state.updateGUI();
                    }
                    // TODO: Add logic in here to determine if its this players or other players turn
                    case RoundPlayedResponse roundPlayedResponse -> {
                        state = otherPlayerTurnState;
                        state.handleResponse(roundPlayedResponse);
                        state.updateGUI();
                    }

                    case VictoryResponse victoryResponse -> {
                        state = victoryState;
                        state.handleResponse(victoryResponse);
                        state.updateGUI();
                    }

                    case DefeatResponse defeatResponse -> {
                        state = defeatState;
                        state.handleResponse(defeatResponse);
                        state.updateGUI();
                    }
                    default -> JOptionPane.showMessageDialog(gui.frame, "Unknown response received");
                }


            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
         gui.init();
         connectToServer();
    }
}
