package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class ScoreBoard extends AbstractBoard {

    JLabel headerLabel;
    JLabel playerLabel;
    JLabel otherPlayerLabel;

    JLabel[] playerScoreLabels;
    JLabel[] otherPlayerScoreLabels;

    JLabel playerTurnLabel;

    private JPanel midPanel;
    private JButton playButton;

    @Override
    protected void initComponents() {
        playerScoreLabels = new JLabel[6];
        otherPlayerScoreLabels = new JLabel[6];

        playerTurnLabel = new JLabel("         Your turn");
        playerTurnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        midPanel = new JPanel(new GridLayout(9, 3, 10, 10));

        midPanel.add(new JLabel());
        midPanel.add(playerTurnLabel);
        midPanel.add(new JLabel());

        playerLabel = new JLabel(" Player 1", SwingConstants.CENTER);
        headerLabel = new JLabel("Question Theme", SwingConstants.CENTER);
        otherPlayerLabel = new JLabel("Player 2 ", SwingConstants.CENTER);

        midPanel.add(playerLabel);
        midPanel.add(headerLabel);
        midPanel.add(otherPlayerLabel);

        for (int i = 1; i <= 6; i++) {
            playerScoreLabels[i-1] = new JLabel("0", SwingConstants.CENTER);
            otherPlayerScoreLabels[i-1] = new JLabel("0", SwingConstants.CENTER);
            midPanel.add(playerScoreLabels[i-1]);
            midPanel.add(new JLabel("Round " + i, SwingConstants.CENTER));
            midPanel.add(otherPlayerScoreLabels[i-1]);
        }
        playButton = new JButton("Play");
        midPanel.add(new JLabel());
        midPanel.add(playButton);
        midPanel.add(new JLabel());



    }

    @Override
    protected void buildLayout() {
        board.setLayout(new BorderLayout());
        board.add(midPanel, BorderLayout.CENTER);
    }

    public JLabel[] getPlayerScoreLabels() {
        return playerScoreLabels;
    }

    public JLabel[] getOtherPlayerScoreLabels() {
        return otherPlayerScoreLabels;
    }

    public JLabel getPlayerLabel() {
        return playerLabel;
    }

    public JLabel getOtherPlayerLabel() {
        return otherPlayerLabel;
    }

    public JLabel getPlayerTurnLabel() {
        return playerTurnLabel;
    }

    public JButton getPlayButton() {
        return playButton;
    }


}







/*package Client.GUI;

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