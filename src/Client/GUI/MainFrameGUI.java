package Client.GUI;

import Server.QuizDatabase.Category;
import Server.QuizDatabase.Question;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class MainFrameGUI {
    JFrame frame;
    JPanel mainFramePanel;

    //Header
    JPanel headerPanel;
    JLabel headerSpacer;

    //Footer
    JPanel footerPanel;
    JLabel homeIconLabel;
    JLabel friendIconLabel;
    JButton surrenderButton;
    JButton nextQuestionButton;

    //Spacers
    JLabel spacer;
    JLabel spacer1;

    //MiddleSection
    JPanel midPanel;
    CardLayout cardLayout;


    //Cards
    LobbyBoard lobbyBoard;
    CategoryBoard categoryBoard;
    QuizGameBoard quizGameBoard;
    ScoreBoard scoreBoard;



    public MainFrameGUI() {
        frame = new JFrame("TAQ - Triple-A Quiz");

        mainFramePanel = new JPanel(new BorderLayout());

        //Header
        headerPanel = new JPanel(new BorderLayout());
        headerSpacer = new JLabel();
        headerSpacer.setPreferredSize(new Dimension(0, 50));



        //Footer
        footerPanel = new JPanel(new GridLayout(3,2,10,10));
        homeIconLabel = new JLabel(new ImageIcon("icons/lobby/home.png"));
        friendIconLabel = new JLabel(new ImageIcon("icons/lobby/friends.png"));
        surrenderButton = new JButton(new ImageIcon("icons/lobby/surrender.png"));
        nextQuestionButton = new JButton("Next Question");

        spacer = new JLabel(" ", SwingConstants.CENTER) {{
                setFont(new Font("Arial", Font.PLAIN, 40));
            }};
        spacer1 = new JLabel(" ", SwingConstants.CENTER) {{
            setFont(new Font("Arial", Font.PLAIN, 40));
        }};

        //midSection
        midPanel = new JPanel();
        cardLayout = new CardLayout();
        midPanel.setLayout(cardLayout);
        mainFramePanel.add(midPanel,BorderLayout.CENTER);


    }

    public void init() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 1000);
        frame.setLocationRelativeTo(null);
        frame.add(mainFramePanel);


        //Header
        mainFramePanel.add(headerPanel, BorderLayout.NORTH);
        headerPanel.add(spacer, BorderLayout.NORTH);
        headerPanel.add(spacer1, BorderLayout.SOUTH);




        //Footer
        mainFramePanel.add(footerPanel, BorderLayout.SOUTH);
        footerPanel.add(surrenderButton);
        nextQuestionButton.setEnabled(false);
        footerPanel.add(nextQuestionButton);
        footerPanel.add(homeIconLabel);
        footerPanel.add(friendIconLabel);
        footerPanel.add(new JLabel("Home", SwingConstants.CENTER));
        footerPanel.add(new JLabel("Friends", SwingConstants.CENTER));



        //LobbyBoard
        lobbyBoard = new LobbyBoard(cardLayout, midPanel);
        addBoard(lobbyBoard.getBoard(), "Lobby");


        //CategoryBoard
        categoryBoard = new CategoryBoard();
        addBoard(categoryBoard.getBoard(), "CategoryBoard");

        //
        quizGameBoard = new QuizGameBoard();
        addBoard(quizGameBoard.getBoard(),"QuizGameBoard");


        scoreBoard = new ScoreBoard();
        addBoard(scoreBoard.getBoard(),"ScoreBoard");


        frame.setVisible(true);


    }

    private void addBoard(JPanel board, String name){
        midPanel.add(board, name);
    }

    public void resetScoreBoard() {
        scoreBoard.resetScoreBoard();
    }

    public void showLobbyView() {
        cardLayout.show(midPanel,"Lobby");
    }

    public void showScoreBoardView() {
        cardLayout.show(midPanel, "ScoreBoard");
    }

    public void showCategoryBoardView() {
        cardLayout.show(midPanel, "CategoryBoard");
    }

    public void setCategoryBoardNames(List<Category> categories) {
        int counter = 0;
        for (JButton b : categoryBoard.getCategoryButtons()) {
            b.setText(categories.get(counter++).name());
        }
    }

    public void showQuizGameView() {
        cardLayout.show(midPanel, "QuizGameBoard");
    }

    public JLabel[] getPlayerScoreLabels() {
        return scoreBoard.getPlayerScoreLabels();
    }

    public JLabel[] getOtherPlayerScoreLabels() {
        return scoreBoard.getOtherPlayerScoreLabels();
    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getScoreBoardPlayButton() {
        return scoreBoard.getPlayButton();
    }

    public JButton[] getCategoryButtons() {
        return categoryBoard.getCategoryButtons();
    }

    public JButton getLobbyStartGameButton() {
        return lobbyBoard.getLobbyStartButton();
    }

    public String getInputUsername() {
        return lobbyBoard.getInputUserName();
    }
    
    public JButton getSurrenderButton() {
        return surrenderButton;
    }

    public JButton getNextQuestionButton() {
        return nextQuestionButton;
    }

    public void setPlayerUserName(String username) {
        scoreBoard.getPlayerLabel().setText(username);
    }

    public void setOtherPlayerUserName(String username) {
        scoreBoard.getOtherPlayerLabel().setText(username);
    }

    public void setPlayerTurnLabelToPlayer() {
        scoreBoard.getPlayerTurnLabel().setText("         Your turn");
    }

    public void setPlayerTurnLabelToOtherPlayer() {
        scoreBoard.getPlayerTurnLabel().setText("         Their turn");
    }

    public void disablePlayButton() {
        scoreBoard.getPlayButton().setEnabled(false);
    }

    public void enablePlayButton() {
        scoreBoard.getPlayButton().setEnabled(true);
    }

    public void setGameBoard(Question selectedQuestion) {
        nextQuestionButton.setText("Next Question");
        for (JButton b : quizGameBoard.getAnswerButtons()) {
            b.setBackground(Color.WHITE);
            b.setEnabled(true);
        }
        quizGameBoard.getQuestionTextArea().setText(selectedQuestion.category() + "\n" +selectedQuestion.question());
        quizGameBoard.getAnswerButtons().getFirst().setText(selectedQuestion.correct_answer());
        for (int i = 1; i < 4; i++)
            quizGameBoard.getAnswerButtons().get(i).setText(selectedQuestion.incorrect_answers().get(i-1));
        Collections.shuffle(quizGameBoard.getAnswerButtons());
    }

    public List<JButton> getAnswerButtons() {
        return quizGameBoard.getAnswerButtons();
    }

    public void disableAnswerButtons() {
        for (JButton b : quizGameBoard.getAnswerButtons()) {
            b.setEnabled(false);
        }
    }

    public JLabel getPlayerTurnLabel() {
        return scoreBoard.getPlayerTurnLabel();
    }

    public JLabel getFinalScorePlayer() {
        return scoreBoard.finalScorePlayer;
    }

    public JLabel getFinalScoreOtherPlayer() {
        return scoreBoard.finalScoreOtherPlayer;
    }
}
