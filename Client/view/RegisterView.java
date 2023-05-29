package view;

import javax.swing.*;

import connection.FileHandle;
import connection.ServerConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import instance.IconList;

public class RegisterView extends JFrame {
    public RegisterView() {
        this.initUI();

        JPanel formPanel = new JPanel();
        // ----- USERNAME
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 50));
        // ----- PASSWORD
        JTextField passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(200, 50));
        // ----- FULL NAME
        JTextField fullnameField = new JTextField();
        fullnameField.setPreferredSize(new Dimension(200, 50));
        // ----- AVATAR
        JTextField avatarField = new JTextField();
        avatarField.setPreferredSize(new Dimension(200, 50));
        JFileChooser chooserAvatar = new JFileChooser();

        JButton chooserButton = new JButton("Browser");
        chooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooserAvatar.showOpenDialog(null);
                avatarField.setText(chooserAvatar.getSelectedFile().getPath());
            }
        });
        // ---------
        JLabel alertLable = new JLabel();

        JButton submitButton = new JButton("Submit");
        ActionListener submitAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String register_Status = "";
                String username = usernameField.getText();
                String password = passwordField.getText();
                String fullname = fullnameField.getText();
                String avatarPath = avatarField.getText();
                if ((!username.equals("") && (!password.equals("")) && (!fullname.equals(""))
                        && (!avatarField.equals("")))) {
                    DataOutputStream out = ServerConnection.outStream;
                    DataInputStream in = ServerConnection.inputStream;
                    try {
                        out.writeUTF("REGISTER");
                        out.writeUTF(username);
                        out.writeUTF(password);
                        out.writeUTF(fullname);
                        // ------ SEND PICTURE
                        FileHandle.sendFile(out, avatarPath);
                        register_Status = in.readUTF();
                        switch (register_Status) {
                            case "DONE":
                                out.writeUTF("CLOSE_CONNECTION");
                                new MainView();
                                dispose();
                                break;
                            case "EXISTED":
                                alertLable.setText("THIS USERNAME IS EXISTED");
                                alertLable.setForeground(Color.red);
                                break;
                            default:
                                break;
                        }
                    } catch (Exception ex) {
                        System.out.println("Error in while send register data");
                    }
                }
            }
        };
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        formPanel.add(new JLabel("Username: "), gbc);
        gbc.gridy = 1;
        formPanel.add(new JLabel("Pasword: "), gbc);
        gbc.gridy = 2;
        formPanel.add(new JLabel("Name: "), gbc);
        gbc.gridy = 3;
        formPanel.add(new JLabel("Avatar: "), gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;

        formPanel.add(chooserButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 4;

        formPanel.add(usernameField, gbc);
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);
        gbc.gridy = 2;
        formPanel.add(fullnameField, gbc);
        gbc.gridy = 3;
        formPanel.add(avatarField, gbc);
        gbc.gridy = 6;
        gbc.gridwidth = 6;
        formPanel.add(submitButton, gbc);

        gbc.gridy = 7;
        formPanel.add(alertLable, gbc);
        submitButton.addActionListener(submitAction);
        this.getContentPane().add(formPanel, BorderLayout.CENTER);
        // -------
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void initUI() {
        this.setTitle("Register");
        this.setIconImage(IconList.favicon.getImage());
        this.setPreferredSize(new Dimension(720, 480));

        this.setResizable(false);
        this.setVisible(true);
    }
}
