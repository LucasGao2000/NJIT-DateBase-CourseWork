package gui;

import com.sun.tools.javac.Main;
import sql.MainController;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;
    private MainController controller;

//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    LoginFrame frame = new LoginFrame();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * Create the frame.
     */
    public LoginFrame(MainController controller) throws SQLException {

        this.controller = controller;
        setTitle("Welome to login!");
        //setEnabled(false);

        controller.connect("root", "root");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(700, 270, 673, 543);
        //this.setUndecorated(true);
        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 255, 255));
        contentPane.setBackground(new Color(105, 105, 105));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(387, 174, 227, 246);
        contentPane.add(panel_1);
        panel_1.setBackground(Color.DARK_GRAY);
        panel_1.setLayout(null);

        JLabel label = new JLabel("\u7528\u6237\u540D:");
        label.setForeground(new Color(255, 255, 255));
        label.setFont(new Font("等线 Light", Font.PLAIN, 16));
        label.setBounds(10, 75, 56, 39);
        panel_1.add(label);

        JLabel label_1 = new JLabel("\u5BC6\u7801:");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setFont(new Font("等线 Light", Font.PLAIN, 16));
        label_1.setForeground(new Color(255, 255, 255));
        label_1.setBounds(10, 123, 56, 44);
        panel_1.add(label_1);

        textField = new JTextField();
        textField.setFont(new Font("宋体", Font.PLAIN, 15));
        textField.setBounds(76, 84, 141, 21);
        panel_1.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("宋体", Font.PLAIN, 15));
        passwordField.setBounds(76, 135, 141, 21);
        panel_1.add(passwordField);

        JRadioButton radioButton = new JRadioButton("\u5B66\u751F");
        radioButton.setFont(new Font("微软雅黑 Light", Font.PLAIN, 13));
        radioButton.setForeground(Color.WHITE);
        radioButton.setBackground(Color.DARK_GRAY);
        radioButton.setBounds(10, 173, 64, 23);
        panel_1.add(radioButton);

        JRadioButton radioButton_1 = new JRadioButton("\u8001\u5E08");
        radioButton_1.setBackground(Color.DARK_GRAY);
        radioButton_1.setForeground(Color.WHITE);
        radioButton_1.setFont(new Font("微软雅黑 Light", Font.PLAIN, 13));
        radioButton_1.setBounds(76, 173, 74, 23);
        panel_1.add(radioButton_1);

        JRadioButton radioButton_2 = new JRadioButton("\u7BA1\u7406\u5458");
        radioButton_2.setBackground(Color.DARK_GRAY);
        radioButton_2.setFont(new Font("微软雅黑 Light", Font.PLAIN, 13));
        radioButton_2.setForeground(Color.WHITE);
        radioButton_2.setBounds(144, 173, 75, 23);
        panel_1.add(radioButton_2);

        JLabel label_2 = new JLabel("\u5B66\u751F\u6210\u7EE9\u7BA1\u7406\u7CFB\u7EDF");
        label_2.setFont(new Font("微软雅黑 Light", Font.BOLD, 17));
        label_2.setForeground(Color.LIGHT_GRAY);
        label_2.setBounds(20, 10, 157, 44);
        panel_1.add(label_2);

        JButton btnNewButton = new JButton("\u767B\u5F55");
        btnNewButton.setBorder(null);
        btnNewButton.addActionListener(this);
        btnNewButton.setBackground(Color.DARK_GRAY);
        btnNewButton.setFont(new Font("微软雅黑 Light", Font.PLAIN, 13));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBounds(32, 205, 64, 31);
        panel_1.add(btnNewButton);
        btnNewButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                btnNewButton.setBackground(Color.gray);
                btnNewButton.setForeground(Color.black);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                btnNewButton.setBackground(Color.darkGray);
                btnNewButton.setForeground(Color.white);
            }
        });


        JButton button = new JButton("\u9000\u51FA");
        button.addActionListener(this);
        button.setBorder(null);
        button.setFont(new Font("微软雅黑 Light", Font.PLAIN, 13));
        button.setForeground(Color.WHITE);
        button.setBounds(130, 205, 64, 31);
        panel_1.add(button);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                button.setBackground(Color.gray);
                button.setForeground(Color.black);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                button.setBackground(Color.darkGray);
                button.setForeground(Color.white);
            }
        });


        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setBounds(0, 228, 657, 151);
        contentPane.add(panel);
        this.setVisible(true);
    }

    private void login() throws SQLException {
        String user = this.textField.getText();
        String password = this.passwordField.getText();

        System.out.println(user);
        System.out.println(password);

        ResultSet login = controller.login(user, password);
        if (login == null) {
            JOptionPane.showMessageDialog(this, "用户名或密码错误");
            return;
        } else {
            login.next();
            String string = login.getString(1);
            MainFrame mainFrame = new MainFrame(controller, string);
        }


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand() == "退出") {
            System.exit(0);
        } else {
            try {
                login();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //controller.selectQuery("select"),


        }


    }
}