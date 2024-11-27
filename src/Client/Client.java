package Client;

import Client.GUI.MainFrameGUI;
import Client.StateMachine.*;
import Requests.*;
import Responses.*;
import Server.QuizDatabase.Category;

import javax.swing.*;
import java.awt.*;
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
    long gameInstanceID;
    int currentRound;
    boolean isRespondingTurn;

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

        currentRound = 0;



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
                    System.out.println(unknownResponseFromServer.getClass().getName());
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
                        }

                        case RoundPlayedResponse roundPlayedResponse -> {
                            switch (roundPlayedResponse.getTurnToPlay()) {
                                case PLAYER_TURN -> state = playerTurnState;
                                case OTHER_PLAYER_TURN -> state = otherPlayerTurnState;
                            }
                            state.handleResponse(roundPlayedResponse);
                            state.updateGUI();
                        }

                        case RespondingAnswersResponse respondingAnswersResponse -> {
                            System.out.println("got to switch for answers response");
                            state.handleResponse(respondingAnswersResponse);
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
            if (isRespondingTurn) {
                guiMainFrame.setGameBoard(questionData.getSelectedCategoryQuestion());
                guiMainFrame.showQuizGameView();
            } else {
                guiMainFrame.setCategoryBoardNames(questionData.getThreeRandomCategories());
                guiMainFrame.showCategoryBoardView();
            }

        });

        for (JButton categoryButton : guiMainFrame.getCategoryButtons()) {
            categoryButton.addActionListener((e) -> {
                questionData.selectCategory(categoryButton.getText());
                guiMainFrame.setGameBoard(questionData.getSelectedCategoryQuestion());
                guiMainFrame.showQuizGameView();
            });
        }

        for (JButton answerButton : guiMainFrame.getAnswerButtons()) {
            answerButton.addActionListener((e) -> {
                if (questionData.checkAnswer(answerButton.getText())) {
                    int currentScore = questionData.getResultsPerRound().stream().reduce(0, Integer::sum);
                    guiMainFrame.getPlayerScoreLabels()[getCurrentRound()-1].setText(String.valueOf(currentScore));
                    answerButton.setBackground(Color.GREEN);
                    System.out.println("Correct answer");
                } else {
                    answerButton.setBackground(Color.RED);
                    System.out.println("Wrong answer");
                }
                try {
                    Thread.sleep(3000);
                    if (questionData.getQuestionsPlayed() >= 3 && isRespondingTurn) {
                        setRespondingTurn(false);
                        sendRespondingAnswers();
                        guiMainFrame.showScoreBoardView();
                    }
                    else if (questionData.getQuestionsPlayed() >= 3) {
                        sendRoundPlayed();
                    }else {
                        guiMainFrame.setGameBoard(questionData.getSelectedCategoryQuestion());
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

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

        guiMainFrame.getSurrenderButton().addActionListener((e) -> {
            try (Socket socket = new Socket(ip, port);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                out.writeObject(new SurrenderRequest(clientID, gameInstanceID));

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }




    private void sendRoundPlayed() {
        try (Socket socket = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            out.writeObject(new RoundPlayedRequest(clientID, gameInstanceID, questionData.getResultsPerRound(), questionData.getSelectedCategory(), questionData.getAnsweredQuestions()));

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void sendRespondingAnswers() {
        try (Socket socket = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(new RespondingAnswersRequest(clientID, gameInstanceID, questionData.getResultsPerRound()));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
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

    public void setGameInstanceID(long gameInstanceID) {
        this.gameInstanceID = gameInstanceID;
    }

    public void setRespondingTurn(boolean respondingTurn) {
        isRespondingTurn = respondingTurn;
    }

    public void setAllCategories(List<Category> questionsToClient) {
        questionData.setAllCategories(questionsToClient);
    }

    public void updateRoundCounter() {
        currentRound++;
    }

    public int getCurrentRound() {
        return currentRound/2;
    }

}
