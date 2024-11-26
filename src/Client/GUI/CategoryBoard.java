package Client.GUI;

import Server.QuizDatabase.QuestionsByCategory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryBoard extends AbstractBoard {

    private JLabel categoryTitleLabel;
    private JButton[] categoryButtons;

    public CategoryBoard() {
        super();
    }
    @Override
    protected void initComponents(){
        categoryTitleLabel = new JLabel("Select a category", SwingConstants.CENTER);
        categoryTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoryTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        categoryButtons = new JButton[3];
        for (int i = 0; i < categoryButtons.length; i++) {
            categoryButtons[i] = new JButton("Category " + (i + 1));
            categoryButtons[i].setFont(new Font("Arial", Font.BOLD, 16));

        }

    }

    @Override
    protected void buildLayout() {
        board.setLayout(new GridLayout(4, 1, 10, 10));

        board.add(categoryTitleLabel);
        for (JButton button : categoryButtons) {
            board.add(button);
        }
    }

    public JButton[] getCategoryButtons() {
        return categoryButtons;
    }
}