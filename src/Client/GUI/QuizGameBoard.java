package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class QuizGameBoard {
    JFrame frame;
    JPanel gameMainPanel;
    JPanel questionPanel;
    JPanel answerPanel;
    JPanel gameBottomPanel;

    JTextArea questionTextArea;
    JLabel timerLabel;

    JButton[] answerButtons;

    JLabel homeIconLabel;
    JLabel friendIconLabel;


    public QuizGameBoard() {
        frame = new JFrame("Triple-A Quiz Game");

        gameMainPanel = new JPanel();
        questionPanel = new JPanel();
        answerPanel = new JPanel();
        gameBottomPanel = new JPanel();

        questionTextArea = new JTextArea();
        timerLabel = new JLabel();


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
        gameMainPanel.add(questionPanel, BorderLayout.NORTH);
        questionPanel.setLayout(new BorderLayout());
        questionPanel.add(questionTextArea, BorderLayout.CENTER);
        questionPanel.add(timerLabel, BorderLayout.SOUTH);

        //Mitt
        gameMainPanel.add(answerPanel, BorderLayout.CENTER);
        answerPanel.setLayout(new GridLayout(2,2,10,10));
        for (JButton button : answerButtons) {
            answerPanel.add(button);
        }

        //Nedre
        gameMainPanel.add(gameBottomPanel, BorderLayout.SOUTH);
        gameBottomPanel.setLayout(new GridLayout(2,4));
        gameBottomPanel.add(homeIconLabel);
        gameBottomPanel.add(new JLabel());
        gameBottomPanel.add(new JLabel());
        gameBottomPanel.add(friendIconLabel);
        gameBottomPanel.add(new JLabel("Home", SwingConstants.CENTER));
        gameBottomPanel.add(new JLabel());
        gameBottomPanel.add(new JLabel());
        gameBottomPanel.add(new JLabel("Friends", SwingConstants.CENTER));

        frame.setVisible(true);
    }


    ///////////////////////////////////
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            QuizGameBoard game = new QuizGameBoard();
            game.QuizBoardinit();
        });
    }*/
    ///////////////////////////////////


}
