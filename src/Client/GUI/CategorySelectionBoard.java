package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class CategorySelectionBoard {
    JFrame frame;

    JPanel categoryMainPanel;

    JLabel categoryTitleLabel;

    JButton[] categoryButtons;


    public CategorySelectionBoard() {
        frame = new JFrame("Category Selection Board");
        categoryMainPanel = new JPanel();

        categoryTitleLabel = new JLabel("Select a category", SwingConstants.CENTER);
        categoryTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoryTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        categoryButtons = new JButton[3];
        for (int i = 0; i < categoryButtons.length; i++) {
            categoryButtons[i] = new JButton("Category " + (i + 1));
            categoryButtons[i].setFont(new Font("Arial", Font.BOLD, 16));

        }
    }



        public void CategorySelectionBoardinit(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600,1000);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        categoryMainPanel.setLayout(new BoxLayout(categoryMainPanel, BoxLayout.Y_AXIS));
        categoryMainPanel.add(Box.createVerticalStrut(200));

        categoryMainPanel.add(categoryTitleLabel);

        categoryMainPanel.add(Box.createVerticalStrut(80));


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1,30,30));
        for (JButton button : categoryButtons) {
            buttonPanel.add(button);
        }

        categoryMainPanel.add(buttonPanel);

        frame.add(categoryMainPanel, BorderLayout.CENTER);

        frame.setVisible(true);


        }
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            CategorySelectionBoard selectionBoard = new CategorySelectionBoard();
            selectionBoard.CategorySelectionBoardinit();
        });

    }
}