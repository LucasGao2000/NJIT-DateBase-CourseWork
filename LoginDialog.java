package gui;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JFrame {
    private JLabel jLabel = new JLabel();           //标题
    private JLabel[] labels = new JLabel[2];
    private JTextField[] textFields = new JTextField[2];
    //private ImageIcon imageIcon = new ImageIcon("piclib/image1.png");
    private JPanel content;
    private JLabel[] submitLables = new JLabel[2];
    private JLabel[] switchLables = new JLabel[2];
    private JButton[] jButtons = new JButton[2];



    public LoginDialog() throws HeadlessException {
        super("欢迎登录");
        content = new JPanel();
        this.getContentPane().add(content);
//        this.getContentPane().setLayout(null);
        content.setLayout(null);
        this.setBounds(700,400,700,600);
        this.setResizable(false);
       // this.setUndecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        jLabel.setText("欢迎登录");
//        jLabel.setHorizontalAlignment(JLabel.CENTER);
//        jLabel.setFont(new Font("宋体", Font.BOLD, 40));
//        jLabel.setBounds(0,60,600,40);
//        content.add(jLabel);
        labels[0] = new JLabel("用户名:",JLabel.CENTER);
        labels[0].setFont(new Font("宋体",Font.BOLD,26));
        labels[0].setForeground(Color.WHITE);
        labels[1] = new JLabel("密码:",JLabel.CENTER);
        labels[1].setFont(new Font("宋体",Font.BOLD,26));
        labels[0].setBounds(100,310,100,30);
        labels[1].setBounds(100,380,100,30);
        labels[1].setForeground(Color.WHITE);
        content.add(labels[0]);
        content.add(labels[1]);
        textFields[0] = new JTextField();
        textFields[1] = new JTextField();
        textFields[0].setBounds(280,310,300,30);
        textFields[0].setFont(new Font("宋体",Font.BOLD,20));
        content.add(textFields[0]);
        textFields[1].setBounds(280,380,300,30);
        textFields[1].setFont(new Font("宋体",Font.BOLD,20));
        content.add(textFields[1]);

        this.setVisible(true);

    }
}
