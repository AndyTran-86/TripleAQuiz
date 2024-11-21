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


    public MainFrameGUI() {
        frame = new JFrame("TAQ - Triple-A Quiz");

        mainFramePanel = new JPanel(new BorderLayout());

        //Header
        headerPanel = new JPanel(new BorderLayout());
        headerLabel = new JLabel("Question Theme", SwingConstants.CENTER);
        player1Label = new JLabel("Player 1", SwingConstants.CENTER);
        player2Label = new JLabel("Player 2", SwingConstants.CENTER);


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
        midPanel = new JPanel(new BorderLayout());
        cardLayout = new CardLayout();

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



        //midSection
        //all code for different boards/STATE should be here. -CardLayout? -Revalidate? -Repaint?
        mainFramePanel.add(midPanel, BorderLayout.CENTER);
        midPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));

        frame.add(mainFramePanel);
        frame.setVisible(true);



    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            MainFrameGUI game = new MainFrameGUI();
            game.init();
        });
    }


}
