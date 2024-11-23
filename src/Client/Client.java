package Client;

import Client.StateMachine.*;
import Requests.ListeningRequest;
import Requests.StartNewGameRequest;
import Responses.*;
import Server.QuizDatabase.Category;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Client implements Runnable {
    ClientQuestionData questionData;
    private int questionsPlayed;

    ObjectOutputStream out;
    ObjectInputStream in;
    ClientGUI gui;
    int port;
    InetAddress ip;
    String username;
    long clientID;

    ClientState state;
    ClientState lobbyState;
    ClientState newGameState;
    ClientState playerTurnState;
    ClientState otherPlayerTurnState;
    ClientState victoryState;
    ClientState defeatState;



    public Client(int port, String username) {
        this.questionData = new ClientQuestionData();
        this.port = port;
        this.username = username;
        this.questionsPlayed = 0;

        ip = InetAddress.getLoopbackAddress();
        gui = new ClientGUI();

        lobbyState = new ClientLobbyState(this, gui);
        playerTurnState = new ClientPlayerTurnState(this, gui);
        newGameState = new ClientNewGameState(this, gui);
        otherPlayerTurnState = new ClientOtherPlayerTurnState(this, gui);
        victoryState = new ClientVictoryState(this, gui);
        defeatState = new ClientDefeatState(this, gui);


    }

    private void connectToServer() {
        new Thread(() -> {
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

                        case NewGameResponse newGameResponse -> {
                            state = newGameState;
                            state.handleResponse(newGameResponse);

                            switch (newGameResponse.getTurnToPlay()) {
                                case PLAYER_TURN -> state = playerTurnState;
                                case OTHER_PLAYER_TURN -> state = otherPlayerTurnState;
                            }
                            state.updateGUI();
                        }

                        case PlayerJoinedResponse playerJoinedResponse -> {
                            state.handlePlayerJoined(playerJoinedResponse);
                            // TODO find a way to update gui with username only - make playerturn state update gui by reading instance valuables, if opponent username != null. update it.
                            state.updateGUI();
                        }

                        case RoundPlayedResponse roundPlayedResponse -> {
                            switch (roundPlayedResponse.getTurnToPlay()) {
                                case PLAYER_TURN -> state = playerTurnState;
                                case OTHER_PLAYER_TURN -> state = otherPlayerTurnState;
                            }
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
        }).start();

    }

    @Override
    public void run() {
         gui.init();
         connectToServer();
         addEventListeners();
    }

    public void addEventListeners() {
        gui.getLobbyStartNewGameButton().addActionListener(event -> {
            System.out.println("pressed start new game button");
            try (Socket socket = new Socket(ip, port);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

                out.writeObject(new StartNewGameRequest(clientID));
            } catch (IOException exception) {
                exception.printStackTrace();
            }



        });
    }

    public int getQuestionsPlayed() {
        return questionsPlayed;
    }

    public void resetQuestionsPlayed() {
        questionsPlayed = 0;
    }

    public boolean checkAnswer(String answer) {
        return questionData.checkAnswer(answer, ++questionsPlayed);
    }



    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public void setAllCategories(List<Category> questionsToClient) {
        questionData.setAllCategories(questionsToClient);
    }
}
