package Client.GUI;

import Server.QuizDatabase.QuestionsByCategory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategorySelectionBoard {
    JFrame frame;

    private JPanel categoryMainPanel;
    JPanel headerPanel;
    JPanel middlePanel;
    JPanel bottomPanel;

    JLabel headerLabel;
    JLabel player1Label;
    JLabel player2Label;
    JLabel spaceLabel;
    JLabel spaceLabel1;

    JLabel categoryTitleLabel;

    JButton[] categoryButtons;

    JLabel homeIconLabel;
    JLabel friendIconLabel;

    public CategorySelectionBoard() {
        frame = new JFrame("Category Selection Board");

        categoryMainPanel = new JPanel();
        headerPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new GridLayout(4,1,10,10));
        bottomPanel = new JPanel();


        headerLabel = new JLabel("Question Theme", SwingConstants.CENTER);
        player1Label = new JLabel("Player 1", SwingConstants.CENTER);
        player2Label = new JLabel("Player 2", SwingConstants.CENTER);

        spaceLabel = new JLabel(" ");
        spaceLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        spaceLabel1 = new JLabel(" ");
        spaceLabel1.setFont(new Font("Arial", Font.PLAIN, 40));


        categoryTitleLabel = new JLabel("Select a category", SwingConstants.CENTER);
        categoryTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoryTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        categoryButtons = new JButton[3];
        for (int i = 0; i < categoryButtons.length; i++) {
            categoryButtons[i] = new JButton("Category " + (i + 1));
            categoryButtons[i].setFont(new Font("Arial", Font.BOLD, 16));

        }

        homeIconLabel = new JLabel(new ImageIcon("icons/lobby/home.png"));
        friendIconLabel = new JLabel(new ImageIcon("icons/lobby/friends.png"));

    }



    public void CategorySelectionBoardinit(){
        /*frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600,1000);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);*/

        categoryMainPanel = new JPanel(new BorderLayout());

        //Header
        headerPanel.add(spaceLabel, BorderLayout.NORTH);
        headerPanel.add(player1Label, BorderLayout.WEST);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(player2Label, BorderLayout.EAST);
        headerPanel.add(spaceLabel1, BorderLayout.SOUTH);

        categoryMainPanel.add(headerPanel, BorderLayout.NORTH);


        //Center
        middlePanel.add(categoryTitleLabel);
        for (JButton button : categoryButtons) {
            middlePanel.add(button);
        }

        categoryMainPanel.add(middlePanel, BorderLayout.CENTER);


        //Footer
        bottomPanel.setLayout(new GridLayout(2,4));
        bottomPanel.add(homeIconLabel);
        bottomPanel.add(new JLabel());
        bottomPanel.add(new JLabel());
        bottomPanel.add(friendIconLabel);
        bottomPanel.add(new JLabel("Home", SwingConstants.CENTER));
        bottomPanel.add(new JLabel());
        bottomPanel.add(new JLabel());
        bottomPanel.add(new JLabel("Friends", SwingConstants.CENTER));
        categoryMainPanel.add(bottomPanel, BorderLayout.SOUTH);


        //frame.add(categoryMainPanel);
        //frame.setVisible(true);
    }

    public JPanel getCategoryMainPanel() {
        return categoryMainPanel;
    }

    public void setCategorySelectionboard(List<QuestionsByCategory> questionsByCategories) {
        for (int i = 0; i < categoryButtons.length; i++) {
            categoryButtons[i].setText(questionsByCategories.get(i).results().getFirst().category());
        }
    }





        /*
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            CategorySelectionBoard selectionBoard = new CategorySelectionBoard();
            selectionBoard.CategorySelectionBoardinit();
        });

    }*/
}