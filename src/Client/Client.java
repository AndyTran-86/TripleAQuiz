package Client;

import Client.GUI.MainFrameGUI;
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
    private ClientQuestionData questionData;


    ObjectOutputStream out;
    ObjectInputStream in;
    MainFrameGUI guiMainFrame;
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



        ip = InetAddress.getLoopbackAddress();
        guiMainFrame = new MainFrameGUI();

        lobbyState = new ClientLobbyState(this, guiMainFrame);
        playerTurnState = new ClientPlayerTurnState(this, guiMainFrame);
        newGameState = new ClientNewGameState(this, guiMainFrame);
        otherPlayerTurnState = new ClientOtherPlayerTurnState(this, guiMainFrame);
        victoryState = new ClientVictoryState(this, guiMainFrame);
        defeatState = new ClientDefeatState(this, guiMainFrame);


    }

    @Override
    public void run() {
        guiMainFrame.init();
        connectToServer();
        addEventListeners();
    }

    private void connectToServer() {
        new Thread(() -> {
            try(Socket socket = new Socket(ip, port)) {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());


                out.writeObject(new ListeningRequest());

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
                        default -> JOptionPane.showMessageDialog(null, "Unknown response received");
                    }


                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public ClientQuestionData getQuestionData() {
        return questionData;
    }

    public void addEventListeners() {

        guiMainFrame.getScoreBoardPlayButton().addActionListener((e) -> {
            guiMainFrame.setCategoryBoardNames(questionData.getThreeRandomCategories());
            guiMainFrame.showCategoryBoardView();
        });

        for (JButton categoryButton : guiMainFrame.getCategoryButtons()) {
            categoryButton.addActionListener((e) -> {
                guiMainFrame.showQuizGameView();
            });
        }

        guiMainFrame.getLobbyStartGameButton().addActionListener(e -> {
            //TODO replace with mainframe gui
            String userName = guiMainFrame.getInputUsername();
            if (!userName.isEmpty()) {
                username = userName;
                guiMainFrame.setPlayerUserName(username);

                try (Socket socket = new Socket(ip, port);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                out.writeObject(new StartNewGameRequest(clientID, username));

            } catch (IOException exception) {
                exception.printStackTrace();
            }

            }else {JOptionPane.showMessageDialog(guiMainFrame.getFrame(), "Please enter a username!", "Error", JOptionPane.ERROR_MESSAGE);

            }

        });
    }


    public boolean checkAnswer(String answer) {
        return questionData.checkAnswer(answer);
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
