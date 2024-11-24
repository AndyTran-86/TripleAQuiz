package Client.GUI;

import Server.QuizDatabase.QuestionsByCategory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryBoard {

    JPanel middlePanel;
    JLabel categoryTitleLabel;
    JButton[] categoryButtons;

    public CategoryBoard() {

        middlePanel = new JPanel(new GridLayout(4, 1, 10, 10));

        categoryTitleLabel = new JLabel("Select a category", SwingConstants.CENTER);
        categoryTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoryTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        categoryButtons = new JButton[3];
        for (int i = 0; i < categoryButtons.length; i++) {
            categoryButtons[i] = new JButton("Category " + (i + 1));
            categoryButtons[i].setFont(new Font("Arial", Font.BOLD, 16));

        }

        buildLayout();
    }


    public void buildLayout() {
        middlePanel.add(categoryTitleLabel);
        for (JButton button : categoryButtons) {
            middlePanel.add(button);
        }
    }



    public JPanel getMiddlePanel(){
        return middlePanel;
    }



    public void setCategoryBoard(List<QuestionsByCategory> questionsByCategories) {
        for (int i = 0; i < categoryButtons.length; i++) {
            categoryButtons[i].setText(questionsByCategories.get(i).results().getFirst().category());
        }
    }
}