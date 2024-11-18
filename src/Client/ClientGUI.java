package Client;

import javax.swing.*;
import java.awt.*;

public class ClientGUI {
    JFrame frame;
    JLabel label;


    String title = "Triple-A Quiz!";
    JPanel panel;

    public ClientGUI() {
        frame = new JFrame(title);
        panel = new JPanel();
        label = new JLabel();
        panel.add(label);
        frame.add(panel);
        setLabelText("Connected");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(500, 500));
        frame.setVisible(true);
    }

    public void setLabelText(String message) {
        label.setText(message);
    }
}
