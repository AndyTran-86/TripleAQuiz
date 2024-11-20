package Client;

import javax.swing.*;
import java.awt.*;

public class ClientGUI {
    public JFrame frame;
    JPanel lobbyMainPanel;
    JPanel lobbyTopHeaderPanel;
    JPanel lobbyCenterPanel;
    JLabel lobbyGameTitleLabel;
    JLabel lobbyProfileIconLabel;
    JButton lobbyStartNewGameButton;
    JPanel lobbyBottomPanel;
    JLabel lobbyBottomHomeIcon;
    JLabel lobbyBottomFriendsIcon;

    public ClientGUI() {
        frame = new JFrame("Triple-A Server.Quiz!");
        lobbyMainPanel = new JPanel();
        lobbyTopHeaderPanel = new JPanel();
        lobbyCenterPanel = new JPanel();
        lobbyGameTitleLabel = new JLabel("                   Triple-A Server.Quiz");
        lobbyProfileIconLabel = new JLabel(new ImageIcon("icons/lobby/user.png"));
        lobbyStartNewGameButton = new JButton("Start New Game");
        lobbyBottomPanel = new JPanel();
        lobbyBottomHomeIcon = new JLabel(new ImageIcon("icons/lobby/home.png"));
        lobbyBottomFriendsIcon = new JLabel(new ImageIcon("icons/lobby/friends.png"));

    }
    public void init() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(600, 1000));
        frame.setVisible(true);
        frame.add(lobbyMainPanel);

        lobbyMainPanel.setLayout(new BorderLayout());
        lobbyMainPanel.add(lobbyTopHeaderPanel, BorderLayout.NORTH);

        lobbyTopHeaderPanel.setLayout(new GridLayout(1, 5));
        lobbyTopHeaderPanel.add(new JLabel("  20:06"));
        lobbyTopHeaderPanel.add(new JLabel());
        lobbyTopHeaderPanel.add(new JLabel());
        lobbyTopHeaderPanel.add(new JLabel());
        lobbyTopHeaderPanel.add(new JLabel("                           95%"));

        lobbyMainPanel.add(lobbyCenterPanel, BorderLayout.CENTER);

        lobbyCenterPanel.setLayout(new GridLayout(6, 3));

        lobbyCenterPanel.add(new JLabel());
        lobbyCenterPanel.add(lobbyGameTitleLabel);
        lobbyCenterPanel.add(new JLabel());

        lobbyCenterPanel.add(new JLabel());
        lobbyCenterPanel.add(lobbyProfileIconLabel);
        lobbyCenterPanel.add(new JLabel());

        lobbyCenterPanel.add(new JLabel());
        lobbyCenterPanel.add(new JLabel("                     Username: "));
        lobbyCenterPanel.add(new JLabel());

        lobbyCenterPanel.add(new JLabel());
        lobbyCenterPanel.add(new JLabel());
        lobbyCenterPanel.add(new JLabel());

        lobbyCenterPanel.add(new JLabel());
        lobbyCenterPanel.add(lobbyStartNewGameButton);
        lobbyCenterPanel.add(new JLabel());

        lobbyCenterPanel.add(new JLabel());
        lobbyCenterPanel.add(new JLabel());
        lobbyCenterPanel.add(new JLabel());

        lobbyMainPanel.add(lobbyBottomPanel, BorderLayout.SOUTH);
        lobbyBottomPanel.setLayout(new GridLayout(2, 4));
        lobbyBottomPanel.add(lobbyBottomHomeIcon);
        lobbyBottomPanel.add(new JLabel());
        lobbyBottomPanel.add(new JLabel());
        lobbyBottomPanel.add(lobbyBottomFriendsIcon);
        lobbyBottomPanel.add(new JLabel("                   Home"));
        lobbyBottomPanel.add(new JLabel());
        lobbyBottomPanel.add(new JLabel());
        lobbyBottomPanel.add(new JLabel("                  Friends"));
    }

    public JButton getLobbyStartNewGameButton() {
        return lobbyStartNewGameButton;
    }
}
