package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("rawtypes")

public class MainView extends JFrame {
    public static DefaultListModel listConnectionModel = new DefaultListModel();
    public static ArrayList<String> listLogChange = new ArrayList<String>();
    public static JList jListConnection;

    public static JLabel content = new JLabel();

    private Thread changeThread;

    


    public void refreshConnectionList() {
        jListConnection.setModel(listConnectionModel);
        content.revalidate();
        content.repaint();
        
        // revalidate();
        // repaint();
    }

    public MainView() {
        initUI();
        // -------------------
        jListConnection = new JList(listConnectionModel);
        jListConnection.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String temp = listLogChange.get(jListConnection.getSelectedIndex());
                // System.out.println("Content: " + temp);
                content.setText(temp);
            }
        });
        jListConnection.setPreferredSize(new Dimension(200, 600));
        getContentPane().add(jListConnection, BorderLayout.WEST);

        content.setPreferredSize(new Dimension(300, 500));
        getContentPane().add(MainView.content, BorderLayout.CENTER);
        // --------------------
        pack();
    }

    private void initUI() {

        

        this.setTitle("Server");
        ImageIcon img = new ImageIcon("image/icon.png");
        this.setIconImage(img.getImage());
        this.setPreferredSize(new Dimension(1080, 720));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}