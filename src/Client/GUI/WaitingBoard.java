package Client.GUI;

import javax.swing.*;
import java.awt.*;

public class WaitingBoard extends AbstractBoard {
    private JLabel waitingLabel;



    public WaitingBoard() {
        super();
    }


        @Override
                protected void initComponents(){
        waitingLabel = new JLabel("Waiting for player...", SwingConstants.CENTER);
        waitingLabel.setFont(new Font("Arial", Font.BOLD, 20));

    }

    @Override
            protected void buildLayout(){
        board.setLayout(new BorderLayout());
        board.add(waitingLabel, BorderLayout.CENTER);

    }
}
