package com.mycompany.sudokuswing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import Database.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInFrame extends JFrame {

    private JPanel jPanel1;
    private JPanel Right;
    private JPanel Left;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel8;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton jButton1;
    private JButton jButton2;

    public LogInFrame() {
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        Right = new JPanel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel8 = new JLabel();
        Left = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        emailField = new JTextField();
        jLabel3 = new JLabel();
        passwordField = new JPasswordField();
        jButton1 = new JButton();
        jButton2 = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");
        this.setLocation(445, 195);
        

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(800, 500));
        jPanel1.setLayout(null);

        Right.setBackground(new Color(255, 255, 255));
        Right.setPreferredSize(new Dimension(400, 500));

        jLabel6.setFont(new Font("Showcard Gothic", Font.BOLD, 24));
        jLabel6.setForeground(new Color(255, 255, 255));
        jLabel6.setIcon(new ImageIcon(getClass().getResource("/Icon/icon.jpg")));
        jLabel6.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setHorizontalTextPosition(SwingConstants.CENTER);

        jLabel8.setIcon(new ImageIcon(getClass().getResource("/Icon/title.jpg")));
        jLabel8.setText("jLabel8");

        GroupLayout RightLayout = new GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
                RightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(RightLayout.createSequentialGroup()
                                .addGroup(RightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(RightLayout.createSequentialGroup()
                                                .addGap(87, 87, 87)
                                                .addComponent(jLabel6)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel5)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, RightLayout.createSequentialGroup()
                                                .addContainerGap(28, Short.MAX_VALUE)
                                                .addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap()));
        RightLayout.setVerticalGroup(
                RightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(RightLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                .addGroup(RightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(RightLayout.createSequentialGroup()
                                                .addGap(243, 243, 243)
                                                .addComponent(jLabel5))
                                        .addGroup(RightLayout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(jLabel6)))
                                .addContainerGap(87, Short.MAX_VALUE)));

        jPanel1.add(Right);
        Right.setBounds(0, 0, 400, 500);

        Left.setBackground(new Color(255, 255, 255));
        Left.setMinimumSize(new Dimension(400, 500));

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36));
        jLabel1.setForeground(new Color(0, 0, 0));
        jLabel1.setText("LOGIN");

        jLabel2.setBackground(new Color(102, 102, 102));
        jLabel2.setFont(new Font("Times New Roman", Font.BOLD, 24));
        jLabel2.setText("Email");

        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setForeground(new Color(102, 102, 102));

        jLabel3.setBackground(new Color(102, 102, 102));
        jLabel3.setFont(new Font("Times New Roman", Font.BOLD, 24));
        jLabel3.setText("Password");

        jButton1.setBackground(new Color(0, 0, 0));
        jButton1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setForeground(new Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed();
            }
        });

        jButton2.setBackground(new Color(0, 0, 0));
        jButton2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jButton2.setForeground(new Color(255, 255, 255));
        jButton2.setText("Sign Up");
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed();
            }
        });

        GroupLayout LeftLayout = new GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
                LeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(LeftLayout.createSequentialGroup()
                                .addGroup(LeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(LeftLayout.createSequentialGroup()
                                                .addGap(138, 138, 138)
                                                .addComponent(jLabel1))
                                        .addGroup(LeftLayout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addGroup(LeftLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(GroupLayout.Alignment.LEADING, LeftLayout.createSequentialGroup()
                                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jLabel2, GroupLayout.Alignment.LEADING)
                                                        .addComponent(emailField, GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3, GroupLayout.Alignment.LEADING)
                                                        .addComponent(passwordField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))))
                                .addContainerGap(366, Short.MAX_VALUE)));
        LeftLayout.setVerticalGroup(
                LeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(LeftLayout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jLabel1)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(emailField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(LeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                                .addContainerGap(130, Short.MAX_VALUE)));

        jPanel1.add(Left);
        Left.setBounds(400, 0, 739, 503);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));
        pack();
    }

     private void jButton1ActionPerformed() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?")) {

            System.out.println("Connect Success");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int user_id = rs.getInt("user_id"); 
                init();
                JOptionPane.showMessageDialog(this, "Login successful!");
                moveMainFrame(user_id);
            } else {
                JOptionPane.showMessageDialog(this, "Login was unsuccessful, please check your email and password");
                init();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Invalid email or password!");
        }
    }

    private void init() {
        emailField.setText("");
        passwordField.setText("");
    }

    private void jButton2ActionPerformed() {
        new SignUpFrame().setVisible(true);
        dispose();
    }
    
    private void moveMainFrame(Integer id) {
        new SudokuFrame(id).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LogInFrame().setVisible(true);
        });
    }
}
