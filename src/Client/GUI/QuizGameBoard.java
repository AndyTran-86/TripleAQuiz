package Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuizGameBoard extends  AbstractBoard {

    private JPanel questionPanel = new JPanel();
    private JPanel answerPanel = new JPanel();

    private JTextArea questionTextArea;
    private JLabel timerLabel;

    private List<JButton> answerButtons;


    public QuizGameBoard() {
        super();
    }

    public JTextArea getQuestionTextArea() {
        return questionTextArea;
    }

    public List<JButton> getAnswerButtons() {
        return answerButtons;
    }

    @Override
    protected void initComponents(){
        questionPanel = new JPanel();
        answerPanel = new JPanel();

        questionTextArea = new JTextArea("Question will be here");
        questionTextArea.setFont(new Font("Arial",Font.PLAIN, 24));
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setEditable(false);
        questionTextArea.setOpaque(false);


        timerLabel = new JLabel("Time left: 30s", SwingConstants.CENTER);



        answerButtons = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            answerButtons.add(new JButton("Answer " + (i + 1)));
            answerButtons.get(i).setFont(new Font("Arial", Font.BOLD, 16));
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
