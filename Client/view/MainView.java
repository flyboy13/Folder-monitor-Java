package view;

import javax.swing.JFrame;
import java.awt.Dimension;
import instance.IconList;

public class MainView extends JFrame {
    public MainView() {
        this.initUI();
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void initUI() {
        this.setTitle("Main view client");
        this.setIconImage(IconList.favicon.getImage());
        this.setPreferredSize(new Dimension(1080, 720));

        this.setResizable(false);
        this.setVisible(true);
    }

}