package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrameGUI {
    JFrame frame;
    JPanel mainFramePanel;

    //Header
    JPanel headerPanel;
    JLabel headerLabel;
    JLabel player1Label;
    JLabel player2Label;

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
        player1Label = new JLabel(" Player 1", SwingConstants.CENTER);
        player2Label = new JLabel("Player 2 ", SwingConstants.CENTER);

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
        headerPanel.add(player1Label, BorderLayout.WEST);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(player2Label, BorderLayout.EAST);
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

        //WaitingBoard
        //TODO: remove
        WaitingBoard waitingBoard = new WaitingBoard();
        addBoard(waitingBoard.getBoard(),"WaitingBoard");

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
        player1Label.setVisible(false);
        player2Label.setVisible(false);
        headerPanel.revalidate();
        headerPanel.repaint();
    }
    private void showHeaderContent() {
        headerLabel.setVisible(true);
        player1Label.setVisible(true);
        player2Label.setVisible(true);
        headerPanel.revalidate();
        headerPanel.repaint();
    }


    public void setLobbyView() {
        cardLayout.show(midPanel,"Lobby");
    }

    public void setScoreBoardView() {
        cardLayout.show(midPanel, "ScoreBoard");
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() ->{
//            MainFrameGUI game = new MainFrameGUI();
//            game.init();
//        });
//    }

    public JButton getLobbyStartGameButton() {
        return lobbyBoard.getLobbyStartButton();
    }

    public String getInputUsername() {
        return lobbyBoard.getInputUserName();
    }


    public void disablePlayButton() {
        System.out.println("disable play button");
    }

    public void enablePlayButton() {
        System.out.println("disable play button");
    }

    public void setPlayerTurn() {
        System.out.println("player turn");
    }

    public void setOtherPlayerTurn() {
        System.out.println("other player turn");
    }


}
