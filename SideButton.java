package gui;

import javax.swing.*;
import java.awt.*;

public class SideButton {
    public static void initsideButton_student(MainFrame frame)
    {
        ImageIcon imageIcon1 = new ImageIcon("piclib/select.png");
        imageIcon1 = new ImageIcon(imageIcon1.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH));
        frame.side_buttons[0] = new JButton();
        frame.side_buttons[0].setActionCommand("查询");
        frame.side_buttons[0].setIcon(imageIcon1);
        frame.side_buttons[0].setBounds(5,15,60,60);
        frame.side_buttons[0].setBorder(null);
        frame.navi.add(frame.side_buttons[0]);

        ImageIcon imageIcon5 = new ImageIcon("piclib/analyze.png");
        imageIcon5 = new ImageIcon(imageIcon5.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        frame.analyze = new JButton(imageIcon5);
        frame.analyze.setBounds(5,90,60,60);
        frame.analyze.setBorder(null);
        frame.analyze.setActionCommand("分析");
        frame.navi.add(frame.analyze);
        frame.analyze.addActionListener(frame);
        frame.addMouseListener(frame);
        for(int i=0;i<1;i++)
        {
            frame.side_buttons[i].addActionListener(frame);
            frame.side_buttons[i].addMouseListener(frame);
        }
    }

    public static void initsideButton_teacher(MainFrame frame)
    {
        ImageIcon imageIcon1 = new ImageIcon("piclib/select.png");
        imageIcon1 = new ImageIcon(imageIcon1.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH));
        frame.side_buttons[0] = new JButton();
        frame.side_buttons[0].setActionCommand("查询");

        frame.side_buttons[0].setIcon(imageIcon1);
        frame.side_buttons[0].setBounds(5,15,60,60);
        frame.side_buttons[0].setBorder(null);
        frame.navi.add(frame.side_buttons[0]);

        ImageIcon imageIcon2 = new ImageIcon("piclib/update.png");
        imageIcon2 = new ImageIcon(imageIcon2.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH));
        frame.side_buttons[1] = new JButton();
        frame.side_buttons[1].setActionCommand("修改");

        frame.side_buttons[1].setIcon(imageIcon2);
        frame.side_buttons[1].setBounds(5,90,60,60);
        frame.side_buttons[1].setBorder(null);
        frame.navi.add(frame.side_buttons[1]);

        ImageIcon imageIcon3 = new ImageIcon("piclib/delete.png");
        imageIcon3 = new ImageIcon(imageIcon3.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        frame.side_buttons[2] = new JButton(imageIcon3);
        frame.side_buttons[2].setBounds(5,165,60,60);
        frame.side_buttons[2].setBorder(null);
        frame.side_buttons[2].setActionCommand("删除");

        frame.navi.add(frame.side_buttons[2]);

        ImageIcon imageIcon4 = new ImageIcon("piclib/insert.png");
        imageIcon4 = new ImageIcon(imageIcon4.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        frame.side_buttons[3] = new JButton(imageIcon4);
        frame.side_buttons[3].setBounds(5,240,60,60);
        frame.side_buttons[3].setBorder(null);
        frame.side_buttons[3].setActionCommand("插入");

        frame.navi.add(frame.side_buttons[3]);

        ImageIcon imageIcon5 = new ImageIcon("piclib/analyze.png");
        imageIcon5 = new ImageIcon(imageIcon5.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        frame.analyze = new JButton(imageIcon5);
        frame.analyze.setBounds(5,315,60,60);
        frame.analyze.setBorder(null);
        frame.navi.add(frame.analyze);
        frame.analyze.setActionCommand("分析");
        frame.analyze.addActionListener(frame);
        frame.analyze.addMouseListener(frame);
        for(int i=0;i<frame.side_buttons.length;i++)
        {
            frame.side_buttons[i].addActionListener(frame);
            frame.side_buttons[i].addMouseListener(frame);
        }

    }

    public static void initsideButton_admin(MainFrame frame)
    {
        ImageIcon imageIcon1 = new ImageIcon("piclib/select.png");
        imageIcon1 = new ImageIcon(imageIcon1.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH));
        frame.side_buttons[0] = new JButton();
        frame.side_buttons[0].setActionCommand("查询");
        frame.side_buttons[0].setIcon(imageIcon1);
        frame.side_buttons[0].setBounds(5,15,60,60);
        frame.side_buttons[0].setBorder(null);
        frame.navi.add(frame.side_buttons[0]);

        ImageIcon imageIcon2 = new ImageIcon("piclib/update.png");
        imageIcon2 = new ImageIcon(imageIcon2.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH));
        frame.side_buttons[1] = new JButton();
        frame.side_buttons[1].setActionCommand("修改");
        frame.side_buttons[1].setIcon(imageIcon2);
        frame.side_buttons[1].setBounds(5,90,60,60);
        frame.side_buttons[1].setBorder(null);
        frame.navi.add(frame.side_buttons[1]);

        ImageIcon imageIcon3 = new ImageIcon("piclib/delete.png");
        imageIcon3 = new ImageIcon(imageIcon3.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        frame.side_buttons[2] = new JButton(imageIcon3);
        frame.side_buttons[2].setActionCommand("删除");
        frame.side_buttons[2].setBounds(5,165,60,60);
        frame.side_buttons[2].setBorder(null);
        frame.navi.add(frame.side_buttons[2]);

        ImageIcon imageIcon4 = new ImageIcon("piclib/insert.png");
        imageIcon4 = new ImageIcon(imageIcon4.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        frame.side_buttons[3] = new JButton(imageIcon4);
        frame.side_buttons[3].setActionCommand("插入");
        frame.side_buttons[3].setBounds(5,240,60,60);
        frame.side_buttons[3].setBorder(null);
        frame.navi.add(frame.side_buttons[3]);


        for(int i=0;i<frame.side_buttons.length;i++)
        {
            frame.side_buttons[i].addActionListener(frame);
            frame.side_buttons[i].addMouseListener(frame);
        }
    }
}
