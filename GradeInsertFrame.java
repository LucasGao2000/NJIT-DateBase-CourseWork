package gui;

import com.sun.tools.javac.Main;
import sql.MainController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class GradeInsertFrame extends JFrame implements ActionListener {
    private MainController controller;
    private JTable jTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JPanel content;
    private JLabel Sno;
    private JLabel Grade;
    private JPanel head;
    public JComboBox<String> classSelectbox;
    public JComboBox<String> deptSelectBox;
    public JComboBox<String> SnoSelectBox;
    private JLabel SnameLable;
    private JLabel name;
    private JComboBox<String> courseSelectbox;
    private JTextField gradeInsertfield;
    private JButton insertButton;


    public GradeInsertFrame(MainController controller) throws HeadlessException, SQLException {

        this.controller = controller;
        controller.connect("root","root");
        this.setLocation(600,300);
        setSize(800,700);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(null);
        initializeContent();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void initializeContent() throws SQLException {
        this.content = new JPanel();
        this.content.setLayout(null);
        this.content.setBounds(0,200,784,500);
        this.content.setBackground(Color.DARK_GRAY);
        this.content.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        this.getContentPane().add(content);
        initalizeHead();
        this.jTable = new JTable();
        jTable.getSelectionModel();
        /**
         * 表格内容居中显示
         */
        {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            jTable.setDefaultRenderer(Object.class, tcr);
        }
        JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
        jTable.setFont(new Font("Menu.font", Font.PLAIN, 14));
        jTable.setRowHeight(25);
        jTable.setGridColor(Color.WHITE);
        jTable.setShowGrid(false);
        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
        head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
        scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(0, 0, 784, 500);
        this.content.add(scrollPane);
    }

    private void initalizeHead() throws SQLException {
        this.head = new JPanel();
        this.head.setLayout(null);
        head.setBounds(0,0,800,200);
        this.head.setBackground(Color.DARK_GRAY);
        this.add(head);

        /**
         * 班级选择框
         */
        {
            classSelectbox = new JComboBox<>();
            classSelectbox.setActionCommand("班级");
            classSelectbox.addActionListener(this);
            classSelectbox.setForeground(Color.white);
            classSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            classSelectbox.setBounds(310, 100, 160, 40);
            classSelectbox.addItem("--班 级--");
            this.head.add(classSelectbox);
        }

        /**
         * 系部选择框
         */
        {
            deptSelectBox = new JComboBox<>();
            deptSelectBox.setActionCommand("系部");
            deptSelectBox.setForeground(Color.white);
            deptSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            deptSelectBox.setBounds(10, 100, 300, 40);
            deptSelectBox.addItem("-------系  部-------");
            deptSelectBox.addActionListener(this);
            ResultSet select_deptNo_from_dept = controller.selectQuery("select DeptName from dept");
            while (select_deptNo_from_dept.next()) {
                deptSelectBox.addItem(select_deptNo_from_dept.getString(1));
            }
            this.head.add(deptSelectBox);
        }
        SnoSelectBox= new JComboBox<>();
        SnoSelectBox.setActionCommand("学号");
        SnoSelectBox.setForeground(Color.white);
        SnoSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
        SnoSelectBox.setBounds(10, 50, 200, 40);
        SnoSelectBox.addItem("-----学号-----");
        SnoSelectBox.addActionListener(this);
        SnoSelectBox.setEditable(true);

        this.head.add(SnoSelectBox);

        SnameLable = new JLabel("姓名:");
        MainFrame.lableCustomize(SnameLable);
        SnameLable.setFont(new Font("宋体", Font.PLAIN, 24));
        SnameLable.setBounds(210,50,100,40);
        this.head.add(SnameLable);

        name = new JLabel();
        MainFrame.lableCustomize(name);
        name.setFont(new Font("宋体", Font.PLAIN, 24));
        name.setBorder(BorderFactory.createLineBorder(Color.gray));
        name.setBounds(310,50,100,40);
        this.head.add(name);

        {
            courseSelectbox = new JComboBox<>();
            courseSelectbox.addItem("--------课 程--------");
            courseSelectbox.setForeground(Color.white);
            courseSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            courseSelectbox.setBounds(420, 50, 300, 40);
            ResultSet select_cname_from_course = controller.selectQuery("select Cname from course");
            while (select_cname_from_course.next()) {
                courseSelectbox.addItem(select_cname_from_course.getString(1));
            }
            this.head.add(courseSelectbox);
        }

        JLabel label = new JLabel("成绩:");
        MainFrame.lableCustomize(label);
        label.setBounds(10,150,100,40);
        label.setFont(new Font("宋体", Font.PLAIN, 24));
        head.add(label);
        gradeInsertfield = new JTextField();
        gradeInsertfield.setForeground(Color.green);
        gradeInsertfield.setFont(new Font("宋体", Font.PLAIN, 24));
        gradeInsertfield.setBounds(100 ,150, 100, 40);
        head.add( gradeInsertfield);

        /**
         * 插入按钮
         */
        {
            insertButton = new JButton("插入成绩");
            insertButton.setBounds(600, 150, 130, 40);
            insertButton.setBackground(Color.DARK_GRAY);
            insertButton.setFont(new Font("宋体", Font.PLAIN, 24));
            insertButton.setBorder(null);
            insertButton.addActionListener(this);
            insertButton.setForeground(Color.white);
            insertButton.addMouseListener(new MouseListener() {
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
                    insertButton.setBackground(Color.gray);
                    insertButton.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    insertButton.setBackground(Color.darkGray);
                    insertButton.setForeground(Color.white);
                }
            });
            this.head.add(insertButton);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() instanceof JComboBox)
        {
            if (actionEvent.getActionCommand()=="系部")
            {
                System.out.println("系部");
                if (deptSelectBox.getSelectedItem()=="-------系  部-------")
                {
                    this.classSelectbox.removeAllItems();
                    this.classSelectbox.addItem("--班 级--");

                }
                else
                {
                    String s = deptSelectBox.getSelectedItem().toString();

//                    ResultSet resultSet = controller.selectQuery("select DeptNo from dept where DeptName='" + s + "'");
//                    try {
//                        resultSet.next();
//                        //this.classInsertfield[2].setText(resultSet.getString(1));
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
                    this.classSelectbox.removeAllItems();
                    this.classSelectbox.addItem("--班 级--");

                    ResultSet select_className_from_class = controller.selectQuery("select ClassName from class,dept where class.DeptNo = dept.DeptNo and DeptName='"+s+"'");
                    try{
                        while (select_className_from_class.next())
                        {
                            String string = select_className_from_class.getString(1);
                            classSelectbox.addItem(string);
                        }
                    }
                    catch (SQLException e)
                    {
                    }
                }
            }
            else if (actionEvent.getActionCommand()=="班级")
            {
                if(classSelectbox.getSelectedItem()=="--班 级--")
                {
                }else {
                    try {
                    String s = classSelectbox.getSelectedItem().toString();
                    System.out.println(s);
                    ResultSet resultSet = controller.selectQuery("select Sno from student where ClassNo=(select ClassNo from class where ClassName='" + s + "')");
                    SnoSelectBox.removeAllItems();
                    SnoSelectBox.addItem("-----学号-----");
                        while (resultSet.next()) {
                            String string = resultSet.getString(1);
                            System.out.println(string);;
                            SnoSelectBox.addItem(string);
                        }
                    }catch (Exception e)
                    {
                        //e.printStackTrace();
                    }
                }

            }
            else if(actionEvent.getActionCommand()=="学号")
            {
                try {
                    if (SnoSelectBox.getSelectedItem()!=null)
                    {
                        String s = SnoSelectBox.getSelectedItem().toString();
                        if (MainFrame.isNumeric(s))
                        {
                            ResultSet resultSet = controller.selectQuery("select Sname from student where Sno = '" + s + "'");
                            resultSet.next();
                            name.setText(resultSet.getString(1));
                            ResultSet resultSet1 = controller.selectQuery("select student.Sno,student.Sname,course.Cno,Cname,Ccredit,Grade " +
                                    "from student,sc,course " +
                                    "where student.Sno = sc.Sno and sc.Cno=course.Cno and student.Sno='"+s+"'");
                            if (resultSet1!=null)
                            {
                                showTable_info(resultSet1);
                            }

                        }
                    }
                }
                catch (Exception e)
                {

                }

            }
        }
        else if (actionEvent.getSource() instanceof JButton)
        {
            if (actionEvent.getActionCommand()=="插入成绩")
            {
                try {
                    insertGrade();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void insertGrade() throws SQLException {


        String Cname=(String) this.courseSelectbox.getSelectedItem();
        ResultSet resultSet = controller.selectQuery("select Cno from course where Cname='" + Cname + "'");
        resultSet.next();

        String Cno=resultSet.getString(1);
        String Sno=(String)SnoSelectBox.getSelectedItem().toString();
        String Grade = gradeInsertfield.getText().toString();
        controller.insert_grade(Sno,Cno,Grade);
        JOptionPane.showMessageDialog(this,"插入成功");
        showTable_info(controller.selectQuery("select student.Sno,student.Sname,course.Cno,Cname,Ccredit,Grade " +
                "from student,sc,course " +
                "where student.Sno = sc.Sno and sc.Cno=course.Cno and student.Sno='"+SnoSelectBox.getSelectedItem().toString()+"'"));
    }


    private void showTable_info(ResultSet resultSet) throws SQLException {
        jTable.removeAll();
        jTable.repaint();
        this.tableModel = new DefaultTableModel();
        System.out.println(resultSet==null);
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i=1;i<=metaData.getColumnCount();i++)
            {
                tableModel.addColumn(MainFrame.nameTrans(metaData.getColumnName(i)));
            }
            Object[] array = new Object[metaData.getColumnCount()];
            while(resultSet.next()) {
                array = new Object[metaData.getColumnCount()];
                for (int i=0;i<metaData.getColumnCount();i++)
                {
                    System.out.println(resultSet.getString(i+1));
                    array[i] = resultSet.getString(i+1);
                }

                tableModel.addRow(array);
            }
        }
        catch (NullPointerException e)
        {
        }
        finally {
            jTable.setModel(this.tableModel);
            scrollPane.repaint();
            scrollPane.revalidate();
        }
    }
}
