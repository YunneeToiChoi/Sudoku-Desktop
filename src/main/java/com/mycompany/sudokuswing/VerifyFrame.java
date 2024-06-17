/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sudokuswing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import Database.DatabaseConnector;

/**
 *
 * @author Tran Giap
 */
public class VerifyFrame extends JFrame {

    private JPanel jPanel1;
    private JLabel jLabel1;
    private JTextField txtCode;
    private JLabel jLabel2;
    private JButton btnCancel;
    private JButton btnOK;
    private String fullname;
    private String email;
    private String password;
    private String otp;

    public VerifyFrame() {
        initComponents();
    }

    public VerifyFrame(String fullname, String email, String password, String otp) {
        initComponents();
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.otp = otp;
    }
    
    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        txtCode = new JTextField();
        jLabel2 = new JLabel();
        btnCancel = new JButton();
        btnOK = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Verify");
        this.setLocation(445, 195);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Verify Code");

        txtCode.setHorizontalAlignment(JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Check your email to get verify code");

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnOK.setText("OK");
        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(173, 173, 173)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE))
                                .addGap(155, 155, 155))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnOK, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                .addGap(47, 47, 47)
                                                .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                .addGap(165, 165, 165))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtCode, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
                                                .addGap(141, 141, 141)))));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtCode, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnOK)
                                        .addComponent(btnCancel))
                                .addContainerGap(23, Short.MAX_VALUE)));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(84, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(68, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null); // This line centers the frame on the screen
    }

    private void btnCancelActionPerformed(ActionEvent evt) {
        backSignUp();
    }

    private void btnOKActionPerformed(ActionEvent evt) {
        String code = txtCode.getText();
        String insertUser = "INSERT INTO Users (fullname, email, password) VALUES (?, ?, ?)";
        if (code.equals(otp)) {
            try (Connection con = DatabaseConnector.getConnection();
                    PreparedStatement stmt = con.prepareStatement(insertUser)) {
                stmt.setString(1, fullname);
                stmt.setString(2, email);
                stmt.setString(3, password);
                int result = stmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    backLogIn();
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error accessing database!");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Invalid OTP!");
            backSignUp();
        }
    }

    private void backSignUp() {
        new SignUpFrame().setVisible(true);
        dispose();
    }

    private void backLogIn() {
        new LogInFrame().setVisible(true);
        dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VerifyFrame().setVisible(true);
            }
        });
    }
}
