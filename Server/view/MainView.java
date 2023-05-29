package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import instance.IconList;

public class MainView extends JFrame {
    public static DefaultListModel listConnectionModel = new DefaultListModel<>();
    public static ArrayList<String> listLogChange = new ArrayList<String>();
    public static JList jListConnection;

    public static JLabel changeInfLabel = new JLabel();

    public MainView() {
        IconList.initListIcon();
        this.initUI();
        // -------------------
        jListConnection = new JList<>(listConnectionModel);
        jListConnection.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                changeInfLabel.setText(listLogChange.get(jListConnection.getSelectedIndex()));
            }
        });
        jListConnection.setPreferredSize(new Dimension(200, 600));
        this.getContentPane().add(jListConnection, BorderLayout.WEST);

        changeInfLabel.setPreferredSize(new Dimension(300, 500));
        this.getContentPane().add(changeInfLabel, BorderLayout.CENTER);
        // --------------------
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void initUI() {
        this.setTitle("Tracker Folder Server");
        this.setIconImage(IconList.favicon.getImage());
        this.setPreferredSize(new Dimension(1080, 720));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

}