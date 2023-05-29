package view;

import javax.swing.JFrame;
import java.awt.Dimension;
import instance.IconList;

public class LoginView extends JFrame {
    public LoginView() {
        this.initUI();
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void initUI() {
        this.setTitle("Login");
        this.setIconImage(IconList.favicon.getImage());
        this.setPreferredSize(new Dimension(720, 480));

        this.setResizable(false);
        this.setVisible(true);
    }
}
