package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class ScoreBoard {
    JFrame frame;
    JPanel scoreMainPanel;
    JPanel headPanel;
    JPanel midPanel;
    JPanel bottomPanel;

    JButton playButton;

    JLabel player1Label;
    JLabel player2Label;
    JLabel turnLabel;


    public ScoreBoard() {
        frame = new JFrame("TAQ Game on");
        scoreMainPanel = new JPanel(new BorderLayout());

        //Header
        headPanel = new JPanel(new FlowLayout());

        player1Label = new JLabel("Player 1         ", SwingConstants.LEFT);
        player1Label.setFont(new Font("Arial", Font.BOLD, 14));
        turnLabel = new JLabel("Your Turn           ", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        player2Label = new JLabel("Player 2", SwingConstants.RIGHT);
        player2Label.setFont(new Font("Arial", Font.BOLD, 14));

        //Center
        midPanel = new JPanel(new GridLayout(6, 3, 10, 10));
        for (int i = 1; i <= 6; i++) {
            midPanel.add(new JLabel("0", SwingConstants.CENTER));
            midPanel.add(new JLabel("Round " + i, SwingConstants.CENTER));
            midPanel.add(new JLabel("0", SwingConstants.CENTER));
        }

        //Footer
        bottomPanel = new JPanel(new FlowLayout());
        JButton playButton = new JButton("Play");
        bottomPanel.add(playButton);
    }

    public void ScoreBoardinit() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 1000);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.add(scoreMainPanel);

        headPanel.add(player1Label);
        headPanel.add(turnLabel);
        headPanel.add(player2Label);

        scoreMainPanel.add(headPanel, BorderLayout.NORTH);
        scoreMainPanel.add(midPanel, BorderLayout.CENTER);
        scoreMainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(scoreMainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScoreBoard scoreboard = new ScoreBoard();
            scoreboard.ScoreBoardinit();
        });
    }


    public JLabel getTurnLabel() {
        return turnLabel;
    }

    public JButton getPlayButton() {
        return playButton;
    }
}*/