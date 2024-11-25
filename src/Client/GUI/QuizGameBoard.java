package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class QuizGameBoard extends  AbstractBoard {

    private JPanel questionPanel = new JPanel();
    private JPanel answerPanel = new JPanel();

    private JTextArea questionTextArea;
    private JLabel timerLabel;

    private JButton[] answerButtons;


    public QuizGameBoard() {
        super();
    }


    @Override
    protected void initComponents(){
        questionPanel = new JPanel();
        answerPanel = new JPanel();

        questionTextArea = new JTextArea("Question will be here, I hope its okay to put the question here. " +
                "I write some just to fill it and see how it looks");
        questionTextArea.setFont(new Font("Arial",Font.PLAIN, 24));
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setEditable(false);
        questionTextArea.setOpaque(false);


        timerLabel = new JLabel("Time left: 30s", SwingConstants.CENTER);



        answerButtons = new JButton[4];
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new JButton("Answer " + (i + 1));
            answerButtons[i].setFont(new Font("Arial", Font.BOLD, 16));
        }

    }

@Override
    protected void buildLayout() {
    board.setLayout(new BorderLayout());

    questionPanel.setLayout(new BorderLayout());
    questionPanel.add(questionTextArea, BorderLayout.CENTER);
    questionPanel.add(timerLabel, BorderLayout.SOUTH);
    board.add(questionPanel, BorderLayout.NORTH);

    answerPanel.setLayout(new GridLayout(2, 2, 10, 10));
    for (JButton button : answerButtons) {
        answerPanel.add(button);
    }
    board.add(answerPanel, BorderLayout.CENTER);


    }

}
