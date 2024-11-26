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
    JLabel headerLabel;
    JLabel playerLabel;
    JLabel otherPlayerLabel;

    JLabel headerSpacer;

    //Footer
    JPanel footerPanel;
    JLabel homeIconLabel;
    JLabel friendIconLabel;

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
        headerLabel = new JLabel("Question Theme", SwingConstants.CENTER);
        playerLabel = new JLabel(" Player 1", SwingConstants.CENTER);
        otherPlayerLabel = new JLabel("Player 2 ", SwingConstants.CENTER);

        headerSpacer = new JLabel();
        headerSpacer.setPreferredSize(new Dimension(0, 50));



        //Footer
        footerPanel = new JPanel(new GridLayout(3,2,10,10));
        homeIconLabel = new JLabel(new ImageIcon("icons/lobby/home.png"));
        friendIconLabel = new JLabel(new ImageIcon("icons/lobby/friends.png"));


        spacer = new JLabel(" ", SwingConstants.CENTER) {{
                setFont(new Font("Arial", Font.PLAIN, 40));
            }};
        spacer1 = new JLabel(" ", SwingConstants.CENTER) {{
            setFont(new Font("Arial", Font.PLAIN, 40));
        }};

        //midSection
        //all code for STATE should be here. -CardLayout? -Revalidate? -Repaint?
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
        headerPanel.add(playerLabel, BorderLayout.WEST);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(otherPlayerLabel, BorderLayout.EAST);
        headerPanel.add(spacer, BorderLayout.NORTH);
        headerPanel.add(spacer1, BorderLayout.SOUTH);




        //Footer
        footerPanel.add(new JLabel());
        footerPanel.add(new JLabel());
        footerPanel.add(new JLabel());
        footerPanel.add(new JLabel());
        mainFramePanel.add(footerPanel, BorderLayout.SOUTH);
        footerPanel.add(homeIconLabel);
        footerPanel.add(new JLabel());
        footerPanel.add(new JLabel());
        footerPanel.add(friendIconLabel);
        footerPanel.add(new JLabel("Home", SwingConstants.CENTER));
        footerPanel.add(new JLabel());
        footerPanel.add(new JLabel());
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

//        cardLayout.show(midPanel,"Lobby");

        //cardLayout.show(midPanel, "WaitingBoard");

        //cardLayout.show(midPanel, "CategoryBoard");

        //cardLayout.show(midPanel, "QuizGameBoard");



        frame.setVisible(true);

        //Hide header
        hideHeaderContent();

    }

    private void addBoard(JPanel board, String name){
        midPanel.add(board, name);
    }



    private void hideHeaderContent() {
        headerLabel.setVisible(false);
        playerLabel.setVisible(false);
        otherPlayerLabel.setVisible(false);
        headerPanel.revalidate();
        headerPanel.repaint();
    }
    private void showHeaderContent() {
        headerLabel.setVisible(true);
        playerLabel.setVisible(true);
        otherPlayerLabel.setVisible(true);
        headerPanel.revalidate();
        headerPanel.repaint();
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
        quizGameBoard.getQuestionTextArea().setText(selectedQuestion.category() + "\n" +selectedQuestion.question());
        quizGameBoard.getAnswerButtons().getFirst().setText(selectedQuestion.correct_answer());
        for (int i = 1; i < 4; i++)
            quizGameBoard.getAnswerButtons().get(i).setText(selectedQuestion.incorrect_answers().get(i-1));
        Collections.shuffle(quizGameBoard.getAnswerButtons());
    }
}
