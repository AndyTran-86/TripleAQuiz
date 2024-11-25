package Client.GUI;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractBoard {
    protected JPanel board;


    public AbstractBoard() {
        board = new JPanel(new BorderLayout());
        initComponents();
        buildLayout();
    }

    protected abstract void initComponents();

    protected abstract void buildLayout();

    public JPanel getBoard() {
        return board;
    }

}
