import instance.HandleChange;

import java.io.File;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import connection.ServerConnection;

public class Main extends JFrame {
    public static JPanel inputPanel;

    public Main() {
    }

    public static void main(String[] args) {
        Main ui = new Main();
        ui.initUI();
        // ------------------
    }

    void initUI() {
        this.setTitle("Tracker Folder Client");
        this.setPreferredSize(new Dimension(1080, 720));
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JTextField ipServerInput = new JTextField();
        ipServerInput.setPreferredSize(new Dimension(200, 50));
        JTextField portServerInput = new JTextField();
        portServerInput.setPreferredSize(new Dimension(200, 50));
        JTextField folderInput = new JTextField();
        folderInput.setPreferredSize(new Dimension(200, 50));
        inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.gridwidth = 1;
        GridBagConstraints gbc_field = new GridBagConstraints();
        gbc_field.gridwidth = 2;

        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        gbc_field.gridx = 1;
        gbc_field.gridy = 0;
        gbc_field.insets = gbc_label.insets = new Insets(10, 10, 10, 10);

        inputPanel.add(new JLabel("SERVER IP: "), gbc_label);
        inputPanel.add(ipServerInput, gbc_field);

        gbc_label.gridy = 1;
        gbc_field.gridy = 1;

        inputPanel.add(new JLabel("SERVER PORT: "), gbc_label);
        inputPanel.add(portServerInput, gbc_field);

        gbc_label.gridy = 2;
        gbc_field.gridy = 2;

        inputPanel.add(new JLabel("FOLDER PATH  : "), gbc_label);
        inputPanel.add(folderInput, gbc_field);

        JButton submitAddButton = new JButton("CONNECT");
        submitAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerConnection.initConnect(ipServerInput.getText(), Integer.parseInt(portServerInput.getText()));
                Main.inputPanel.removeAll();
                Main.inputPanel.setLayout(new BorderLayout());
                Main.inputPanel.add(new JLabel("Connection successfuly! Your folder is tracking by server"),
                        BorderLayout.CENTER);
                String path = folderInput.getText();
                File root = new File(path);
                File list[] = root.listFiles();
                long modified[] = new long[list.length];
                for (int i = 0; i < list.length; i++)
                    modified[i] = list[i].lastModified();
                File list1[];
                try {
                    while (true) {
                        File root1 = new File(path);
                        list1 = root1.listFiles();
                        for (int i = 0; i < list1.length; i++)
                            if ((list.length != list1.length) || (list1[i].compareTo(list[i]) != 0)
                                    || (modified[i] != list1[i].lastModified())) {
                                HandleChange handle = new HandleChange(list, list1, modified);
                                Thread handleThread = new Thread(handle);
                                handleThread.start();
                                list = list1;
                                modified = new long[list1.length];
                                for (int j = 0; j < list.length; j++)
                                    modified[j] = list1[j].lastModified();
                            }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        gbc_label.gridx = 2;
        gbc_label.gridy = 5;
        gbc_label.gridwidth = 2;
        gbc_label.gridheight = 2;
        inputPanel.add(submitAddButton, gbc_label);
        this.getContentPane().add(inputPanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
