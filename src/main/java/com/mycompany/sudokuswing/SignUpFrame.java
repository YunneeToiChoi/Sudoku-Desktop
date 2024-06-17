/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sudokuswing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import Email.EmailCenter;
import Database.DatabaseConnector;
/**
 *
 * @author Tran Giap
 */
public class SignUpFrame extends JFrame {

    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JLabel jLabel1;
    private JLabel jLabel9;
    private JLabel jLabel10;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField fullnameField;
    private JLabel jLabel6;
    private JTextField emailField;
    private JLabel jLabel7;
    private JPasswordField passwordField;
    private JButton jButton1;
    private JButton jButton2;

    public SignUpFrame() {
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jLabel1 = new JLabel();
        jLabel9 = new JLabel();
        jLabel10 = new JLabel();
        jPanel3 = new JPanel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        fullnameField = new JTextField();
        jLabel6 = new JLabel();
        emailField = new JTextField();
        jLabel7 = new JLabel();
        passwordField = new JPasswordField();
        jButton1 = new JButton();
        jButton2 = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign Up");
        this.setLocation(445, 195);
        setPreferredSize(new Dimension(800, 500));

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new Color(255, 255, 255));

        jLabel9.setIcon(new ImageIcon(getClass().getResource("/Icon/title.jpg")));
        jLabel9.setText("jLabel8");

        jLabel10.setFont(new Font("Showcard Gothic", Font.BOLD, 24));
        jLabel10.setForeground(new Color(255, 255, 255));
        jLabel10.setIcon(new ImageIcon(getClass().getResource("/Icon/icon.jpg")));
        jLabel10.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel10.setHorizontalTextPosition(SwingConstants.CENTER);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(378, 378, 378)
                                .addComponent(jLabel1)
                                .addContainerGap(22, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(82, 82, 82))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap()))));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel9, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel10))
                                .addContainerGap(103, Short.MAX_VALUE)));

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 400, 500);

        jPanel3.setBackground(new Color(255, 255, 255));

        jLabel4.setBackground(new Color(0, 0, 0));
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel4.setText("SIGN UP");

        jLabel5.setBackground(new Color(0, 0, 0));
        jLabel5.setFont(new Font("Times New Roman", Font.BOLD, 24));
        jLabel5.setText("Full name");
        jLabel5.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        fullnameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fullnameField.setForeground(new Color(102, 102, 102));

        jLabel6.setBackground(new Color(0, 0, 0));
        jLabel6.setFont(new Font("Times New Roman", Font.BOLD, 24));
        jLabel6.setText("Email");

        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setForeground(new Color(102, 102, 102));

        jLabel7.setBackground(new Color(0, 0, 0));
        jLabel7.setFont(new Font("Times New Roman", Font.BOLD, 24));
        jLabel7.setText("Password");

        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setForeground(new Color(102, 102, 102));

        jButton1.setBackground(new Color(0, 0, 0));
        jButton1.setForeground(new Color(255, 255, 255));
        jButton1.setText("Sign Up");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        jButton2.setBackground(new Color(0, 0, 0));
        jButton2.setForeground(new Color(255, 255, 255));
        jButton2.setText("Login");
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(145, 145, 145)
                                                .addComponent(jLabel4))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jLabel5)
                                                        .addComponent(fullnameField)
                                                        .addComponent(jLabel6)
                                                        .addComponent(emailField, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                                                        .addComponent(jLabel7)
                                                        .addComponent(passwordField))))
                                .addContainerGap(24, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel4)
                                .addGap(17, 17, 17)
                                .addComponent(jLabel5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fullnameField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addGap(11, 11, 11)
                                .addComponent(emailField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                                .addContainerGap(120, Short.MAX_VALUE)));

        jPanel1.add(jPanel3);
        jPanel3.setBounds(400, 0, 400, 500);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 113, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 126, Short.MAX_VALUE)));

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String fullname = fullnameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String checkEmailSQL = "SELECT email FROM Users WHERE email = ?";
        try (Connection con = DatabaseConnector.getConnection();
                PreparedStatement checkEmailStmt = con.prepareStatement(checkEmailSQL)) {

            // Check exist email
            checkEmailStmt.setString(1, email);
            ResultSet rs = checkEmailStmt.executeQuery();
            if (rs.next()) {
                init();
                JOptionPane.showMessageDialog(this, "Email already registered!");
            } else {
                String otp = generateOTP();
                // Send email
                EmailCenter.sendEmail(otp, email);

                new VerifyFrame(fullname, email, password, otp).setVisible(true);
                init();
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during registration!");
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        new LogInFrame().setVisible(true);
        dispose();
    }
    
    
    private void init() {
        fullnameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = random.nextInt(999999);
        return String.format("%06d", otp);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignUpFrame().setVisible(true);
        });
    }

}
