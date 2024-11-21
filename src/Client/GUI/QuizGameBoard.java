package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class QuizGameBoard {
    JFrame frame;
    JPanel gameMainPanel;
    JPanel middlePanel;
    JPanel questionPanel;
    JPanel answerPanel;
    JPanel gameBottomPanel;
    JPanel headerPanel;


    JTextArea questionTextArea;
    JLabel timerLabel;

    JButton[] answerButtons;

    JLabel headerLabel;
    JLabel player1Label;
    JLabel player2Label;
    JLabel spaceLabel;
    JLabel spaceLabel1;

    JLabel homeIconLabel;
    JLabel friendIconLabel;


    public QuizGameBoard() {
        frame = new JFrame("Triple-A Quiz Game");

        gameMainPanel = new JPanel();
        middlePanel = new JPanel(new BorderLayout());
        questionPanel = new JPanel();
        answerPanel = new JPanel();
        gameBottomPanel = new JPanel();
        headerPanel = new JPanel(new BorderLayout());
        headerLabel = new JLabel("Question Theme", SwingConstants.CENTER);
        player1Label = new JLabel("Player 1", SwingConstants.CENTER);
        player2Label = new JLabel("Player 2", SwingConstants.CENTER);

        spaceLabel = new JLabel(" ");
        spaceLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        spaceLabel1 = new JLabel(" ");
        spaceLabel1.setFont(new Font("Arial", Font.PLAIN, 40));

        questionTextArea = new JTextArea("Question will be here, I hope its okay to put the question here. " +
                "I write some just to fill it and see how it looks");
        questionTextArea.setFont(new Font("Arial",Font.PLAIN, 24));
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setEditable(false);
        questionTextArea.setOpaque(false);


        timerLabel = new JLabel("Time left: 30s", SwingConstants.CENTER);



        answerButtons = new JButton[4];
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new JButton("Answer " + (i + 1));
            answerButtons[i].setFont(new Font("Arial", Font.BOLD, 16));
        }

        homeIconLabel = new JLabel(new ImageIcon("icons/lobby/home.png"));
        friendIconLabel = new JLabel(new ImageIcon("icons/lobby/friends.png"));

    }


    public void QuizBoardinit() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 1000);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.add(gameMainPanel);

        gameMainPanel.setLayout(new BorderLayout());


        //Övre

        headerPanel.add(spaceLabel, BorderLayout.NORTH);
        headerPanel.add(player1Label, BorderLayout.WEST);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(player2Label, BorderLayout.EAST);
        headerPanel.add(spaceLabel1, BorderLayout.SOUTH);

        gameMainPanel.add(headerPanel, BorderLayout.NORTH);

        questionPanel.setLayout(new BorderLayout());
        questionPanel.add(questionTextArea, BorderLayout.CENTER);
        questionPanel.add(timerLabel, BorderLayout.SOUTH);
        //gameMainPanel.add(questionPanel, BorderLayout.NORTH);


        //Mitt
        answerPanel.setLayout(new GridLayout(2,2,10,10));
        for (JButton button : answerButtons) {
            answerPanel.add(button);
        }
        middlePanel.add(questionPanel, BorderLayout.NORTH);
        middlePanel.add(answerPanel, BorderLayout.CENTER);
        gameMainPanel.add(middlePanel, BorderLayout.CENTER);


        //Nedre
        gameBottomPanel.setLayout(new GridLayout(2,4));
        gameBottomPanel.add(homeIconLabel);
        gameBottomPanel.add(new JLabel());
        gameBottomPanel.add(new JLabel());
        gameBottomPanel.add(friendIconLabel);
        gameBottomPanel.add(new JLabel("Home", SwingConstants.CENTER));
        gameBottomPanel.add(new JLabel());
        gameBottomPanel.add(new JLabel());
        gameBottomPanel.add(new JLabel("Friends", SwingConstants.CENTER));
        gameMainPanel.add(gameBottomPanel, BorderLayout.SOUTH);


        frame.add(gameMainPanel);
        frame.setVisible(true);
    }


    ///////////////////////////////////
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            QuizGameBoard game = new QuizGameBoard();
            game.QuizBoardinit();
        });
    }
    ///////////////////////////////////


}
