import java.io.File;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import connection.HandleChange;
import connection.ServerConnection;
import connection.WatchDirectory;

public class Main extends JFrame {
    private Thread watchThread;
    private Thread handleThread;

    public void initUI() {
        setTitle("Client");
        setPreferredSize(new Dimension(720, 480));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField server = new JTextField();
        server.setText("localhost");
        server.setPreferredSize(new Dimension(200, 30));

        JTextField folder = new JTextField();
        folder.setPreferredSize(new Dimension(200, 30));
        JPanel panel = new JPanel();
        panel = new JPanel(new GridBagLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Loop through all the threads and interrupt them
                watchThread.stop();

                System.out.println("Thread closed");
                // Call the super method to close the window
                super.windowClosing(e);
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0;
        constraints.weighty = 0.5;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("SERVER IP: "), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(server, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("FOLDER PATH: "), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(folder, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        JButton submitAddButton = new JButton("CONNECT");

        panel.add(submitAddButton, constraints);

        submitAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerConnection.initConnect(server.getText(), 5000);
                String path = folder.getText();
                // Create a new thread to run the loop that monitors for changes
                watchThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        
                        try {
                            WatchDirectory watch = new WatchDirectory();
                            watch.Running(path);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                // Start the thread to run the loop
                watchThread.start();
            }
        });

        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.initUI();
        // ------------------
    }
}
