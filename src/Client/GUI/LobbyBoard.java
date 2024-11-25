package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class LobbyBoard extends AbstractBoard {

    private JLabel lobbyGameTitleLabel;
    private JLabel lobbyProfileIcon;
    private JButton lobbyStartButton;
    private JTextField userNameInput;

    private CardLayout cardLayout;
    private JPanel midPanel;

    public LobbyBoard(CardLayout cardLayout, JPanel midPanel) {
        this.cardLayout = cardLayout;
        this.midPanel = midPanel;
    }

    @Override
    protected void initComponents(){
        lobbyGameTitleLabel = new JLabel("TAQ - Triple-A Quiz", SwingConstants.CENTER);
        lobbyProfileIcon = new JLabel(new ImageIcon("icons/lobby/user.png"));
        lobbyStartButton = new JButton("Start New Game");
        userNameInput = new JTextField(15);


    }

    @Override
    protected void buildLayout(){

        board.setLayout(new GridLayout(6,3));

        board.add(new JLabel());
        board.add(lobbyGameTitleLabel);
        board.add(new JLabel());

        board.add(new JLabel());
        board.add(lobbyProfileIcon);
        board.add(new JLabel());

        board.add(new JLabel());
        board.add(new JLabel("Username:", SwingConstants.CENTER));
        board.add(new JLabel());

        board.add(new JLabel());
        JPanel userNamePanel = new JPanel(new FlowLayout());
        userNamePanel.add(userNameInput);
        board.add(userNamePanel);
        board.add(new JLabel());

        board.add(new JLabel());
        board.add(lobbyStartButton);
        board.add(new JLabel());


//        lobbyStartButton.addActionListener(e -> {
//            //TODO replace with mainframe gui
//            String userName = this.getInputUserName();
//            if (!userName.isEmpty()) {
//                System.out.println("User name: " + userName);
//
//                //change board?
//                cardLayout.show(midPanel, "CategoryBoard");
//            }else {JOptionPane.showMessageDialog(board, "Please enter a username!", "Error", JOptionPane.ERROR_MESSAGE);
//
//            }
//
//        });
    }

    public JButton getLobbyStartButton() {
        return lobbyStartButton;
    }

    public String getInputUserName() {
        return userNameInput.getText().trim();
    }
}



