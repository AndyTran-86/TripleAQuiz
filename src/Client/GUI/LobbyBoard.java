package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class LobbyBoard {
    JPanel lobbyBoard;
    JLabel lobbyGameTitleLabel;
    JLabel lobbyProfileIcon;
    JButton lobbyStartButton;
    JTextField userNameInput;

    public LobbyBoard(CardLayout cardLayout, JPanel midpanel) {
        lobbyGameTitleLabel = new JLabel("TAQ - Triple-A Quiz", SwingConstants.CENTER);
        lobbyProfileIcon = new JLabel(new ImageIcon("images/lobby/user.png"));
        lobbyStartButton = new JButton("Start New Game");
        userNameInput = new JTextField(15);

        lobbyBoard = new JPanel(new GridLayout(6,3));

        buildLobbyLayout(cardLayout, midpanel);
    }

    private void buildLobbyLayout(CardLayout cardLayout, JPanel midPanel) {
        lobbyBoard.add(new JLabel());
        lobbyBoard.add(lobbyGameTitleLabel);
        lobbyBoard.add(new JLabel());

        lobbyBoard.add(new JLabel());
        lobbyBoard.add(lobbyProfileIcon);
        lobbyBoard.add(new JLabel());

        lobbyBoard.add(new JLabel());
        lobbyBoard.add(new JLabel("Username:", SwingConstants.CENTER));
        lobbyBoard.add(new JLabel());

        lobbyBoard.add(new JLabel());
        JPanel userNamePanel = new JPanel(new FlowLayout());
        userNamePanel.add(userNameInput);
        lobbyBoard.add(userNamePanel);
        lobbyBoard.add(new JLabel());

        lobbyBoard.add(new JLabel());
        lobbyBoard.add(lobbyStartButton);
        lobbyBoard.add(new JLabel());


        lobbyStartButton.addActionListener(e -> {
            String userName = userNameInput.getText().trim();
            if (!userName.isEmpty()) {
                System.out.println("User name: " + userName);

                //change board?
                cardLayout.show(midPanel, "CategoryBoard");
            }else {JOptionPane.showMessageDialog(lobbyBoard, "Please enter a username!", "Error", JOptionPane.ERROR_MESSAGE);

            }

        });
    }
    public JPanel getLobbyBoard(){
        return lobbyBoard;
    }

}

