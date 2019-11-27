package gui;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import sql.MainController;
import sql.Sqlstatement;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;


public class MainFrame extends JFrame implements ActionListener, MouseListener, ListSelectionListener, TableModelListener {
    public JPanel navi,content;
    public MainController controller;
    public JTable jTable;
    public DefaultTableModel tableModel;
    public JScrollPane scrollPane = new JScrollPane();
    public JButton[] side_buttons = new JButton[4];
    public JButton analyze = new JButton();
    public int userType;
    public static final int USER_TYPE_STUDENT = 1;
    public static final int USER_TYPE_TEACHER = 2;
    public static final int USER_TYPE_ADMIN = 3;
    public JButton[] student_query = new JButton[3];
    public JButton Cpno_button;
    public static final int TABLE_TYPE_STUDENT = 1;
    public static final int TABLE_TYPE_SC = 2;
    public static final int TABLE_TYPE_CLASS = 3;
    public static final int TABLE_TYPE_COURSE = 4;

    public JLabel[] queryPanelLable = new JLabel[3];
    public JTextField[] queryPanelText = new JTextField[3];
    public JButton teacher_admin_query_button;
    public String[] SelectInfo = new String[6];;
    public String topNub;
    public JTextField topText;
    public JComboBox<String> orderSelectbox;
    public JRadioButton rb;
    public JComboBox<String> sexSelectBox;
    public JComboBox<String> classSelectbox;
    public JComboBox<String> deptSelectBox;
    public JButton Teacher_admin_query_grade_button;
    public JRadioButton passRb;
    public JComboBox<String> courseSelectbox;
    public JButton courseQuerybutton;
    public JButton classQuerybutton;
    public String editValue = "";
    public JButton delete_button;
    public int col;
    public int row;
    public String s="";
    public LinkedList<String> tableNamelist = new LinkedList<>();
    public JTextField ageTextfield;
    public JTextField[] courseInsertfield= new JTextField[3];
    private JTextField[] classInsertfield = new JTextField[3];
    private JTextField gradeInsertfield;

    private int usertype(String s)
    {
        switch (s)
        {
            case "student":return USER_TYPE_STUDENT;
            case "teacher":return USER_TYPE_TEACHER;
            case "admin":return USER_TYPE_ADMIN;
        }
        return 0;
    }


    public MainFrame(MainController controller,String type) throws HeadlessException, SQLException {
        super("学生成绩管理系统                                                             Made By :高境辰");
        userType=USER_TYPE_TEACHER;
        userType=usertype(type);
        this.controller = controller;
//        controller.connect("root","root");
        setBounds(0,20,1920,1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        initalizeNavi();
        initalizeContent();
        if (userType==USER_TYPE_STUDENT)
        {
            SideButton.initsideButton_student(this);
        }else if (userType==USER_TYPE_TEACHER)
        {
            SideButton.initsideButton_teacher(this);
        }else if (userType==USER_TYPE_ADMIN)
        {
            SideButton.initsideButton_teacher(this);
        }
        else {
            JOptionPane.showMessageDialog(this,"程序初始化失败");
        }
        //initsideButton_admin();
        //SideButton.initsideButton_teacher(this);
        //initsideButton_teacher();
        //initsideButton_student();
        showWelcomeInfo();
        this.setVisible(true);
        //showTable(controller.selectQuery("select * from student,sc,course where student.Sno = sc.Sno and sc.Cno = course.Cno"));
    }

    public static void lableCustomize(JLabel labels)
    {
        labels.setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 24));
        labels.setHorizontalAlignment(SwingConstants.CENTER);
        labels.setForeground(Color.CYAN);
        labels.setBackground(Color.DARK_GRAY);
    }

    private void initalizeNavi()
    {
        this.navi = new JPanel(null);
        this.navi.setLayout(null);
        this.navi.setBounds(0,0,70,1080);
        this.navi.setBackground(Color.gray);
        this.getContentPane().add(navi);
    }
    private void initalizeContent()
    {
        this.content = new JPanel();
        this.content.setLayout(null);
        this.content.setBounds(70,0,1850,1080);
        this.content.setBackground(Color.DARK_GRAY);
        this.getContentPane().add(content);
    }

    private void student_query_panel()
    {
        content.removeAll();
        content.repaint();
        student_query[0] = new JButton("个人信息查询");
        student_query[0].setBounds(300,300,300,120);
        student_query[0].setBackground(Color.DARK_GRAY);
        student_query[0].setBorder(null);
        student_query[0].setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 40));
        this.content.add(student_query[0]);
        student_query[1] = new JButton("考试成绩查询");
        student_query[1].setBounds(800,300,300,120);
        student_query[1].setBackground(Color.DARK_GRAY);
        student_query[1].setBorder(null);
        student_query[1].setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 40));
        this.content.add(student_query[1]);
        student_query[2] = new JButton("课程信息查询");
        student_query[2].setBounds(1300,300,300,120);
        student_query[2].setBackground(Color.DARK_GRAY);
        student_query[2].setBorder(null);
        student_query[2].setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 40));
        this.content.add(student_query[2]);
        JLabel[] labels = new JLabel[3];
        labels[0] = new JLabel("您的个人信息");
        labels[0].setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 27));
        labels[0].setHorizontalAlignment(SwingConstants.CENTER);
        labels[0].setBounds(300,500,300,90);
        labels[0].setForeground(Color.CYAN);
        labels[0].setBackground(Color.DARK_GRAY);
        this.content.add(labels[0]);
        labels[1] = new JLabel("您的考试成绩");
        labels[1].setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 27));
        labels[1].setHorizontalAlignment(SwingConstants.CENTER);
        labels[1].setBounds(800,500,300,90);
        labels[1].setForeground(Color.CYAN);
        labels[1].setBackground(Color.DARK_GRAY);
        this.content.add(labels[1]);
        labels[2] = new JLabel("您的课程信息");
        labels[2].setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 27));
        labels[2].setHorizontalAlignment(SwingConstants.CENTER);
        labels[2].setBounds(1300,500,300,90);
        labels[2].setForeground(Color.CYAN);
        labels[2].setBackground(Color.DARK_GRAY);
        this.content.add(labels[2]);
        for (int i=0;i<student_query.length;i++)
        {
            student_query[i].addActionListener(this);
        }
    }

    private void student_info_query_panel() throws SQLException {
        content.removeAll();
        content.repaint();
        //ResultSet set = controller.selectQuery(Sqlstatement.select("*", "student", controller.getUser()));
        ResultSet set = controller.selectQuery(Sqlstatement.select("*", "student","Sno=202131401"));
        JLabel labels;
        labels = new JLabel("学生个人信息:");
        labels.setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 27));
        labels.setHorizontalAlignment(SwingConstants.CENTER);
        labels.setBounds(300,100,300,90);
        labels.setForeground(Color.CYAN);
        labels.setBackground(Color.DARK_GRAY);
        this.content.add(labels);
        showTable_studentInfo(set);
        content.repaint();
    }

    private void student_grade_query_panel() throws SQLException {
        content.removeAll();
        content.repaint();
        //ResultSet set = controller.selectQuery(Sqlstatement.select("*", "student", controller.getUser()));
        ResultSet set = controller.selectQuery("select student.Sno,student.Sname,course.Cname,sc.Grade from sc,student,course where sc.Cno=course.Cno and sc.Sno = student.Sno and student.Sno='202131401'");
        JLabel labels;
        labels = new JLabel("您的考试成绩为:");
        labels.setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 27));
        labels.setHorizontalAlignment(SwingConstants.CENTER);
        labels.setBounds(300,100,300,90);
        labels.setForeground(Color.CYAN);
        labels.setBackground(Color.DARK_GRAY);
        this.content.add(labels);
        showTable_studentGrade(set);         //每个类型的查询单独写一个建表语句,这个仅为测试
        content.repaint();
    }

    /**
     * 需要加入先修课信息
     * @throws SQLException
     */
    private void student_course_query_panel_whole() throws SQLException
    {
        content.removeAll();
        content.repaint();
        //ResultSet set = controller.selectQuery(Sqlstatement.select("*", "student", controller.getUser()));
        String sql = "select course.Cno,course.Cname,course.Ccredit,course.Cpno,A.Cname\n" +
                "from education_215180311.course left join course as A on (A.Cno = course.Cpno) ,sc,student\n" +
                "where sc.Sno = student.Sno and sc.Cno = course.Cno and sc.Sno = '202131401'";
        ResultSet set = controller.selectQuery(sql);
        JLabel labels;
        labels = new JLabel("您的课程信息为:");
        labels.setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 27));
        labels.setHorizontalAlignment(SwingConstants.CENTER);
        labels.setBounds(300,100,300,90);
        labels.setForeground(Color.CYAN);
        labels.setBackground(Color.DARK_GRAY);
        this.content.add(labels);
        this.Cpno_button = new JButton("查看先修课信息");
        this.Cpno_button.setBounds(1100,100,300,100);
        Cpno_button.addActionListener(this);
        Cpno_button.setBorder(null);
        Cpno_button.setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 20));
        Cpno_button.setBackground(Color.DARK_GRAY);

        this.content.add(Cpno_button);




        showTable_studentCourse_whole(set);         //每个类型的查询单独写一个建表语句,这个仅为测试
        content.repaint();
    }

    private void student_course_query_panel_Pre() throws SQLException
    {
        content.removeAll();
        content.repaint();
        //ResultSet set = controller.selectQuery(Sqlstatement.select("*", "student", controller.getUser()));
        String sql = "select course.Cno,course.Cname,course.Ccredit,course.Cpno,A.Cname\n" +
                "from education_215180311.course left join course as A on (A.Cno = course.Cpno) ,sc,student\n" +
                "where sc.Cno in (select course.Cpno\n" +
                "from education_215180311.course,sc,student\n" +
                "where sc.Sno = student.Sno and sc.Cno = course.Cno and sc.Sno = '202131401')\n" +
                "and sc.Sno = student.Sno and sc.Cno = course.Cno and sc.Sno = '202131401';";

        ResultSet set = controller.selectQuery(sql);
        JLabel labels;
        labels = new JLabel("您的先修课程信息为:");
        labels.setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 27));
        labels.setHorizontalAlignment(SwingConstants.CENTER);
        labels.setBounds(300,100,300,90);
        labels.setForeground(Color.CYAN);
        labels.setBackground(Color.DARK_GRAY);
        this.content.add(labels);
        this.Cpno_button = new JButton("返 回");
        this.Cpno_button.setBounds(1100,100,300,100);
        Cpno_button.addActionListener(this);
        Cpno_button.setBorder(null);
        Cpno_button.setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 20));
        Cpno_button.setBackground(Color.DARK_GRAY);
        this.content.add(Cpno_button);
        showTable_studentCourse_whole(set);         //每个类型的查询单独写一个建表语句,这个仅为测试
        content.repaint();
    }
    private void student_info_update_panel() throws SQLException {
    }  //未写完

    private void showTable(ResultSet resultSet) throws SQLException {
        this.tableModel = new DefaultTableModel();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for(int i=1;i<=metaData.getColumnCount();i++)
        {
            System.out.println(metaData.getColumnName(i));
            tableModel.addColumn(metaData.getColumnName(i));
        }
        Object[] array = new Object[metaData.getColumnCount()];
        this.jTable = new JTable(tableModel);
        //this.content.add(jTable);
        //scrollPane.setBorder(null);
        JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
        head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
        scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(125,300,1600,600);
        content.add(scrollPane);
        /**
         * 表格内容居中显示
         */
        {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
           // tcr.setSize(40,35);
            tcr.setHorizontalAlignment(SwingConstants.CENTER);

            jTable.setDefaultRenderer(Object.class, tcr);
        }
        jTable.setFont(new Font("Menu.font", Font.PLAIN, 20));
        jTable.setRowHeight(25);
        jTable.setGridColor(Color.WHITE);
        jTable.setShowGrid(false);
        jTable.setBounds(60,223,1000,500);

        this.content.validate();
        while(resultSet.next()) {
            array = new Object[metaData.getColumnCount()];
            for (int i=0;i<metaData.getColumnCount();i++)
            {
                array[i] = resultSet.getString(i+1);
            }
            tableModel.addRow(array);
            System.out.print(resultSet.getString(1) + "---");
            System.out.print(resultSet.getString(2) + "---");
            System.out.println(resultSet.getString(3));
        }


    }

    private void student_analyze_panel() throws SQLException {
        content.removeAll();
        content.repaint();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ResultSet set = controller.selectQuery("select course.Cname,sc.Grade " +
                "from sc,student,course " +
                "where sc.Cno=course.Cno and sc.Sno = student.Sno and student.Sno='202131401'");
        ArrayList<Double> list = new ArrayList<Double>();
        while (set.next()){
            dataset.addValue(Double.parseDouble(set.getString(2)),set.getString(1),set.getString(1));
            list.add(set.getDouble(2));
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "成绩对比表", // 图表标题
                "科目", // 目录轴的显示标签
                "分数", // 数值轴的显示标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                true,           // 是否显示图例(对于简单的柱状图必须是false)
                false,          // 是否生成工具
                false           // 是否生成URL链接
        );
        chart.setBackgroundPaint(Color.gray);
        chart.setBorderVisible(false);
        CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象
        CategoryAxis domainAxis=plot.getDomainAxis();         //水平底部列表
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,16));         //水平底部标题
        domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,14));  //垂直标题
        ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,18));
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 18));
        chart.getLegend().setBackgroundPaint(Color.gray);
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
        plot.setBackgroundPaint(Color.lightGray);
        ChartPanel frame1 = new ChartPanel(chart,true);
        frame1.setBounds(100,300,800,600);
        this.content.add(frame1);
        JLabel j1 = new JLabel("您的总成绩为:");
        lableCustomize(j1);
        j1.setBounds(100,80,180,80);
        content.add(j1);
        double temp=0;
        for(int i=0;i<list.size();i++)
        {
            temp+=list.get(i);
        }
        System.out.println(temp);
        JLabel j2 = new JLabel(Double.toString(temp));
        lableCustomize(j2);
        j2.setForeground(Color.green);
        j2.setBounds(300,80,100,80);
        content.add(j2);

        JLabel j3 = new JLabel("您的平均成绩为:");
        lableCustomize(j3);
        j3.setBounds(420,80,180,80);
        content.add(j3);
        String s = String.format("%.1f",temp / list.size());
        System.out.println(s);
        JLabel j4 = new JLabel(s);
        lableCustomize(j4);
        j4.setForeground(Color.green);
        j4.setBounds(610,80,100,80);
        content.add(j4);

        JLabel j5 = new JLabel("奖学金等级:");
        lableCustomize(j5);
        j5.setBounds(820,80,180,80);
        content.add(j5);

        JLabel j6 = new JLabel("班级排名：");
        lableCustomize(j6);
        j6.setBounds(1000,200,280,80);
        content.add(j6);
        showTable_studentRank(null);
    }  //未写完

    /**
     * 欢迎信息
     */
    private void showWelcomeInfo()
    {
        JLabel welcome = new JLabel("学生成绩管理系统");
        welcome.setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 32));
        welcome.setForeground(Color.WHITE);
        welcome.setBounds(250,500,300,70);
        content.add(welcome);
        JLabel explaination =new JLabel("欢迎使用本系统,本系统可以查询，更新您的数据。");
        explaination.setFont(new Font("Adobe 宋体 Std L", Font.PLAIN, 19));
        explaination.setBounds(250,570,600,50);
        this.content.add(explaination);

        JLabel welcome_en = new JLabel("Student Achievement Management System");
        welcome_en.setFont(new Font("Kozuka Gothic Pro EL", Font.PLAIN, 32));
        welcome_en.setForeground(Color.WHITE);
        welcome_en.setBounds(250,200,700,70);
        content.add(welcome_en);

        JLabel explaination_en = new JLabel("Greetings,welcome to use this system.\nIn this system.You can query information.Update your data.");
        explaination_en.setFont(new Font("Kozuka Gothic Pro EL", Font.PLAIN, 19));
        explaination_en.setBounds(250,270,800,50);
        this.content.add(explaination_en);
    }

    /**
     * 教师成绩查询面板
     * @throws SQLException
     */
    private void teacher_admin_query_grade_pane() throws SQLException {
        content.removeAll();
        content.repaint();
        /**
         * 学号，姓名，班级号提示
         */
        {
            queryPanelLable[0] = new JLabel("学号:");
            queryPanelLable[0].setBounds(100, 80, 100, 80);
            lableCustomize(queryPanelLable[0]);
            content.add(queryPanelLable[0]);
            queryPanelLable[1] = new JLabel("姓名:");
            queryPanelLable[1].setBounds(400, 80, 100, 80);
            lableCustomize(queryPanelLable[1]);
            content.add(queryPanelLable[1]);
            queryPanelLable[2] = new JLabel("班级号:");
            queryPanelLable[2].setBounds(700, 80, 100, 80);
            lableCustomize(queryPanelLable[2]);
            content.add(queryPanelLable[2]);
        }
        /**
         * 学号
         * 姓名
         * 班级
         * 输入框体
         */
        {
            queryPanelText[0] = new JTextField();
            queryPanelText[0].setBounds(200, 93, 200, 30);
            queryPanelText[0].setForeground(Color.green);
            content.add(queryPanelText[0]);
            queryPanelText[1] = new JTextField();
            queryPanelText[1].setBounds(500, 93, 200, 30);
            queryPanelText[1].setForeground(Color.green);
            content.add(queryPanelText[1]);
            queryPanelText[2] = new JTextField();
            queryPanelText[2].setBounds(800, 93, 200, 30);
            queryPanelText[2].setForeground(Color.green);
            content.add(queryPanelText[2]);
        }
        /**
         * 表格初始化
         */
        {
            this.jTable = new JTable();
            jTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
                if (!listSelectionEvent.getValueIsAdjusting()) {
                    //支持拖动多选
                    int[] rows = jTable.getSelectedRows();
                    // int[] cols = table.getSelectedColumns();//选中的列
                    for (int i : rows) {
                        System.out.println("#方法一:\t" + jTable.getValueAt(i, 0) + "\t" + jTable.getValueAt(i, 1));
                    }
                }
            });
            JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
            head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
            head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
            scrollPane = new JScrollPane(jTable);
            scrollPane.setBounds(125, 300, 1600, 600);
            this.content.add(scrollPane);
        }

        /**
         * 查询按钮
         */
        {
            teacher_admin_query_button = new JButton("查 询");
            teacher_admin_query_button.setActionCommand("成绩 查询");
            teacher_admin_query_button.setBounds(1400, 90, 100, 60);
            teacher_admin_query_button.setBackground(Color.DARK_GRAY);
            teacher_admin_query_button.setFont(new Font("宋体", Font.PLAIN, 30));
            teacher_admin_query_button.setBorder(null);
            teacher_admin_query_button.addActionListener(this);
            teacher_admin_query_button.setForeground(Color.white);
            teacher_admin_query_button.addMouseListener(new MouseListener() {
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
                    teacher_admin_query_button.setBackground(Color.gray);
                    teacher_admin_query_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    teacher_admin_query_button.setBackground(Color.darkGray);
                    teacher_admin_query_button.setForeground(Color.white);
                }
            });
            this.content.add(teacher_admin_query_button);
        }
        /**
         * 排序方式选择框
         */
        {
            orderSelectbox = new JComboBox<>();
            orderSelectbox.setBounds(1200, 93, 150, 40);
            orderSelectbox.setForeground(Color.white);
            orderSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            orderSelectbox.addItem("升序");
            orderSelectbox.addItem("降序");
            orderSelectbox.addItem("成绩升序");
            orderSelectbox.addItem("成绩降序");
            orderSelectbox.addItem("姓名升序");
            orderSelectbox.addItem("姓名降序");
            orderSelectbox.addItem("学号升序");
            orderSelectbox.addItem("学号降序");
            orderSelectbox.addItem("班级升序");
            orderSelectbox.addItem("班级降序");
            JLabel label = new JLabel("排序方式:");
            lableCustomize(label);
            label.setBounds(1050, 90, 120, 80);
            this.content.add(label);
            this.content.add(orderSelectbox);
        }
        /**
         * 排名选择框
         */
        {
            JLabel label1 = new JLabel("查看前");
            JLabel label2 = new JLabel("名");
            lableCustomize(label1);
            label1.setBounds(200, 200, 80, 50);
            this.content.add(label1);
            lableCustomize(label2);
            label2.setBounds(350, 200, 40, 50);
            this.content.add(label2);
            topText = new JTextField();
            topText.setForeground(Color.green);
            topText.setBounds(290, 200, 50, 30);
            content.add(topText);
        }

        /**
         * 奖学金选择框
         */
        {
            rb = new JRadioButton("奖学金");
            rb.addActionListener(this);
            rb.setFont(new Font("宋体", Font.PLAIN, 23));
            rb.setBounds(400, 190, 150, 50);
            content.add(rb);
        }
        /**
         * 班级选择框
         */
        {
            classSelectbox = new JComboBox<>();
            classSelectbox.setForeground(Color.white);
            classSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            classSelectbox.setBounds(560, 200, 160, 40);
            classSelectbox.addItem("--班 级--");
            this.content.add(classSelectbox);
        }

        /**
         * 系部选择框
         */
        {
            deptSelectBox = new JComboBox<>();
            deptSelectBox.setForeground(Color.white);
            deptSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            deptSelectBox.setBounds(800, 200, 300, 40);
            deptSelectBox.addItem("-------系  部-------");
            deptSelectBox.addActionListener(this);
            ResultSet select_deptNo_from_dept = controller.selectQuery("select DeptName from dept");
            while (select_deptNo_from_dept.next()) {
                deptSelectBox.addItem(select_deptNo_from_dept.getString(1));
            }
            this.content.add(deptSelectBox);
        }
        /**
         * 及格选择
         */
        {
            passRb = new JRadioButton("不及格");
            passRb.addActionListener(this);
            passRb.setFont(new Font("宋体", Font.PLAIN, 23));
            passRb.setBounds(1130, 190, 140, 50);
            content.add(passRb);
        }
        /**
         * 课程信息选择框
         */
        {
            courseSelectbox = new JComboBox<>();
            courseSelectbox.addItem("--------课 程--------");
            courseSelectbox.setForeground(Color.white);
            courseSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            courseSelectbox.setBounds(1270, 200, 300, 40);
            ResultSet select_cname_from_course = controller.selectQuery("select Cname from course");
            while (select_cname_from_course.next()) {
                courseSelectbox.addItem(select_cname_from_course.getString(1));
            }
            this.content.add(courseSelectbox);
        }
    }
    /**
     * 教师基本信息查询面板
     * @throws SQLException
     */
    private void teacher_admin_query_panel() throws SQLException {
            content.removeAll();
            content.repaint();

        /**
         * 学号，姓名，班级
         * 提示文字
         */
        {
            queryPanelLable[0] = new JLabel("学号:");
            queryPanelLable[0].setBounds(100, 80, 100, 80);
            lableCustomize(queryPanelLable[0]);
            content.add(queryPanelLable[0]);
            queryPanelLable[1] = new JLabel("姓名:");
            queryPanelLable[1].setBounds(400, 80, 100, 80);
            lableCustomize(queryPanelLable[1]);
            content.add(queryPanelLable[1]);
            queryPanelLable[2] = new JLabel("班级号:");
            queryPanelLable[2].setBounds(700, 80, 100, 80);
            lableCustomize(queryPanelLable[2]);
            content.add(queryPanelLable[2]);
        }
        /**
         * 学号
         * 姓名
         * 班级
         * 输入框体
         */
        {
            queryPanelText[0] = new JTextField();
            queryPanelText[0].setBounds(200, 93, 200, 30);
            queryPanelText[0].setForeground(Color.green);
            content.add(queryPanelText[0]);
            queryPanelText[1] = new JTextField();
            queryPanelText[1].setBounds(500, 93, 200, 30);
            queryPanelText[1].setForeground(Color.green);
            content.add(queryPanelText[1]);
            queryPanelText[2] = new JTextField();
            queryPanelText[2].setBounds(800, 93, 200, 30);
            queryPanelText[2].setForeground(Color.green);
            content.add(queryPanelText[2]);
        }

        /**
         * 查询按钮
         */
        {
            teacher_admin_query_button = new JButton("查 询");
            teacher_admin_query_button.setBounds(1400, 90, 100, 60);
            teacher_admin_query_button.setBackground(Color.DARK_GRAY);
            teacher_admin_query_button.setFont(new Font("宋体", Font.PLAIN, 30));
            teacher_admin_query_button.setBorder(null);
            teacher_admin_query_button.addActionListener(this);
            teacher_admin_query_button.setForeground(Color.white);
            teacher_admin_query_button.addMouseListener(new MouseListener() {
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
                    teacher_admin_query_button.setBackground(Color.gray);
                    teacher_admin_query_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    teacher_admin_query_button.setBackground(Color.darkGray);
                    teacher_admin_query_button.setForeground(Color.white);
                }
            });
            this.content.add(teacher_admin_query_button);
        }
        /**
         * 成绩查询页面跳转按钮
         */
        {
            Teacher_admin_query_grade_button = new JButton("成绩查询");
            Teacher_admin_query_grade_button.setBounds(1400, 190, 150, 60);
            Teacher_admin_query_grade_button.setBackground(Color.DARK_GRAY);
            Teacher_admin_query_grade_button.setFont(new Font("宋体", Font.PLAIN, 30));
            Teacher_admin_query_grade_button.setBorder(null);
            Teacher_admin_query_grade_button.addActionListener(this);
            Teacher_admin_query_grade_button.setForeground(Color.white);
            Teacher_admin_query_grade_button.addMouseListener(new MouseListener() {
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
                    Teacher_admin_query_grade_button.setBackground(Color.gray);
                    Teacher_admin_query_grade_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    Teacher_admin_query_grade_button.setBackground(Color.darkGray);
                    Teacher_admin_query_grade_button.setForeground(Color.white);
                }
            });
            this.content.add(Teacher_admin_query_grade_button);
        }

        /**
         * 课程查询按钮
         */
        {
            courseQuerybutton = new JButton("课程查询");
            courseQuerybutton.setBounds(1600, 190, 150, 60);
            courseQuerybutton.setBackground(Color.DARK_GRAY);
            courseQuerybutton.setFont(new Font("宋体", Font.PLAIN, 30));
            courseQuerybutton.setBorder(null);
            courseQuerybutton.addActionListener(this);
            courseQuerybutton.setForeground(Color.white);
            courseQuerybutton.addMouseListener(new MouseListener() {
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
                    courseQuerybutton.setBackground(Color.gray);
                    courseQuerybutton.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    courseQuerybutton.setBackground(Color.darkGray);
                    courseQuerybutton.setForeground(Color.white);
                }
            });
            this.content.add(courseQuerybutton);
        }

        /**
         * 班级查询按钮
         */
        {
            classQuerybutton = new JButton("班级查询");
            classQuerybutton.setBounds(1600, 90, 150, 60);
            classQuerybutton.setBackground(Color.DARK_GRAY);
            classQuerybutton.setFont(new Font("宋体", Font.PLAIN, 30));
            classQuerybutton.setBorder(null);
            classQuerybutton.addActionListener(this);
            classQuerybutton.setForeground(Color.white);
            classQuerybutton.addMouseListener(new MouseListener() {
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
                    classQuerybutton.setBackground(Color.gray);
                    classQuerybutton.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    classQuerybutton.setBackground(Color.darkGray);
                    classQuerybutton.setForeground(Color.white);
                }
            });
            this.content.add(classQuerybutton);
        }

        /**
         * 空表格初始化
         */
        {
            this.jTable = new JTable();
            jTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
                if (!listSelectionEvent.getValueIsAdjusting()) {
                    //支持拖动多选
                    int[] rows = jTable.getSelectedRows();
                    // int[] cols = table.getSelectedColumns();//选中的列
                    for (int i : rows) {
                        System.out.println("#方法一:\t" + jTable.getValueAt(i, 0) + "\t" + jTable.getValueAt(i, 1));
                    }
                }
            });
            JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
            head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
            head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
            scrollPane = new JScrollPane(jTable);
            scrollPane.setBounds(125, 300, 1600, 600);
            content.add(scrollPane);
        }
        /**
         * 排序方式选择框
         */
        {
            orderSelectbox = new JComboBox<>();
            orderSelectbox.setBounds(1200, 93, 150, 40);
            orderSelectbox.setForeground(Color.white);
            orderSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            orderSelectbox.addItem("升序");
            orderSelectbox.addItem("降序");
            orderSelectbox.addItem("姓名升序");
            orderSelectbox.addItem("姓名降序");
            orderSelectbox.addItem("学号升序");
            orderSelectbox.addItem("学号降序");
            orderSelectbox.addItem("班级升序");
            orderSelectbox.addItem("班级降序");
            JLabel label = new JLabel("排序方式:");
            lableCustomize(label);
            label.setBounds(1050, 90, 120, 80);
            this.content.add(label);
            this.content.add(orderSelectbox);
        }
        /**
         * 排名选择框
         */
        {
            JLabel label1 = new JLabel("查看前");
            JLabel label2 = new JLabel("人");
            lableCustomize(label1);
            label1.setBounds(200, 200, 80, 50);
            this.content.add(label1);
            lableCustomize(label2);
            label2.setBounds(350, 200, 40, 50);
            this.content.add(label2);
            topText = new JTextField();
            topText.setForeground(Color.green);
            topText.setBounds(290, 200, 50, 30);
            content.add(topText);
        }
        /**
         * 奖学金选择框
         */
        {
            rb = new JRadioButton("奖学金");
            rb.addActionListener(this);
            rb.setFont(new Font("宋体", Font.PLAIN, 23));
            rb.setBounds(400, 190, 150, 50);
            // content.add(rb);
        }

        /**
         * 性别选择框
         */
        {
            sexSelectBox = new JComboBox<>();
            sexSelectBox.addItem("性别");
            sexSelectBox.addItem("男");
            sexSelectBox.addItem("女");
            sexSelectBox.setForeground(Color.white);
            sexSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            sexSelectBox.setBounds(1200, 200, 100, 40);
            this.content.add(sexSelectBox);
        }
        /**
         * 班级选择框
         */
        {
            classSelectbox = new JComboBox<>();
            classSelectbox.setForeground(Color.white);
            classSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            classSelectbox.setBounds(560, 200, 160, 40);
            classSelectbox.addItem("--班 级--");
//        ResultSet select_className_from_class = controller.selectQuery("select ClassName from class");
//        while (select_className_from_class.next())
//        {
//            String string = select_className_from_class.getString(1);
//            classSelectbox.addItem(string);
//        }
            this.content.add(classSelectbox);
        }
        /**
         * 系部选择框
         */
        {
            deptSelectBox = new JComboBox<>();
            deptSelectBox.setForeground(Color.white);
            deptSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            deptSelectBox.setBounds(800, 200, 300, 40);
            deptSelectBox.addItem("-------系  部-------");
            deptSelectBox.addActionListener(this);
            ResultSet select_deptNo_from_dept = controller.selectQuery("select DeptName from dept");
            while (select_deptNo_from_dept.next()) {
                deptSelectBox.addItem(select_deptNo_from_dept.getString(1));
            }
            this.content.add(deptSelectBox);
        }
    }

    private void teacher_admin_query_class_panel() throws SQLException {
        content.removeAll();
        content.repaint();
        /**
         * 学号，姓名，班级
         * 提示文字
         */
        {
            queryPanelLable[0] = new JLabel("学号:");
            queryPanelLable[0].setBounds(100, 80, 100, 80);
            lableCustomize(queryPanelLable[0]);
            content.add(queryPanelLable[0]);
            queryPanelLable[1] = new JLabel("姓名:");
            queryPanelLable[1].setBounds(400, 80, 100, 80);
            lableCustomize(queryPanelLable[1]);
            content.add(queryPanelLable[1]);
            queryPanelLable[2] = new JLabel("班级号:");
            queryPanelLable[2].setBounds(700, 80, 100, 80);
            lableCustomize(queryPanelLable[2]);
            content.add(queryPanelLable[2]);
        }
        /**
         * 学号
         * 姓名
         * 班级
         * 输入框体
         */
        {
            queryPanelText[0] = new JTextField();
            queryPanelText[0].setBounds(200, 93, 200, 30);
            queryPanelText[0].setForeground(Color.green);
            content.add(queryPanelText[0]);
            queryPanelText[1] = new JTextField();
            queryPanelText[1].setBounds(500, 93, 200, 30);
            queryPanelText[1].setForeground(Color.green);
            content.add(queryPanelText[1]);
            queryPanelText[2] = new JTextField();
            queryPanelText[2].setBounds(800, 93, 200, 30);
            queryPanelText[2].setForeground(Color.green);
            content.add(queryPanelText[2]);
        }
        /**
         * 空表格初始化
         */
        {
            this.jTable = new JTable();
            jTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
                if (!listSelectionEvent.getValueIsAdjusting()) {
                    //支持拖动多选
                    int[] rows = jTable.getSelectedRows();
                    // int[] cols = table.getSelectedColumns();//选中的列
                    for (int i : rows) {
                        System.out.println("#方法一:\t" + jTable.getValueAt(i, 0) + "\t" + jTable.getValueAt(i, 1));
                    }
                }
            });
            JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
            head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
            head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
            scrollPane = new JScrollPane(jTable);
            scrollPane.setBounds(125, 300, 1600, 600);
            content.add(scrollPane);
        }
        /**
         * 查询按钮
         */
        {
            teacher_admin_query_button = new JButton("查 询");
            teacher_admin_query_button.setActionCommand("班级 查询");
            teacher_admin_query_button.setBounds(1400, 90, 100, 60);
            teacher_admin_query_button.setBackground(Color.DARK_GRAY);
            teacher_admin_query_button.setFont(new Font("宋体", Font.PLAIN, 30));
            teacher_admin_query_button.setBorder(null);
            teacher_admin_query_button.addActionListener(this);
            teacher_admin_query_button.setForeground(Color.white);
            teacher_admin_query_button.addMouseListener(new MouseListener() {
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
                    teacher_admin_query_button.setBackground(Color.gray);
                    teacher_admin_query_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    teacher_admin_query_button.setBackground(Color.darkGray);
                    teacher_admin_query_button.setForeground(Color.white);
                }
            });
            this.content.add(teacher_admin_query_button);
        }
        /**
         * 排序方式选择框
         */
        {
            orderSelectbox = new JComboBox<>();
            orderSelectbox.setBounds(1200, 93, 150, 40);
            orderSelectbox.setForeground(Color.white);
            orderSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            orderSelectbox.addItem("升序");
            orderSelectbox.addItem("降序");
            orderSelectbox.addItem("姓名升序");
            orderSelectbox.addItem("姓名降序");
            orderSelectbox.addItem("学号升序");
            orderSelectbox.addItem("学号降序");
            orderSelectbox.addItem("班级升序");
            orderSelectbox.addItem("班级降序");
            JLabel label = new JLabel("排序方式:");
            lableCustomize(label);
            label.setBounds(1050, 90, 120, 80);
            this.content.add(label);
            this.content.add(orderSelectbox);
        }
        /**
         * 班级选择框
         */
        {
            classSelectbox = new JComboBox<>();
            classSelectbox.setForeground(Color.white);
            classSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            classSelectbox.setBounds(560, 200, 160, 40);
            classSelectbox.addItem("--班 级--");
//        ResultSet select_className_from_class = controller.selectQuery("select ClassName from class");
//        while (select_className_from_class.next())
//        {
//            String string = select_className_from_class.getString(1);
//            classSelectbox.addItem(string);
//        }
            this.content.add(classSelectbox);
        }
        /**
         * 系部选择框
         */
        {
            deptSelectBox = new JComboBox<>();
            deptSelectBox.setForeground(Color.white);
            deptSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            deptSelectBox.setBounds(800, 200, 300, 40);
            deptSelectBox.addItem("-------系  部-------");
            deptSelectBox.addActionListener(this);
            ResultSet select_deptNo_from_dept = controller.selectQuery("select DeptName from dept");
            while (select_deptNo_from_dept.next()) {
                deptSelectBox.addItem(select_deptNo_from_dept.getString(1));
            }
            this.content.add(deptSelectBox);
        }

    }

    private void teacher_admin_query_course_panel() throws SQLException {
        content.removeAll();
        content.repaint();
        /**
         * 学号，姓名，班级
         * 提示文字
         */
        {
            queryPanelLable[0] = new JLabel("学号:");
            queryPanelLable[0].setBounds(100, 80, 100, 80);
            lableCustomize(queryPanelLable[0]);
            content.add(queryPanelLable[0]);
            queryPanelLable[1] = new JLabel("姓名:");
            queryPanelLable[1].setBounds(400, 80, 100, 80);
            lableCustomize(queryPanelLable[1]);
            content.add(queryPanelLable[1]);
            queryPanelLable[2] = new JLabel("班级号:");
            queryPanelLable[2].setBounds(700, 80, 100, 80);
            lableCustomize(queryPanelLable[2]);
            content.add(queryPanelLable[2]);
        }
        /**
         * 学号
         * 姓名
         * 班级
         * 输入框体
         */
        {
            queryPanelText[0] = new JTextField();
            queryPanelText[0].setBounds(200, 93, 200, 30);
            queryPanelText[0].setForeground(Color.green);
            content.add(queryPanelText[0]);
            queryPanelText[1] = new JTextField();
            queryPanelText[1].setBounds(500, 93, 200, 30);
            queryPanelText[1].setForeground(Color.green);
            content.add(queryPanelText[1]);
            queryPanelText[2] = new JTextField();
            queryPanelText[2].setBounds(800, 93, 200, 30);
            queryPanelText[2].setForeground(Color.green);
            content.add(queryPanelText[2]);
        }
        /**
         * 空表格初始化
         */
        {
            this.jTable = new JTable();
            jTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
                if (!listSelectionEvent.getValueIsAdjusting()) {
                    //支持拖动多选
                    int[] rows = jTable.getSelectedRows();
                    // int[] cols = table.getSelectedColumns();//选中的列
                    for (int i : rows) {
                        System.out.println("#方法一:\t" + jTable.getValueAt(i, 0) + "\t" + jTable.getValueAt(i, 1));
                    }
                }
            });
            JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
            head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
            head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
            scrollPane = new JScrollPane(jTable);
            scrollPane.setBounds(125, 300, 1600, 600);
            content.add(scrollPane);
        }
        /**
         * 查询按钮
         */
        {
            teacher_admin_query_button = new JButton("查 询");
            teacher_admin_query_button.setActionCommand("课程 查询");
            teacher_admin_query_button.setBounds(1400, 90, 100, 60);
            teacher_admin_query_button.setBackground(Color.DARK_GRAY);
            teacher_admin_query_button.setFont(new Font("宋体", Font.PLAIN, 30));
            teacher_admin_query_button.setBorder(null);
            teacher_admin_query_button.addActionListener(this);
            teacher_admin_query_button.setForeground(Color.white);
            teacher_admin_query_button.addMouseListener(new MouseListener() {
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
                    teacher_admin_query_button.setBackground(Color.gray);
                    teacher_admin_query_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    teacher_admin_query_button.setBackground(Color.darkGray);
                    teacher_admin_query_button.setForeground(Color.white);
                }
            });
            this.content.add(teacher_admin_query_button);
        }
        /**
         * 课程信息选择框
         */
        {
            courseSelectbox = new JComboBox<>();
            courseSelectbox.addItem("--------课 程--------");
            courseSelectbox.setForeground(Color.white);
            courseSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            courseSelectbox.setBounds(300, 200, 300, 40);
            ResultSet select_cname_from_course = controller.selectQuery("select Cname from course");
            while (select_cname_from_course.next()) {
                courseSelectbox.addItem(select_cname_from_course.getString(1));
            }
            this.content.add(courseSelectbox);
        }

    }

    /**
     * 老师更新面板
     * @throws SQLException
     */
    private void teacher_admin_update_panel() throws SQLException {
        content.removeAll();
        content.repaint();

        /**
         * 学号，姓名，班级
         * 提示文字
         */
        {
            queryPanelLable[0] = new JLabel("学号:");
            queryPanelLable[0].setBounds(100, 130, 100, 80);
            lableCustomize(queryPanelLable[0]);
            content.add(queryPanelLable[0]);
            queryPanelLable[1] = new JLabel("姓名:");
            queryPanelLable[1].setBounds(400, 130, 100, 80);
            lableCustomize(queryPanelLable[1]);
            content.add(queryPanelLable[1]);
            queryPanelLable[2] = new JLabel("班级号:");
            queryPanelLable[2].setBounds(700, 130, 100, 80);
            lableCustomize(queryPanelLable[2]);
            content.add(queryPanelLable[2]);
        }
        /**
         * 学号
         * 姓名
         * 班级
         * 输入框体
         */
        {
            queryPanelText[0] = new JTextField();
            queryPanelText[0].setBounds(200, 143, 200, 30);
            queryPanelText[0].setForeground(Color.green);
            content.add(queryPanelText[0]);
            queryPanelText[1] = new JTextField();
            queryPanelText[1].setBounds(500, 143, 200, 30);
            queryPanelText[1].setForeground(Color.green);
            content.add(queryPanelText[1]);
            queryPanelText[2] = new JTextField();
            queryPanelText[2].setBounds(800, 143, 200, 30);
            queryPanelText[2].setForeground(Color.green);
            content.add(queryPanelText[2]);
        }

        /**
         * 查询按钮
         */
        {
            teacher_admin_query_button = new JButton("查 询");
            teacher_admin_query_button.setBounds(100, 60, 100, 60);
            teacher_admin_query_button.setBackground(Color.DARK_GRAY);
            teacher_admin_query_button.setFont(new Font("宋体", Font.PLAIN, 30));
            teacher_admin_query_button.setBorder(null);
            teacher_admin_query_button.addActionListener(this);
            teacher_admin_query_button.setForeground(Color.white);
            teacher_admin_query_button.addMouseListener(new MouseListener() {
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
                    teacher_admin_query_button.setBackground(Color.gray);
                    teacher_admin_query_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    teacher_admin_query_button.setBackground(Color.darkGray);
                    teacher_admin_query_button.setForeground(Color.white);
                }
            });
            this.content.add(teacher_admin_query_button);
        }
        /**
         * 成绩查询按钮
         */
        {
            courseQuerybutton = new JButton("课程查询");
            courseQuerybutton.setActionCommand("修改_课程查询");
            courseQuerybutton.setBounds(350, 60, 150, 60);
            courseQuerybutton.setBackground(Color.DARK_GRAY);
            courseQuerybutton.setFont(new Font("宋体", Font.PLAIN, 30));
            courseQuerybutton.setBorder(null);
            courseQuerybutton.addActionListener(this);
            courseQuerybutton.setForeground(Color.white);
            courseQuerybutton.addMouseListener(new MouseListener() {
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
                    courseQuerybutton.setBackground(Color.gray);
                    courseQuerybutton.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    courseQuerybutton.setBackground(Color.darkGray);
                    courseQuerybutton.setForeground(Color.white);
                }
            });
            this.content.add(courseQuerybutton);
        }
        /**
         * 成绩查询按钮
         */
        {
            Teacher_admin_query_grade_button = new JButton("成绩查询");
            Teacher_admin_query_grade_button.setActionCommand("修改_成绩查询");
            Teacher_admin_query_grade_button.setBounds(200, 60, 150, 60);
            Teacher_admin_query_grade_button.setBackground(Color.DARK_GRAY);
            Teacher_admin_query_grade_button.setFont(new Font("宋体", Font.PLAIN, 30));
            Teacher_admin_query_grade_button.setBorder(null);
            Teacher_admin_query_grade_button.addActionListener(this);
            Teacher_admin_query_grade_button.setForeground(Color.white);
            Teacher_admin_query_grade_button.addMouseListener(new MouseListener() {
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
                    Teacher_admin_query_grade_button.setBackground(Color.gray);
                    Teacher_admin_query_grade_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    Teacher_admin_query_grade_button.setBackground(Color.darkGray);
                    Teacher_admin_query_grade_button.setForeground(Color.white);
                }
            });
            this.content.add(Teacher_admin_query_grade_button);
        }
        /**
         * 空表格初始化
         */
        {

            this.jTable = new JTable() {
                //保存修改前的值
                private static final long serialVersionUID = 1L;
                public void editingStopped(ChangeEvent changeevent) {
                    int r = getEditingRow();
                    int c = getEditingColumn();
                    System.out.println(r + "--" + c);
                    editValue = (String) jTable.getValueAt(r, c);
                   // System.out.println(editValue);
                    TableCellEditor tablecelleditor = getCellEditor();
                    if (tablecelleditor != null) {
                        Object obj = tablecelleditor.getCellEditorValue();
                        setValueAt(obj, editingRow, editingColumn);
                        removeEditor();
                    }
                }
            };
            jTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
                if (!listSelectionEvent.getValueIsAdjusting()) {
                    //支持拖动多选
                    int[] rows = jTable.getSelectedRows();
                    // int[] cols = table.getSelectedColumns();//选中的列
                    for (int i : rows) {
                        System.out.println("#方法一:\t" + jTable.getValueAt(i, 0) + "\t" + jTable.getValueAt(i, 1));
                    }
                }
            });
            JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
            head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
            head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
            scrollPane = new JScrollPane(jTable);
            scrollPane.setBounds(125, 300, 1600, 600);
            content.add(scrollPane);
        }


        /**
         * 排序方式选择框
         */
        {
            orderSelectbox = new JComboBox<>();
            orderSelectbox.setBounds(1200, 143, 150, 40);
            orderSelectbox.setForeground(Color.white);
            orderSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            orderSelectbox.addItem("升序");
            orderSelectbox.addItem("降序");
            orderSelectbox.addItem("成绩升序");
            orderSelectbox.addItem("成绩降序");
            orderSelectbox.addItem("姓名升序");
            orderSelectbox.addItem("姓名降序");
            orderSelectbox.addItem("学号升序");
            orderSelectbox.addItem("学号降序");
            orderSelectbox.addItem("班级升序");
            orderSelectbox.addItem("班级降序");
            JLabel label = new JLabel("排序方式:");
            lableCustomize(label);
            label.setBounds(1050, 130, 120, 80);
            this.content.add(label);
            this.content.add(orderSelectbox);
        }
        /**
         * 排名选择框
         */
        {
            JLabel label1 = new JLabel("查看前");
            JLabel label2 = new JLabel("人");
            lableCustomize(label1);
            label1.setBounds(200, 200, 80, 50);
            this.content.add(label1);
            lableCustomize(label2);
            label2.setBounds(350, 200, 40, 50);
            this.content.add(label2);
            topText = new JTextField();
            topText.setForeground(Color.green);
            topText.setBounds(290, 200, 50, 30);
            content.add(topText);
        }
        /**
         * 奖学金选择框
         */
        {
            rb = new JRadioButton("奖学金");
            rb.addActionListener(this);
            rb.setFont(new Font("宋体", Font.PLAIN, 23));
            rb.setBounds(400, 190, 150, 50);
            content.add(rb);
        }

        /**
         * 性别选择框
         */
        {
            sexSelectBox = new JComboBox<>();
            sexSelectBox.addItem("性别");
            sexSelectBox.addItem("男");
            sexSelectBox.addItem("女");
            sexSelectBox.setForeground(Color.white);
            sexSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            sexSelectBox.setBounds(1200, 200, 100, 40);
            this.content.add(sexSelectBox);
        }
        /**
         * 班级选择框
         */
        {
            classSelectbox = new JComboBox<>();
            classSelectbox.setForeground(Color.white);
            classSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            classSelectbox.setBounds(560, 200, 160, 40);
            classSelectbox.addItem("--班 级--");
//        ResultSet select_className_from_class = controller.selectQuery("select ClassName from class");
//        while (select_className_from_class.next())
//        {
//            String string = select_className_from_class.getString(1);
//            classSelectbox.addItem(string);
//        }
            this.content.add(classSelectbox);
        }
        /**
         * 系部选择框
         */
        {
            deptSelectBox = new JComboBox<>();
            deptSelectBox.setForeground(Color.white);
            deptSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            deptSelectBox.setBounds(800, 200, 300, 40);
            deptSelectBox.addItem("-------系  部-------");
            deptSelectBox.addActionListener(this);
            ResultSet select_deptNo_from_dept = controller.selectQuery("select DeptName from dept");
            while (select_deptNo_from_dept.next()) {
                deptSelectBox.addItem(select_deptNo_from_dept.getString(1));
            }
            this.content.add(deptSelectBox);
        }
        /**
         * 及格选择
         */
        {
            passRb = new JRadioButton("不及格");
            passRb.addActionListener(this);
            passRb.setFont(new Font("宋体", Font.PLAIN, 23));
            passRb.setBounds(130, 240, 140, 50);
            content.add(passRb);
        }
        /**
         * 课程信息选择框
         */
        {
            courseSelectbox = new JComboBox<>();
            courseSelectbox.addItem("--------课 程--------");
            courseSelectbox.setForeground(Color.white);
            courseSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            courseSelectbox.setBounds(270, 250, 300, 40);
            ResultSet select_cname_from_course = controller.selectQuery("select Cname from course");
            while (select_cname_from_course.next()) {
                courseSelectbox.addItem(select_cname_from_course.getString(1));
            }
            this.content.add(courseSelectbox);
        }
    }

    private void teacher_admin_delete_panel() throws SQLException {
        content.removeAll();
        content.repaint();

        /**
         * 学号，姓名，班级
         * 提示文字
         */
        {
            queryPanelLable[0] = new JLabel("学号:");
            queryPanelLable[0].setBounds(100, 130, 100, 80);
            lableCustomize(queryPanelLable[0]);
            content.add(queryPanelLable[0]);
            queryPanelLable[1] = new JLabel("姓名:");
            queryPanelLable[1].setBounds(400, 130, 100, 80);
            lableCustomize(queryPanelLable[1]);
            content.add(queryPanelLable[1]);
            queryPanelLable[2] = new JLabel("班级号:");
            queryPanelLable[2].setBounds(700, 130, 100, 80);
            lableCustomize(queryPanelLable[2]);
            content.add(queryPanelLable[2]);
        }
        /**
         * 学号
         * 姓名
         * 班级
         * 输入框体
         */
        {
            queryPanelText[0] = new JTextField();
            queryPanelText[0].setBounds(200, 143, 200, 30);
            queryPanelText[0].setForeground(Color.green);
            content.add(queryPanelText[0]);
            queryPanelText[1] = new JTextField();
            queryPanelText[1].setBounds(500, 143, 200, 30);
            queryPanelText[1].setForeground(Color.green);
            content.add(queryPanelText[1]);
            queryPanelText[2] = new JTextField();
            queryPanelText[2].setBounds(800, 143, 200, 30);
            queryPanelText[2].setForeground(Color.green);
            content.add(queryPanelText[2]);
        }


        /**
         * 删除按钮
         */
        {
            delete_button = new JButton("删 除");
            delete_button.setBounds(700, 60, 100, 60);
            delete_button.setBackground(Color.DARK_GRAY);
            delete_button.setFont(new Font("宋体", Font.PLAIN, 30));
            delete_button.setBorder(null);
            delete_button.addActionListener(this);
            delete_button.setForeground(Color.white);
            delete_button.addMouseListener(new MouseListener() {
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
                    delete_button.setBackground(Color.gray);
                    delete_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    delete_button.setBackground(Color.darkGray);
                    delete_button.setForeground(Color.white);
                }
            });
            this.content.add( delete_button);
        }

        /**
         * 查询按钮
         */
        {
            teacher_admin_query_button = new JButton("查 询");
            teacher_admin_query_button.setBounds(100, 60, 100, 60);
            teacher_admin_query_button.setBackground(Color.DARK_GRAY);
            teacher_admin_query_button.setFont(new Font("宋体", Font.PLAIN, 30));
            teacher_admin_query_button.setBorder(null);
            teacher_admin_query_button.addActionListener(this);
            teacher_admin_query_button.setForeground(Color.white);
            teacher_admin_query_button.addMouseListener(new MouseListener() {
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
                    teacher_admin_query_button.setBackground(Color.gray);
                    teacher_admin_query_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    teacher_admin_query_button.setBackground(Color.darkGray);
                    teacher_admin_query_button.setForeground(Color.white);
                }
            });
            this.content.add(teacher_admin_query_button);
        }
        /**
         * 课程查询按钮
         */
        {
            courseQuerybutton = new JButton("课程查询");
            courseQuerybutton.setActionCommand("课程 查询 删除面板");
            courseQuerybutton.setBounds(500, 60, 150, 60);
            courseQuerybutton.setBackground(Color.DARK_GRAY);
            courseQuerybutton.setFont(new Font("宋体", Font.PLAIN, 30));
            courseQuerybutton.setBorder(null);
            courseQuerybutton.addActionListener(this);
            courseQuerybutton.setForeground(Color.white);
            courseQuerybutton.addMouseListener(new MouseListener() {
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
                    courseQuerybutton.setBackground(Color.gray);
                    courseQuerybutton.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    courseQuerybutton.setBackground(Color.darkGray);
                    courseQuerybutton.setForeground(Color.white);
                }
            });
            this.content.add(courseQuerybutton);
        }

        /**
         * 班级查询按钮
         */
        {
            classQuerybutton = new JButton("班级查询");
            classQuerybutton.setActionCommand("班级 查询_删除");
            classQuerybutton.setBounds(350, 60, 150, 60);
            classQuerybutton.setBackground(Color.DARK_GRAY);
            classQuerybutton.setFont(new Font("宋体", Font.PLAIN, 30));
            classQuerybutton.setBorder(null);
            classQuerybutton.addActionListener(this);
            classQuerybutton.setForeground(Color.white);
            classQuerybutton.addMouseListener(new MouseListener() {
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
                    classQuerybutton.setBackground(Color.gray);
                    classQuerybutton.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    classQuerybutton.setBackground(Color.darkGray);
                    classQuerybutton.setForeground(Color.white);
                }
            });
            this.content.add(classQuerybutton);
        }

        /**
         * 成绩查询页面跳转按钮
         */
        {
            Teacher_admin_query_grade_button = new JButton("成绩查询");
            Teacher_admin_query_grade_button.setActionCommand("修改_成绩查询");
            Teacher_admin_query_grade_button.setBounds(200, 60, 150, 60);
            Teacher_admin_query_grade_button.setBackground(Color.DARK_GRAY);
            Teacher_admin_query_grade_button.setFont(new Font("宋体", Font.PLAIN, 30));
            Teacher_admin_query_grade_button.setBorder(null);
            Teacher_admin_query_grade_button.addActionListener(this);
            Teacher_admin_query_grade_button.setForeground(Color.white);
            Teacher_admin_query_grade_button.addMouseListener(new MouseListener() {
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
                    Teacher_admin_query_grade_button.setBackground(Color.gray);
                    Teacher_admin_query_grade_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    Teacher_admin_query_grade_button.setBackground(Color.darkGray);
                    Teacher_admin_query_grade_button.setForeground(Color.white);
                }
            });
            this.content.add(Teacher_admin_query_grade_button);
        }
        /**
         * 空表格初始化
         */
        {
            this.jTable = new JTable() {
                //保存修改前的值
                private static final long serialVersionUID = 1L;
                public void editingStopped(ChangeEvent changeevent) {
                    int r = getEditingRow();
                    int c = getEditingColumn();
                    System.out.println(r + "--" + c);
                    editValue = (String) jTable.getValueAt(r, c);
                    // System.out.println(editValue);
                    TableCellEditor tablecelleditor = getCellEditor();
                    if (tablecelleditor != null) {
                        Object obj = tablecelleditor.getCellEditorValue();
                        setValueAt(obj, editingRow, editingColumn);
                        removeEditor();
                    }
                }
            };
            jTable.getSelectionModel().addListSelectionListener(this);
            JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
            head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
            head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
            scrollPane = new JScrollPane(jTable);
            scrollPane.setBounds(125, 300, 1600, 600);
            content.add(scrollPane);
        }
        /**
         * 排序方式选择框
         */
        {
            orderSelectbox = new JComboBox<>();
            orderSelectbox.setBounds(1200, 143, 150, 40);
            orderSelectbox.setForeground(Color.white);
            orderSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            orderSelectbox.addItem("升序");
            orderSelectbox.addItem("降序");
            orderSelectbox.addItem("成绩升序");
            orderSelectbox.addItem("成绩降序");
            orderSelectbox.addItem("姓名升序");
            orderSelectbox.addItem("姓名降序");
            orderSelectbox.addItem("学号升序");
            orderSelectbox.addItem("学号降序");
            orderSelectbox.addItem("班级升序");
            orderSelectbox.addItem("班级降序");
            JLabel label = new JLabel("排序方式:");
            lableCustomize(label);
            label.setBounds(1050, 130, 120, 80);
            this.content.add(label);
            this.content.add(orderSelectbox);
        }
        /**
         * 排名选择框
         */
        {
            JLabel label1 = new JLabel("查看前");
            JLabel label2 = new JLabel("人");
            lableCustomize(label1);
            label1.setBounds(200, 200, 80, 50);
            this.content.add(label1);
            lableCustomize(label2);
            label2.setBounds(350, 200, 40, 50);
            this.content.add(label2);
            topText = new JTextField();
            topText.setForeground(Color.green);
            topText.setBounds(290, 200, 50, 30);
            content.add(topText);
        }
        /**
         * 奖学金选择框
         */
        {
            rb = new JRadioButton("奖学金");
            rb.addActionListener(this);
            rb.setFont(new Font("宋体", Font.PLAIN, 23));
            rb.setBounds(400, 190, 150, 50);
            content.add(rb);
        }

        /**
         * 性别选择框
         */
        {
            sexSelectBox = new JComboBox<>();
            sexSelectBox.addItem("性别");
            sexSelectBox.addItem("男");
            sexSelectBox.addItem("女");
            sexSelectBox.setForeground(Color.white);
            sexSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            sexSelectBox.setBounds(1200, 200, 100, 40);
            this.content.add(sexSelectBox);
        }
        /**
         * 班级选择框
         */
        {
            classSelectbox = new JComboBox<>();
            classSelectbox.setForeground(Color.white);
            classSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            classSelectbox.setBounds(560, 200, 160, 40);
            classSelectbox.addItem("--班 级--");
            this.content.add(classSelectbox);
        }
        /**
         * 系部选择框
         */
        {
            deptSelectBox = new JComboBox<>();
            deptSelectBox.setForeground(Color.white);
            deptSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            deptSelectBox.setBounds(800, 200, 300, 40);
            deptSelectBox.addItem("-------系  部-------");
            deptSelectBox.addActionListener(this);
            ResultSet select_deptNo_from_dept = controller.selectQuery("select DeptName from dept");
            while (select_deptNo_from_dept.next()) {
                deptSelectBox.addItem(select_deptNo_from_dept.getString(1));
            }
            this.content.add(deptSelectBox);
        }
        /**
         * 及格选择
         */
        {
            passRb = new JRadioButton("不及格");
            passRb.addActionListener(this);
            passRb.setFont(new Font("宋体", Font.PLAIN, 23));
            passRb.setBounds(130, 240, 140, 50);
            content.add(passRb);
        }
        /**
         * 课程信息选择框
         */
        {
            courseSelectbox = new JComboBox<>();
            courseSelectbox.addItem("--------课 程--------");
            courseSelectbox.setForeground(Color.white);
            courseSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            courseSelectbox.setBounds(270, 250, 300, 40);
            ResultSet select_cname_from_course = controller.selectQuery("select Cname from course");
            while (select_cname_from_course.next()) {
                courseSelectbox.addItem(select_cname_from_course.getString(1));
            }
            this.content.add(courseSelectbox);
        }
    }

    private void teacher_admin_insert_panel() throws SQLException {
        content.removeAll();
        content.repaint();

        /**
         * 学号，姓名
         * 提示文字
         */
        {
            queryPanelLable[0] = new JLabel("学号:");
            queryPanelLable[0].setBounds(100, 130, 100, 80);
            lableCustomize(queryPanelLable[0]);
            content.add(queryPanelLable[0]);
            queryPanelLable[1] = new JLabel("姓名:");
            queryPanelLable[1].setBounds(400, 130, 100, 80);
            lableCustomize(queryPanelLable[1]);
            content.add(queryPanelLable[1]);
        }
        /**
         * 性别输入
         */
        {
            JLabel label = new JLabel("年龄:");
            lableCustomize(label);
            label.setBounds(1400,130,100,80);
            content.add(label);
            ageTextfield = new JTextField();
            ageTextfield.setBounds(1500, 143, 200, 30);
            ageTextfield.setForeground(Color.green);
            content.add(ageTextfield);
        }


        {
            JLabel[] labels = new JLabel[4];
            String[] strings={"课程号:","课程名:","学分:","先修课:"};
            for (int i=0;i<4;i++)
            {
                labels[i] = new JLabel(strings[i]);
                lableCustomize(labels[i]);
                labels[i].setBounds(100+i*300,187,100,80);
                content.add(labels[i]);
            }
            for (int i = 0; i < 3; i++) {
                courseInsertfield[i] = new JTextField();
                courseInsertfield[i].setForeground(Color.green);
                courseInsertfield[i].setBounds(200 + i * 300, 200, 200, 30);
                content.add(courseInsertfield[i]);
            }
        }

        {
            JLabel[] label = new JLabel[4];
            String[] strings={"班级号:","班级名:","部门号:","成绩:"};
            for (int i=0;i<4;i++)
            {
                label[i] = new JLabel(strings[i]);
                lableCustomize(label[i]);
                label[i].setBounds(100+i*300,237,100,80);
                content.add(label[i]);
            }
            for (int i = 0; i < 3; i++) {
                classInsertfield[i] = new JTextField();
                classInsertfield[i].setForeground(Color.green);
                classInsertfield[i].setBounds(200 + i * 300, 250, 200, 30);
                content.add(classInsertfield[i]);
            }
            gradeInsertfield = new JTextField();
            gradeInsertfield.setForeground(Color.green);
            gradeInsertfield.setBounds(200 + 3 * 300, 250, 200, 30);
            content.add( gradeInsertfield);
        }

        /**
         * 学号
         * 姓名
         * 输入框体
         */
        {
            queryPanelText[0] = new JTextField();
            queryPanelText[0].setBounds(200, 143, 200, 30);
            queryPanelText[0].setForeground(Color.green);
            content.add(queryPanelText[0]);
            queryPanelText[1] = new JTextField();
            queryPanelText[1].setBounds(500, 143, 200, 30);
            queryPanelText[1].setForeground(Color.green);
            content.add(queryPanelText[1]);
        }


        /**
         * 插入按钮
         */
        {
            delete_button = new JButton("新建课程");
            delete_button.setBounds(650, 60, 150, 60);
            delete_button.setBackground(Color.DARK_GRAY);
            delete_button.setFont(new Font("宋体", Font.PLAIN, 30));
            delete_button.setBorder(null);
            delete_button.addActionListener(this);
            delete_button.setForeground(Color.white);
            delete_button.addMouseListener(new MouseListener() {
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
                    delete_button.setBackground(Color.gray);
                    delete_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    delete_button.setBackground(Color.darkGray);
                    delete_button.setForeground(Color.white);
                }
            });
            this.content.add( delete_button);
        }

        /**
         * 课程按钮
         */
        {
            courseQuerybutton = new JButton("新建学生");
            courseQuerybutton.setBounds(500, 60, 150, 60);
            courseQuerybutton.setBackground(Color.DARK_GRAY);
            courseQuerybutton.setFont(new Font("宋体", Font.PLAIN, 30));
            courseQuerybutton.setBorder(null);
            courseQuerybutton.addActionListener(this);
            courseQuerybutton.setForeground(Color.white);
            courseQuerybutton.addMouseListener(new MouseListener() {
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
                    courseQuerybutton.setBackground(Color.gray);
                    courseQuerybutton.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    courseQuerybutton.setBackground(Color.darkGray);
                    courseQuerybutton.setForeground(Color.white);
                }
            });
            this.content.add(courseQuerybutton);
        }

        /**
         * 班级按钮
         */
        {
            classQuerybutton = new JButton("新建班级");

            classQuerybutton.setBounds(350, 60, 150, 60);
            classQuerybutton.setBackground(Color.DARK_GRAY);
            classQuerybutton.setFont(new Font("宋体", Font.PLAIN, 30));
            classQuerybutton.setBorder(null);
            classQuerybutton.addActionListener(this);
            classQuerybutton.setForeground(Color.white);
            classQuerybutton.addMouseListener(new MouseListener() {
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
                    classQuerybutton.setBackground(Color.gray);
                    classQuerybutton.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    classQuerybutton.setBackground(Color.darkGray);
                    classQuerybutton.setForeground(Color.white);
                }
            });
            this.content.add(classQuerybutton);
        }

        /**
         * 成绩按钮
         */
        {
            Teacher_admin_query_grade_button = new JButton("新建成绩");
            Teacher_admin_query_grade_button.setBounds(200, 60, 150, 60);
            Teacher_admin_query_grade_button.setBackground(Color.DARK_GRAY);
            Teacher_admin_query_grade_button.setFont(new Font("宋体", Font.PLAIN, 30));
            Teacher_admin_query_grade_button.setBorder(null);
            Teacher_admin_query_grade_button.addActionListener(this);
            Teacher_admin_query_grade_button.setForeground(Color.white);
            Teacher_admin_query_grade_button.addMouseListener(new MouseListener() {
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
                    Teacher_admin_query_grade_button.setBackground(Color.gray);
                    Teacher_admin_query_grade_button.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    Teacher_admin_query_grade_button.setBackground(Color.darkGray);
                    Teacher_admin_query_grade_button.setForeground(Color.white);
                }
            });
            this.content.add(Teacher_admin_query_grade_button);
        }
        /**
         * 空表格初始化
         */
        {
            this.jTable = new JTable() {
                //保存修改前的值
                private static final long serialVersionUID = 1L;
                public void editingStopped(ChangeEvent changeevent) {
                    int r = getEditingRow();
                    int c = getEditingColumn();
                    System.out.println(r + "--" + c);
                    editValue = (String) jTable.getValueAt(r, c);
                    // System.out.println(editValue);
                    TableCellEditor tablecelleditor = getCellEditor();
                    if (tablecelleditor != null) {
                        Object obj = tablecelleditor.getCellEditorValue();
                        setValueAt(obj, editingRow, editingColumn);
                        removeEditor();
                    }
                }
            };
            jTable.getSelectionModel().addListSelectionListener(this);
            JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
            head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
            head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
            scrollPane = new JScrollPane(jTable);
            scrollPane.setBounds(125, 300, 1600, 600);
            content.add(scrollPane);
        }
        /**
         * 性别选择框
         */
        {
            sexSelectBox = new JComboBox<>();
            sexSelectBox.addItem("性别");
            sexSelectBox.addItem("男");
            sexSelectBox.addItem("女");
            sexSelectBox.setForeground(Color.white);
            sexSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            sexSelectBox.setBounds(1250, 140, 100, 40);
            this.content.add(sexSelectBox);
        }
        /**
         * 班级选择框
         */
        {
            classSelectbox = new JComboBox<>();
            classSelectbox.setForeground(Color.white);
            classSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            classSelectbox.setBounds(750, 140, 160, 40);
            classSelectbox.addItem("--班 级--");
            this.content.add(classSelectbox);
        }
        /**
         * 系部选择框
         */
        {
            deptSelectBox = new JComboBox<>();
            deptSelectBox.setForeground(Color.white);
            deptSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            deptSelectBox.setBounds(930, 140, 300, 40);
            deptSelectBox.addItem("-------系  部-------");
            deptSelectBox.addActionListener(this);
            ResultSet select_deptNo_from_dept = controller.selectQuery("select DeptName from dept");
            while (select_deptNo_from_dept.next()) {
                deptSelectBox.addItem(select_deptNo_from_dept.getString(1));
            }
            this.content.add(deptSelectBox);
        }

        /**
         * 课程信息选择框
         */
        {
            courseSelectbox = new JComboBox<>();
            courseSelectbox.addItem("--------课 程--------");
            courseSelectbox.setForeground(Color.white);
            courseSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            courseSelectbox.setBounds(1100, 197, 300, 40);
            ResultSet select_cname_from_course = controller.selectQuery("select Cname from course");
            while (select_cname_from_course.next()) {
                courseSelectbox.addItem(select_cname_from_course.getString(1));
            }
            this.content.add(courseSelectbox);
        }
    }

    private void teacher_admin_analyze_panel() throws SQLException {
        content.removeAll();
        content.repaint();
        /**
         * 排序方式选择框
         */
        {
            orderSelectbox = new JComboBox<>();
            orderSelectbox.setBounds(1200, 220, 150, 40);
            orderSelectbox.setForeground(Color.white);
            orderSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            orderSelectbox.addItem("升序");
            orderSelectbox.addItem("降序");
            orderSelectbox.addItem("成绩升序");
            orderSelectbox.addItem("成绩降序");
            orderSelectbox.addItem("姓名升序");
            orderSelectbox.addItem("姓名降序");
            orderSelectbox.addItem("学号升序");
            orderSelectbox.addItem("学号降序");
            orderSelectbox.addItem("班级升序");
            orderSelectbox.addItem("班级降序");
            JLabel label = new JLabel("排序方式:");
            lableCustomize(label);
            label.setBounds(1050, 213, 120, 80);
            this.content.add(label);
            this.content.add(orderSelectbox);
        }

        /**
         * 及格选择
         */
        {
            passRb = new JRadioButton("不及格");
            passRb.addActionListener(this);
            passRb.setFont(new Font("宋体", Font.PLAIN, 23));
            passRb.setBounds(130, 240, 140, 50);
            content.add(passRb);
        }
        /**
         * 学号
         * 姓名
         * 输入框体
         */
        {
            queryPanelText[0] = new JTextField();
            queryPanelText[0].setBounds(200, 143, 200, 30);
            queryPanelText[0].setForeground(Color.green);
            content.add(queryPanelText[0]);
            queryPanelText[1] = new JTextField();
            queryPanelText[1].setBounds(500, 143, 200, 30);
            queryPanelText[1].setForeground(Color.green);
            content.add(queryPanelText[1]);
        }
        /**
         * 学号，姓名
         * 提示文字
         */
        {
            queryPanelLable[0] = new JLabel("学号:");
            queryPanelLable[0].setBounds(100, 130, 100, 80);
            lableCustomize(queryPanelLable[0]);
            content.add(queryPanelLable[0]);
            queryPanelLable[1] = new JLabel("姓名:");
            queryPanelLable[1].setBounds(400, 130, 100, 80);
            lableCustomize(queryPanelLable[1]);
            content.add(queryPanelLable[1]);
        }
        /**
         * 班级选择框
         */
        {
            classSelectbox = new JComboBox<>();
            classSelectbox.setForeground(Color.white);
            classSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            classSelectbox.setBounds(750, 140, 160, 40);
            classSelectbox.addItem("--班 级--");
            this.content.add(classSelectbox);
        }
        /**
         * 空表格初始化
         */
        {
            this.jTable = new JTable() {
                //保存修改前的值
                private static final long serialVersionUID = 1L;
                public void editingStopped(ChangeEvent changeevent) {
                    int r = getEditingRow();
                    int c = getEditingColumn();
                    System.out.println(r + "--" + c);
                    editValue = (String) jTable.getValueAt(r, c);
                    // System.out.println(editValue);
                    TableCellEditor tablecelleditor = getCellEditor();
                    if (tablecelleditor != null) {
                        Object obj = tablecelleditor.getCellEditorValue();
                        setValueAt(obj, editingRow, editingColumn);
                        removeEditor();
                    }
                }
            };
            jTable.getSelectionModel().addListSelectionListener(this);
            JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
            head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
            head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
            scrollPane = new JScrollPane(jTable);
            scrollPane.setBounds(125, 300, 1600, 600);
            content.add(scrollPane);
        }
        /**
         * 系部选择框
         */
        {
            deptSelectBox = new JComboBox<>();
            deptSelectBox.setForeground(Color.white);
            deptSelectBox.setFont(new Font("楷体", Font.PLAIN, 24));
            deptSelectBox.setBounds(930, 140, 300, 40);
            deptSelectBox.addItem("-------系  部-------");
            deptSelectBox.addActionListener(this);
            ResultSet select_deptNo_from_dept = controller.selectQuery("select DeptName from dept");
            while (select_deptNo_from_dept.next()) {
                deptSelectBox.addItem(select_deptNo_from_dept.getString(1));
            }
            this.content.add(deptSelectBox);
        }

        /**
         * 课程信息选择框
         */
        {
            courseSelectbox = new JComboBox<>();
            courseSelectbox.addItem("--------课 程--------");
            courseSelectbox.setForeground(Color.white);
            courseSelectbox.setFont(new Font("楷体", Font.PLAIN, 24));
            courseSelectbox.setBounds(1300, 140, 300, 40);
            ResultSet select_cname_from_course = controller.selectQuery("select Cname from course");
            while (select_cname_from_course.next()) {
                courseSelectbox.addItem(select_cname_from_course.getString(1));
            }
            this.content.add(courseSelectbox);
        }
        JButton studentGrade = new JButton("学生成绩分析");
        JButton classGrade = new JButton("班级成绩分析");
        JButton jige = new JButton("不及格统计");
        JButton maxGrade;
        JButton maxSumGrade;
        /**
         * 插入按钮
         */
        {
            maxGrade = new JButton("单科最高成绩");
            maxGrade.setBounds(750, 60, 250, 60);
            maxGrade.setBackground(Color.DARK_GRAY);
            maxGrade.setFont(new Font("宋体", Font.PLAIN, 30));
            maxGrade.setBorder(null);
            maxGrade.addActionListener(this);
            maxGrade.setForeground(Color.white);
            maxGrade.addMouseListener(new MouseListener() {
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
                    maxGrade.setBackground(Color.gray);
                    maxGrade.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    maxGrade.setBackground(Color.darkGray);
                    maxGrade.setForeground(Color.white);
                }
            });
            this.content.add( maxGrade);
        }

        /**
         * 插入按钮
         */
        {
            maxSumGrade = new JButton("最高总成绩");
            maxSumGrade.setBounds(1000, 60, 250, 60);
            maxSumGrade.setBackground(Color.DARK_GRAY);
            maxSumGrade.setFont(new Font("宋体", Font.PLAIN, 30));
            maxSumGrade.setBorder(null);
            maxSumGrade.addActionListener(this);
            maxSumGrade.setForeground(Color.white);
            maxSumGrade.addMouseListener(new MouseListener() {
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
                    maxSumGrade.setBackground(Color.gray);
                    maxSumGrade.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    maxSumGrade.setBackground(Color.darkGray);
                    maxSumGrade.setForeground(Color.white);
                }
            });
            this.content.add( maxSumGrade);
        }

        /**
         * 课程按钮
         */
        {
            jige.setBounds(580, 60, 150, 60);
            jige.setBackground(Color.DARK_GRAY);
            jige.setFont(new Font("宋体", Font.PLAIN, 30));
            jige.setBorder(null);
            jige.addActionListener(this);
            jige.setForeground(Color.white);
            jige.addMouseListener(new MouseListener() {
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
                    jige.setBackground(Color.gray);
                    jige.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    jige.setBackground(Color.darkGray);
                    jige.setForeground(Color.white);
                }
            });
            this.content.add(jige);
        }

        /**
         * 班级成绩分析按钮
         */
        {


            classGrade.setBounds(350, 60, 190, 60);
            classGrade.setBackground(Color.DARK_GRAY);
            classGrade.setFont(new Font("宋体", Font.PLAIN, 30));
            classGrade.setBorder(null);
            classGrade.addActionListener(this);
            classGrade.setForeground(Color.white);
            classGrade.addMouseListener(new MouseListener() {
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
                    classGrade.setBackground(Color.gray);
                    classGrade.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    classGrade.setBackground(Color.darkGray);
                    classGrade.setForeground(Color.white);
                }
            });
            this.content.add(classGrade);
        }

        /**
         * 学生成绩分析按钮
         */
        {

            studentGrade.setBounds(150, 60, 180, 60);
            studentGrade.setBackground(Color.DARK_GRAY);
            studentGrade.setFont(new Font("宋体", Font.PLAIN, 30));
            studentGrade.setBorder(null);
            studentGrade.addActionListener(this);
            studentGrade.setForeground(Color.white);
            studentGrade.addMouseListener(new MouseListener() {
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
                    studentGrade.setBackground(Color.gray);
                    studentGrade.setForeground(Color.black);
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    studentGrade.setBackground(Color.darkGray);
                    studentGrade.setForeground(Color.white);
                }
            });
            this.content.add(studentGrade);
        }

    }

    private void teacher_admin_query()
    {
        content.remove(scrollPane);
        content.repaint();
        for(int i=0;i<queryPanelText.length;i++)
        {
            if(queryPanelText[i].getText() == null)
            {
                SelectInfo[i] = "";
                continue;
            }
            SelectInfo[i] = queryPanelText[i].getText();
        }
        String sql = select(SelectInfo);
        String s="";
        if (sql.isEmpty())
        {
            s = "select * from student";
        }
        else
        {   s = "select * from student where "+sql;}
    }

    private void insertStudent() throws SQLException {
        String Sno=queryPanelText[0].getText();
        String Sname=queryPanelText[1].getText();
        String Ssex = (String) sexSelectBox.getSelectedItem()=="性别"?"男":(String) sexSelectBox.getSelectedItem();
        String Sage = this.ageTextfield.getText();
        String ClassName = (String) this.classSelectbox.getSelectedItem();
        ResultSet resultSet = controller.selectQuery("select ClassNo from class where ClassName='" + ClassName + "'");
        String ClassNo="";

        try {
            resultSet.next();
            ClassNo = resultSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            resultSet.close();
        }
        controller.insert_student(Sno,Sname,Ssex,Sage,ClassNo);
        JOptionPane.showMessageDialog(this,"插入成功");
        showTable_info(controller.selectQuery("select * from student"));
    }

    private void insertClass() throws SQLException {
        String className = this.classInsertfield[1].getText();
        String classNo = this.classInsertfield[0].getText();
        String DeptNo = this.classInsertfield[2].getText();
        controller.insert_class(classNo,className,DeptNo);
        JOptionPane.showMessageDialog(this,"插入成功");
        showTable_info(controller.selectQuery("select * from class"));
    }

    private void insertGrade() throws SQLException {
        String Cno= courseInsertfield[0].getText();
        String Sno=queryPanelText[0].getText();
        String Grade = gradeInsertfield.getText();
        controller.insert_grade(Sno,Cno,Grade);
        JOptionPane.showMessageDialog(this,"插入成功");
        showTable_info(controller.selectQuery("select * from sc"));
    }

    private void insertCourse() throws SQLException {
        String Cno= courseInsertfield[0].getText();
        String Cname = courseInsertfield[1].getText();
        String Ccredit = courseInsertfield[2].getText();
        String xCname = (String) courseSelectbox.getSelectedItem();
        String Cpno = "";
        ResultSet resultSet = controller.selectQuery("select Cno from course where Cname='" + xCname + "'");

        try {
            if (resultSet==null)
            {
                Cpno=null;
            }else {
            resultSet.next();
            Cpno= resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(resultSet!=null)
            {
                resultSet.close();
            }

        }
        if (Cpno.isEmpty())
        {
            controller.insert_course(Cno,Cname,null,Ccredit);
        }else {
            controller.insert_course(Cno, Cname, Cpno, Ccredit);
        }
        JOptionPane.showMessageDialog(this,"插入成功");
        showTable_info(controller.selectQuery("select * from course"));

    }
    /**
     * 学生基本信息查询
     * @param resultSet
     * @throws SQLException
     */
    private void showTable_studentInfo(ResultSet resultSet) throws SQLException {
        this.tableModel = new DefaultTableModel();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for(int i=1;i<=metaData.getColumnCount();i++)
        {
            System.out.println(metaData.getColumnName(i));
            tableModel.addColumn(nameTrans(metaData.getColumnName(i)));
        }

        Object[] array = new Object[metaData.getColumnCount()];
        this.jTable = new JTable(tableModel);
        jTable.getSelectionModel().addListSelectionListener(this);

        JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
        head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
        scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(125,300,1600,600);
        content.add(scrollPane);
        /**
         * 表格内容居中显示
         */
        {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            // tcr.setSize(40,35);
            tcr.setHorizontalAlignment(SwingConstants.CENTER);

            jTable.setDefaultRenderer(Object.class, tcr);
        }
        jTable.setFont(new Font("Menu.font", Font.PLAIN, 20));
        jTable.setRowHeight(25);
        jTable.setGridColor(Color.WHITE);
        jTable.setShowGrid(false);
        jTable.setBounds(60,223,1000,500);

        this.content.validate();
    }

    /**
     * 表头转换
     * @param name
     * @return
     */
    public static String nameTrans(String name)
    {
       switch (name)
       {
           case "Sno": return "学号";
           case "Sname": return "姓名";
           case "Cno": return "课程号";
           case "Ssex": return "性别";
           case "ClassNo": return "班级号";
           case "ClassName": return "班级";
           case "DeptNo": return "系部号";
           case "DeptName": return "院系";
           case "Cname": return "课程名";
           case "Ccredit": return "学分";
           case "Cpno": return "先修课课号";
           case "Grade":return "成绩";
           case "Sage":return "年龄";
           case "Sdept": return "院系";
           case "CpName": return "先修课名称";
           case "AVG_grade": return "平均分";
           case "total" : return "总分";
           case "max_grade": return "最高分";
       }
       return name;
    }

    /**
     * 反向转换列名
     * 修改和删除用
     * @param name
     * @return
     */
    private String nameTransReverse(String name)
    {
        switch (name)
        {
            case "学号": return "Sno";
            case"姓名" : return "Sname";
            case "课程号": return "Cno";
            case "性别": return "Ssex" ;
            case "班级": return "ClassName";
            case "系部号": return "DeptNo";
            case "院系": return "DeptName";
            case "课程名": return "Cname";
            case "学分": return "Ccredit";
            case "先修课课号": return "Cpno";
            case "成绩":return "Grade";
            case "年龄":return "Sage";
            case "先修课名称": return "CpName";
            case "班级号" :return "ClassNo";

        }
        return name;
    }

    /**
     * 总查询方案
     * Teacher
     * admin
     * 自动识别表头
     * @param resultSet
     * @throws SQLException
     */
    private void showTable_info(ResultSet resultSet) throws SQLException {
        jTable.removeAll();
        jTable.repaint();

        this.tableModel = new DefaultTableModel();
        this.tableModel.addTableModelListener(this);

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i=1;i<=metaData.getColumnCount();i++)
            {
                tableModel.addColumn(nameTrans(metaData.getColumnName(i)));
            }
            Object[] array = new Object[metaData.getColumnCount()];
            while(resultSet.next()) {
                array = new Object[metaData.getColumnCount()];
                for (int i=0;i<metaData.getColumnCount();i++)
                {
                    array[i] = resultSet.getString(i+1);
                }
                tableModel.addRow(array);
            }
        }
        catch (NullPointerException e)
        {

        }
        finally {
            {
                DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
                tcr.setHorizontalAlignment(SwingConstants.CENTER);
                jTable.setDefaultRenderer(Object.class, tcr);
            }
            jTable.setFont(new Font("Menu.font", Font.PLAIN, 20));
            jTable.setRowHeight(25);
            jTable.setGridColor(Color.WHITE);
            jTable.setShowGrid(false);
            jTable.setBounds(60,223,1000,500);
            jTable.setModel(this.tableModel);
            scrollPane.repaint();
            this.content.add(scrollPane);
            this.content.revalidate();
        }


    }

    /**
     * 学生成绩的表格显示
     * @param resultSet
     * @throws SQLException
     */
    private void showTable_studentGrade(ResultSet resultSet) throws SQLException {
        this.tableModel = new DefaultTableModel();
        String[] strs = {"学号","姓名","课程名","分数"};
        ResultSetMetaData metaData = resultSet.getMetaData();
        for(int i=1;i<=metaData.getColumnCount();i++)
        {
            System.out.println(metaData.getColumnName(i));
            tableModel.addColumn(strs[i-1]);
        }
        Object[] array = new Object[metaData.getColumnCount()];
        this.jTable = new JTable(tableModel);
        //this.content.add(jTable);
        //scrollPane.setBorder(null);
        JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
        head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
        scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(125,300,1600,600);
        content.add(scrollPane);
        /**
         * 表格内容居中显示
         */
        {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            // tcr.setSize(40,35);
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            jTable.setDefaultRenderer(Object.class, tcr);
        }
        jTable.setFont(new Font("Menu.font", Font.PLAIN, 20));
        jTable.setRowHeight(25);
        jTable.setGridColor(Color.WHITE);
        jTable.setShowGrid(false);
        jTable.setBounds(60,223,1000,500);

        this.content.validate();
        while(resultSet.next()) {
            array = new Object[metaData.getColumnCount()];
            for (int i=0;i<metaData.getColumnCount();i++)
            {
                array[i] = resultSet.getString(i+1);
            }
            tableModel.addRow(array);
            System.out.print(resultSet.getString(1) + "---");
            System.out.print(resultSet.getString(2) + "---");
            System.out.println(resultSet.getString(3));
        }


    }

    /**
     * 学生课程查询无先修课
     * @param resultSet
     * @throws SQLException
     */
    private void showTable_studentCourse_whole(ResultSet resultSet) throws SQLException {
        this.tableModel = new DefaultTableModel();
        String[] strs = {"课程号","课程名","学分","先修课课程号","先修课课程名"};
        ResultSetMetaData metaData = resultSet.getMetaData();
        for(int i=1;i<=metaData.getColumnCount();i++)
        {
            System.out.println(metaData.getColumnName(i));
            tableModel.addColumn(strs[i-1]);
        }
        Object[] array = new Object[metaData.getColumnCount()];
        this.jTable = new JTable(tableModel);
        //this.content.add(jTable);
        //scrollPane.setBorder(null);
        JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
        head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
        scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(125,300,1600,600);
        content.add(scrollPane);
        /**
         * 表格内容居中显示
         */
        {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            // tcr.setSize(40,35);
            tcr.setHorizontalAlignment(SwingConstants.CENTER);

            jTable.setDefaultRenderer(Object.class, tcr);
        }
        jTable.setFont(new Font("Menu.font", Font.PLAIN, 20));
        jTable.setRowHeight(25);
        jTable.setGridColor(Color.WHITE);
        jTable.setShowGrid(false);
        jTable.setBounds(60,223,1000,500);

        this.content.validate();
        while(resultSet.next()) {
            array = new Object[metaData.getColumnCount()];
            for (int i=0;i<metaData.getColumnCount();i++)
            {
                array[i] = resultSet.getString(i+1);
            }
            tableModel.addRow(array);
            System.out.print(resultSet.getString(1) + "---");
            System.out.print(resultSet.getString(2) + "---");
            System.out.println(resultSet.getString(3));
        }


    }

    /**
     * 排名系统
     * 没写完
     * @param resultSet
     * @throws SQLException
     */
    private void showTable_studentRank(ResultSet resultSet) throws SQLException {
        this.tableModel = new DefaultTableModel();
        String[] strs = {"排名","姓名","总分"};
        for(int i=1;i<=strs.length;i++)
        {
            //System.out.println(metaData.getColumnName(i));
            tableModel.addColumn(strs[i-1]);
        }
//        ResultSetMetaData metaData = resultSet.getMetaData();
//        for(int i=1;i<=metaData.getColumnCount();i++)
//        {
//            System.out.println(metaData.getColumnName(i));
//            tableModel.addColumn(strs[i-1]);
//        }
//        Object[] array = new Object[metaData.getColumnCount()];
        this.jTable = new JTable(tableModel);
        JTableHeader head = jTable.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
        head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
        scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(1000,300,800,600);
        content.add(scrollPane);
        /**
         * 表格内容居中显示
         */
        {
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            // tcr.setSize(40,35);
            tcr.setHorizontalAlignment(SwingConstants.CENTER);

            jTable.setDefaultRenderer(Object.class, tcr);
        }
        jTable.setFont(new Font("Menu.font", Font.PLAIN, 20));
        jTable.setRowHeight(25);
        jTable.setGridColor(Color.WHITE);
        jTable.setShowGrid(false);
        jTable.setBounds(1000,223,800,600);

        this.content.validate();
//        while(resultSet.next()) {
//            array = new Object[metaData.getColumnCount()];
//            for (int i=0;i<metaData.getColumnCount();i++)
//            {
//                array[i] = resultSet.getString(i+1);
//            }
//            tableModel.addRow(array);
//            System.out.print(resultSet.getString(1) + "---");
//            System.out.print(resultSet.getString(2) + "---");
//            System.out.println(resultSet.getString(3));
//        }


    }

    /**
     * 老师管理员
     * 基本信息查询
     * @throws SQLException
     */
    private void teacher_admin_query_exe() throws SQLException {
        content.remove(scrollPane);
        content.repaint();
        for(int i=0;i<queryPanelText.length;i++)
        {
            if(queryPanelText[i].getText() == null)
            {
                SelectInfo[i] = "";
                continue;
            }
            SelectInfo[i] = queryPanelText[i].getText();
        }
        String sql = select(SelectInfo);
        s="";
        if (sql.isEmpty())
        {
             s = "select Sno,Sname,Ssex,Sage,ClassName,DeptName from dept,student,class where dept.DeptNo=class.DeptNo and student.ClassNo = class.ClassNo ";
        }
        else
        {   s = "select Sno,Sname,Ssex,Sage,ClassName,DeptName from dept,student,class where dept.DeptNo=class.DeptNo and student.ClassNo = class.ClassNo and "+sql;}

        if (sexSelectBox.getSelectedItem()!="性别") {
                s+=" and Ssex = '"+sexSelectBox.getSelectedItem().toString()+"'";
        }
        if (SelectInfo[2].isEmpty()){
            if (classSelectbox.getSelectedItem().toString()!="--班 级--")
            {
                    s+=" and ClassName ='"+classSelectbox.getSelectedItem().toString()+"'";
            }
        }
        if (deptSelectBox.getSelectedItem().toString()!="-------系  部-------")
        {
                s+=" and DeptName='"+deptSelectBox.getSelectedItem().toString()+"'";
        }
        if (orderSelectbox.getSelectedItem().toString()=="升序")
        {
            s+=" order by Sno asc";
        }
        else if(orderSelectbox.getSelectedItem().toString()=="降序"){
            s+=" order by Sno desc";
        }else{
            s+=sql_add_order();
        }
        if (rb.isSelected())
        {
            s+=" limit 3";
        }
        else {
            if (!topText.getText().isEmpty())
            {
                s+=" limit "+topText.getText();
            }
        }

        System.out.println(s);
        showTable_info(controller.selectQuery(s));
        //showTable_studentInfo(controller.selectQuery(s));
        content.repaint();
    }

    public String  sql_add_order()
    {

        if (orderSelectbox.getSelectedItem().toString()=="姓名升序")
        {
            return " order by Sname asc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="姓名降序")
        {
            return " order by Sno desc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="学号降序")
        {
            return " order by Sno desc";
        } else if (orderSelectbox.getSelectedItem().toString()=="学号升序")
        {
            return " order by Sno asc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="成绩降序")
        {
            return " order by Grade desc";
        } else if (orderSelectbox.getSelectedItem().toString()=="成绩升序")
        {
            return " order by Grade asc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="班级降序")
        {
            return " order by ClassNo desc";
        } else if (orderSelectbox.getSelectedItem().toString()=="班级升序")
        {
            return " order by ClassNo asc";
        }
        return "";
    }

    /**
     * 成绩查询
     * @throws SQLException
     */
    private void teacher_admin_query_grade_exe() throws SQLException {
        content.remove(scrollPane);
        content.repaint();
        jTable.removeAll();
        content.add(scrollPane);
        content.repaint();
        for(int i=0;i<queryPanelText.length;i++)
        {
            if(queryPanelText[i].getText() == null)
            {
                SelectInfo[i] = "";
                continue;
            }
            SelectInfo[i] = queryPanelText[i].getText();
        }
        String sql = select(SelectInfo);
        String s="";
        if (sql.isEmpty())
        {
            s+="select student.Sno,Sname,ClassName,Cname,Ccredit, ifnull(Grade,0) Grade " +
                    "from course,sc,student,class,dept " +
                    "where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo";
        }else
        {
            s+="select student.Sno,Sname,ClassName,Cname,Ccredit, ifnull(Grade,0) Grade " +
                    "from course,sc,student,class,dept " +
                    "where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo and "+sql;
            ;
        }
        if (SelectInfo[2].isEmpty()){
            if (classSelectbox.getSelectedItem().toString()!="--班 级--")
            {
                s+=" and ClassName ='"+classSelectbox.getSelectedItem().toString()+"'";
            }
        }
        if (deptSelectBox.getSelectedItem().toString()!="-------系  部-------")
        {
            s+=" and DeptName='"+deptSelectBox.getSelectedItem().toString()+"'";
        }
        if (passRb.isSelected())
        {
            s+=" and Grade<60";
        }
        if (courseSelectbox.getSelectedItem()!="--------课 程--------"){
            s+=" and Cname='"+courseSelectbox.getSelectedItem().toString()+"'";
        }
        if (orderSelectbox.getSelectedItem().toString()=="升序")
        {
            s+=" order by Grade asc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="降序 "){
            s+=" order by Grade desc";
        }
        else
        {
            s+=sql_add_order();
        }
        showTable_info(controller.selectQuery(s));

        content.repaint();
    }

    private void teacher_admin_query_grade_analyze_exe() throws SQLException {
        content.remove(scrollPane);
        content.repaint();
        jTable.removeAll();
        content.add(scrollPane);
        content.repaint();
        for(int i=0;i<3;i++)
        {
            if( queryPanelText[i]==null || queryPanelText[i].getText() == null)
            {
                SelectInfo[i] = "";
                continue;
            }
            SelectInfo[i] = queryPanelText[i].getText();
        }
        String sql = select(SelectInfo);
        String s="";
        if (sql.isEmpty())
        {
            s+="select student.Sno,Sname,ClassName,AVG(Grade) AVG_grade  " +
                    " from course,sc,student,class,dept " +
                    "where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo";
        }else
        {
            s+="select student.Sno,Sname,ClassName,AVG(Grade) AVG_grade" +
                    " from course,sc,student,class,dept " +
                    "where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo and "+sql;
            ;
        }
        if (SelectInfo[2].isEmpty()){
            if (classSelectbox.getSelectedItem().toString()!="--班 级--")
            {
                s+=" and ClassName ='"+classSelectbox.getSelectedItem().toString()+"'";
            }
        }
        if (deptSelectBox.getSelectedItem().toString()!="-------系  部-------")
        {
            s+=" and DeptName='"+deptSelectBox.getSelectedItem().toString()+"'";
        }
//        if (passRb.isSelected())
//        {
//            s+=" and Grade<60";
//        }
//        if (courseSelectbox.getSelectedItem()!="--------课 程--------"){
//            s+=" and Cname='"+courseSelectbox.getSelectedItem().toString()+"'";
//        }
        s+="  group by student.Sno";
        if (orderSelectbox.getSelectedItem().toString()=="升序")
        {
            s+=" order by Grade asc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="降序 "){
            s+=" order by Grade desc";
        }
        else
        {
            s+=sql_add_order();
        }


        System.out.println(s);
        showTable_info(controller.selectQuery(s));

        content.repaint();
    }


    private void teacher_admin_query_bujige_grade_analyze_exe() throws SQLException {
        content.remove(scrollPane);
        content.repaint();
        jTable.removeAll();
        content.add(scrollPane);
        content.repaint();
        for(int i=0;i<3;i++)
        {
            if( queryPanelText[i]==null || queryPanelText[i].getText() == null)
            {
                SelectInfo[i] = "";
                continue;
            }
            SelectInfo[i] = queryPanelText[i].getText();
        }
        String sql = select(SelectInfo);
        String s="";
        if (sql.isEmpty())
        {
            s+="select student.Sno,Sname,ClassName,course.Cname,sc.Grade " +
                    " from course,sc,student,class,dept " +
                    "where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo and sc.Grade<60 ";
        }else
        {
            s+="select student.Sno,Sname,ClassName,course.Cname,sc.Grade " +
                    " from course,sc,student,class,dept " +
                    "where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo and sc.Grade<60 and  "+sql;
            ;
        }
        if (SelectInfo[2].isEmpty()){
            if (classSelectbox.getSelectedItem().toString()!="--班 级--")
            {
                s+=" and ClassName ='"+classSelectbox.getSelectedItem().toString()+"'";
            }
        }
        if (deptSelectBox.getSelectedItem().toString()!="-------系  部-------")
        {
            s+=" and DeptName='"+deptSelectBox.getSelectedItem().toString()+"'";
        }
//        if (passRb.isSelected())
//        {
//            s+=" and Grade<60";
//        }
//        if (courseSelectbox.getSelectedItem()!="--------课 程--------"){
//            s+=" and Cname='"+courseSelectbox.getSelectedItem().toString()+"'";
//        }

        if (orderSelectbox.getSelectedItem().toString()=="升序")
        {
            s+=" order by Grade asc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="降序 "){
            s+=" order by Grade desc";
        }
        else
        {
            s+=sql_add_order();
        }


        System.out.println(s);
        showTable_info(controller.selectQuery(s));

        content.repaint();
    }


    private void teacher_admin_query_clsss_grade_analyze_exe() throws SQLException {
        content.remove(scrollPane);
        content.repaint();
        jTable.removeAll();
        content.add(scrollPane);
        content.repaint();
//        for(int i=0;i<3;i++)
//        {
//            if( queryPanelText[i]==null || queryPanelText[i].getText() == null)
//            {
//                SelectInfo[i] = "";
//                continue;
//            }
//            SelectInfo[i] = queryPanelText[i].getText();
//        }
//        String sql = select(SelectInfo);
        String sql="";
        String s="";
        if (sql.isEmpty())
        {
            s+="select class.ClassNo, ClassName,avg(Grade) AVG_grade ,sum(grade) total\n" +
                    "from course,sc,student,class,dept where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo ";
        }else
        {
            s+="select class.ClassNo, ClassName,avg(Grade) AVG_grade ,sum(grade) total\n" +
                    "from course,sc,student,class,dept where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo  and "+sql;
        }

            if (classSelectbox.getSelectedItem().toString()!="--班 级--")
            {
                s+=" and ClassName ='"+classSelectbox.getSelectedItem().toString()+"'";
            }

        if (deptSelectBox.getSelectedItem().toString()!="-------系  部-------")
        {
            s+=" and DeptName='"+deptSelectBox.getSelectedItem().toString()+"'";
        }
//        if (passRb.isSelected())
//        {
//            s+=" and Grade<60";
//        }
//        if (courseSelectbox.getSelectedItem()!="--------课 程--------"){
//            s+=" and Cname='"+courseSelectbox.getSelectedItem().toString()+"'";
//        }
            s+="  group by class.ClassNo";
        if (orderSelectbox.getSelectedItem().toString()=="升序")
        {
            s+=" order by AVG_grade asc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="降序 "){
            s+=" order by AVG_grade desc";
        }
        else
        {
            s+=sql_add_order();
        }


        System.out.println(s);
        showTable_info(controller.selectQuery(s));

        content.repaint();
    }

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
       try {
           Double.parseDouble(str);
       }
       catch (NumberFormatException e)
       {
           return false;
       }
       return true;
    }

    private void delete_exe(int row,int type)
    {
        if (type==TABLE_TYPE_STUDENT)
        {
            String Sno=(String) jTable.getValueAt(row,0);
            String sql="delete from sc where Sno='"+Sno+"'";
            try {
                controller.updateQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sql="delete from student where Sno='"+Sno+"'";
            try {
                controller.updateQuery(sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,"删除失败");
            }
            JOptionPane.showMessageDialog(this,"删除成功");
            try {
                teacher_admin_query_exe();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (type==TABLE_TYPE_SC)
        {
            String Sno=(String) jTable.getValueAt(row,0);
            String Cname=(String) jTable.getValueAt(row,3);
            System.out.println(Sno);
            String sql="delete from sc where Sno='"+Sno+"' and Cno=(select Cno from course where Cname='"+Cname+"')";
            System.out.println(sql);
            try {
                controller.updateQuery(sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,"删除失败");
            }
            JOptionPane.showMessageDialog(this,"删除成功");
            try {
                teacher_admin_query_grade_exe();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(type==TABLE_TYPE_COURSE)
        {
            String Cno=(String) jTable.getValueAt(row,0);
            String sql="delete from course where Cno='"+Cno+"'";
            try {
                controller.updateQuery(sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,"删除失败");
            }
            JOptionPane.showMessageDialog(this,"删除成功");
            try {
                teacher_admin_query_course_delete_exe();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(type==TABLE_TYPE_CLASS)
        {
            String ClassNo=(String) jTable.getValueAt(row,0);
            String sql="delete from class where ClassNo='"+ClassNo+"'";
            try {
                controller.updateQuery(sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,"删除失败");
            }
            JOptionPane.showMessageDialog(this,"删除成功");
            try {
                teacher_admin_query_class_delete_exe();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private int getTableType(LinkedList<String> tableNamelist)
    {
        if(tableNamelist.contains("Ssex"))
        {
            return TABLE_TYPE_STUDENT;
        }
        else if (tableNamelist.contains("ClassNo"))
        {
            return TABLE_TYPE_CLASS;
        }else if (tableNamelist.contains("CpName") && tableNamelist.contains("Ccredit"))
        {
            return TABLE_TYPE_COURSE;
        }
        else if(tableNamelist.contains("Grade") && tableNamelist.contains("Cname")){
            return TABLE_TYPE_SC;
        }
        return 0;
    }

    /**
     * 课程查询
     * @throws SQLException
     */
    private void teacher_admin_query_course_exe() throws SQLException {
        content.remove(scrollPane);
        content.repaint();
        jTable.removeAll();
        content.add(scrollPane);
        content.repaint();
        for(int i=0;i<queryPanelText.length;i++)
        {
            if(queryPanelText[i].getText() == null)
            {
                SelectInfo[i] = "";
                continue;
            }
            SelectInfo[i] = queryPanelText[i].getText();
        }
        String sql = select(SelectInfo);
        String s="";
        if (sql.isEmpty())
        {
            s+="select ifnull(student.Sno,'无') Sno,ifnull(Sname,'无') Sname,course.Ccredit,course.Cname,ifnull(course.Cpno,'无') Cpno,ifnull(temp.Cname,'无') CpName " +
                    "from ((course left join education.course as temp on course.Cpno = temp.Cno) left join sc on sc.Cno = course.Cno) left join education.student on sc.Sno = student.Sno";

        }else
        {
            s+="select ifnull(student.Sno,'无') Sno,ifnull(Sname,'无') Sname,course.Ccredit,course.Cname,ifnull(course.Cpno,'无') Cpno,ifnull(temp.Cname,'无') CpName " +
                    "from ((course left join education.course as temp on course.Cpno = temp.Cno) left join sc on sc.Cno = course.Cno) left join education.student on sc.Sno = student.Sno where "+sql;
        }
        if (courseSelectbox.getSelectedItem()!="--------课 程--------")
        {
            s="select ifnull(student.Sno,'无') Sno,ifnull(Sname,'无') Sname,course.Ccredit,course.Cname,ifnull(course.Cpno,'无') Cpno,ifnull(temp.Cname,'无') CpName " +
                    " from course left join education.course as temp on course.Cpno = temp.Cno where course.Cname='"+courseSelectbox.getSelectedItem().toString()+"'";
        }
        System.out.println(s);
        showTable_info(controller.selectQuery(s));

    }

    /**
     * 只查询课程信息
     * @throws SQLException
     */
    private void teacher_admin_query_course_delete_exe() throws SQLException {
        content.remove(scrollPane);
        content.repaint();
        jTable.removeAll();
        content.add(scrollPane);
        content.repaint();
        for(int i=0;i<queryPanelText.length;i++)
        {
            if(queryPanelText[i].getText() == null)
            {
                SelectInfo[i] = "";
                continue;
            }
            SelectInfo[i] = queryPanelText[i].getText();
        }
        String sql = select(SelectInfo);
        String s="";
        if (sql.isEmpty())
        {
            s+="select distinct course.Cno,course.Cname,course.Ccredit,ifnull(course.Cpno,'无') Cpno,ifnull(temp.Cname,'无') CpName " +
                    "from ((course left join education.course as temp on course.Cpno = temp.Cno) left join sc on sc.Cno = course.Cno) left join education.student on sc.Sno = student.Sno";

        }else
        {
            s+="select distinct course.Cno,course.Cname,course.Ccredit,ifnull(course.Cpno,'无') Cpno,ifnull(temp.Cname,'无') CpName " +
                    "from ((course left join education.course as temp on course.Cpno = temp.Cno) left join sc on sc.Cno = course.Cno) left join education.student on sc.Sno = student.Sno where "+sql;

        }
        if (courseSelectbox.getSelectedItem()!="--------课 程--------")
        {
            s="select distinct course.Cno,course.Cname,course.Ccredit,ifnull(course.Cpno,'无') Cpno,ifnull(temp.Cname,'无') CpName " +
                    " from course left join education.course as temp on course.Cpno = temp.Cno where course.Cname='"+courseSelectbox.getSelectedItem().toString()+"'";
        }
        System.out.println(s);
        showTable_info(controller.selectQuery(s));

    }

    /**
     * 班级查询
     * @throws SQLException
     */
    private void teacher_admin_query_class_exe() throws SQLException {
        content.remove(scrollPane);
        content.repaint();
        jTable.removeAll();
        content.add(scrollPane);
        content.repaint();
        for(int i=0;i<queryPanelText.length;i++)
        {
            if(queryPanelText[i].getText() == null)
            {
                SelectInfo[i] = "";
                continue;
            }
            SelectInfo[i] = queryPanelText[i].getText();
        }
        String sql = select(SelectInfo);
        String s="";
        if (sql.isEmpty())
        {
            s+="select Sno,Sname,class.ClassNo,ClassName,DeptName\n" +
                    "from class,dept,student where student.ClassNo=class.ClassNo and dept.DeptNo=class.DeptNo ";

        }else
        {
            s+="select Sno,Sname,class.ClassNo,ClassName,DeptName\n" +
                    "from class,dept,student where student.ClassNo=class.ClassNo and dept.DeptNo=class.DeptNo and "+sql;
        }
        if (SelectInfo[2].isEmpty()){
            if (classSelectbox.getSelectedItem().toString()!="--班 级--")
            {
                s+=" and ClassName ='"+classSelectbox.getSelectedItem().toString()+"'";
            }
        }
        if (deptSelectBox.getSelectedItem().toString()!="-------系  部-------")
        {
            s+=" and DeptName='"+deptSelectBox.getSelectedItem().toString()+"'";
        }
        if (orderSelectbox.getSelectedItem().toString()=="升序")
        {
            s+=" order by ClassName asc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="降序"){
            s+=" order by ClassName desc";
        }else {
            s+=sql_add_order();
        }

        System.out.println(s);
        showTable_info(controller.selectQuery(s));
    }

    private void teacher_admin_query_class_delete_exe() throws SQLException {
        content.remove(scrollPane);
        content.repaint();
        jTable.removeAll();
        content.add(scrollPane);
        content.repaint();
        for(int i=0;i<queryPanelText.length;i++)
        {
            if(queryPanelText[i].getText() == null)
            {
                SelectInfo[i] = "";
                continue;
            }
            SelectInfo[i] = queryPanelText[i].getText();
        }
        String sql = select(SelectInfo);
        String s="";
        if (sql.isEmpty())
        {
            s+="select distinct class.ClassNo,ClassName,DeptName\n" +
                    "from class left join student on student.ClassNo=class.ClassNo,dept where dept.DeptNo=class.DeptNo ";

        }else
        {
            s+="select distinct class.ClassNo,ClassName,DeptName\n" +
                    "from class left join student on student.ClassNo=class.ClassNo,dept where  dept.DeptNo=class.DeptNo and "+sql;
        }
        if (SelectInfo[2].isEmpty()){
            if (classSelectbox.getSelectedItem().toString()!="--班 级--")
            {
                s+=" and ClassName ='"+classSelectbox.getSelectedItem().toString()+"'";
            }
        }
        if (deptSelectBox.getSelectedItem().toString()!="-------系  部-------")
        {
            s+=" and DeptName='"+deptSelectBox.getSelectedItem().toString()+"'";
        }
        if (orderSelectbox.getSelectedItem().toString()=="升序")
        {
            s+=" order by ClassName asc";
        }
        else if (orderSelectbox.getSelectedItem().toString()=="降序"){
            s+=" order by ClassName desc";
        }else {
            s+=sql_add_order();
        }

        System.out.println(s);
        showTable_info(controller.selectQuery(s));
    }




    /**
     * 数据更新操作
     * @param courseName
     * @param Sno
     * @param colname
     * @param val
     * @return
     * @throws SQLException
     */
    private boolean update_exe(String courseName,String Sno,String colname,String val) throws SQLException {
        if(colname=="Grade")
        {
            if (isNumeric(val)) {
                String sql = "update sc set Grade='" + val.trim() + "' where Sno='" + Sno + "' and Cno=(select Cno from course where Cname='" + courseName + "')";
                System.out.println(sql);
                int i = controller.updateQuery(sql);
            }else {
                JOptionPane.showMessageDialog(this,"数据类型错误");
                this.tableModel.setValueAt(editValue,row,col);
            }return true;
        }
        else if (colname=="Sname" || colname=="Sage" || colname=="Ssex")
        {
            String sql = "update student set "+colname+"='"+val+"' where Sno='"+Sno+"'";
            try{
                controller.updateQuery(sql);
            }catch (SQLException e)
            {
                JOptionPane.showMessageDialog(this,"数据错误");
                this.tableModel.setValueAt(editValue,row,col);
            }return true;
        }else if (colname=="ClassName")
        {
            String sql ="update student set ClassNo=(select ClassNo from class where ClassName = '"+val+"') where Sno='"+Sno+"'";
            try{
                controller.updateQuery(sql);
            }catch (SQLException e)
            {
                JOptionPane.showMessageDialog(this,"修改失败");
                this.tableModel.setValueAt(editValue,row,col);
            }
            this.jTable.repaint();
            return true;
        }
        else if (colname=="Sno") {
           String sql = "update student set " + colname + "='" + val + "' where Sno='" + editValue + "'";
            System.out.println(sql);
            try {
                controller.updateQuery(sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "数据错误");
                this.tableModel.setValueAt(editValue, row, col);
            }
            return true;
        }else if(colname=="Ccredit" || colname=="Cpno" )
        {
            String sql="";
            if (val.equals("无"))
            {
                sql = "update course set " + colname + "= null where Cname = '"+courseName+"'";
                System.out.println(sql);
            }
            else {
                sql = "update course set " + colname + "='" + val + "' where Cname = '" + courseName + "'";
                System.out.println(sql);
            }
            try {
                controller.updateQuery(sql);
            } catch (SQLException e) {
               // JOptionPane.showMessageDialog(this, "数据错误");
                this.tableModel.setValueAt(editValue, row, col);
            }
            return true;
        }
        else if (colname=="Cname")
        {
            String sql = "update course set " + colname + "='" + val + "' where Cname = '"+editValue+"'";
            System.out.println(sql);
            try {
                controller.updateQuery(sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "数据错误");
                this.tableModel.setValueAt(editValue, row, col);
            }
            return true;
        }

        return false;
    }


    /**
     * selectInfo 学号，姓名，班级号
     * 组成条件
     * @param selectInfo
     * @return
     */
    public String select(String[] selectInfo)
    {
        String sql="";
                if (!selectInfo[0].isEmpty()) {
                    sql += "student.Sno='" + selectInfo[0] + "'";
                    if (!selectInfo[1].isEmpty()) {
                        if (selectInfo[1].contains("%"))
                        {
                            sql+=" and "+"student.Sname like '"+selectInfo[1]+"'";
                        }
                        else sql += " and " + "student.Sname='" + selectInfo[1] + "'";
                        if (!selectInfo[2].isEmpty()) {

                            sql += " and " + "student.ClassNo='" + selectInfo[2] + "'";
                        }
                    }else
                    {
                        if (!selectInfo[2].isEmpty()) {
                            sql += " and " + "student.ClassNo='" + selectInfo[2] + "'";
                        }
                    }
                }
                else {
                        if (!selectInfo[1].isEmpty())
                        {
                            if (selectInfo[1].contains("%"))
                            {
                                sql+="student.Sname like '"+selectInfo[1]+"'";
                            }
                            else sql+="student.Sname='"+selectInfo[1]+"'";
                            if (!selectInfo[2].isEmpty())
                            {
                                sql+=" and "+"student.ClassNo='"+selectInfo[2]+"'";
                            }
                        }
                        else {
                            if (!selectInfo[2].isEmpty() )
                            {
                                sql+="student.ClassNo='"+selectInfo[2]+"'";
                            }
                        }
                    }
        System.out.println(sql);
        return sql;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() instanceof JButton)
        {
            if (actionEvent.getActionCommand()=="查询")
            {
                if (userType==USER_TYPE_STUDENT)
                {student_query_panel();}
                else
                {
                    try {
                        teacher_admin_query_panel();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(actionEvent.getActionCommand()=="最高总成绩")
            {
                try {
                    showTable_info(controller.selectQuery("select sc.Sno,student.Sname,ClassName,sum(Grade) max_grade\n" +
                            "from course,sc,student,class,dept\n" +
                            "where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo\n" +
                            "group by sc.Sno having max_grade =\n" +
                            "(select sum(Grade) sum\n" +
                            "from sc,student\n" +
                            "where  sc.Sno=student.Sno\n" +
                            "group by sc.Sno\n" +
                            "order by sum desc limit 1)"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="单科最高成绩")
            {
                try {
                    showTable_info(controller.selectQuery("select sc.Sno,student.Sname,Cname,max(Grade) max_grade\n" +
                            "from course,sc,student,class,dept\n" +
                            "where dept.DeptNo=class.DeptNo and sc.Sno=student.Sno and course.Cno=sc.Cno and student.ClassNo = class.ClassNo group by sc.Cno"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if (actionEvent.getActionCommand()=="班级成绩分析")
            {
                try {
                    teacher_admin_query_clsss_grade_analyze_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="不及格统计")
            {
                try {
                    teacher_admin_query_bujige_grade_analyze_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="学生成绩分析") {
                try {
                    teacher_admin_query_grade_analyze_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if (actionEvent.getActionCommand()=="新建班级")
            {
                try {
                    this.insertClass();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
                else if(actionEvent.getActionCommand()=="插入")
            {
                try {
                    teacher_admin_insert_panel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="个人信息查询")
            {
                try {
                        student_info_query_panel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if(actionEvent.getActionCommand()=="修改")
            {
                try {
                    teacher_admin_update_panel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if (actionEvent.getActionCommand()=="删 除")
            {
                delete_exe(this.row,getTableType(tableNamelist));
            }
            else if (actionEvent.getActionCommand()=="课程 查询 删除面板")
            {
                try {
                    teacher_admin_query_course_delete_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
                else if(actionEvent.getActionCommand()=="考试成绩查询")
            {
                try {
                    student_grade_query_panel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
                else if(actionEvent.getActionCommand()=="新建成绩")
            {
                try {
                    new GradeInsertFrame(controller);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="课程信息查询")
            {
                try {
                    student_course_query_panel_whole();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if (actionEvent.getActionCommand()=="查看先修课信息")
            {
                try {
                    student_course_query_panel_Pre();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="返 回")
            {
                try {
                    student_course_query_panel_whole();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="修改_课程查询")
            {
                try {
                    teacher_admin_query_course_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="分析")
            {
                try {
                    if (userType==USER_TYPE_STUDENT){
                    student_analyze_panel();}
                    else {
                    teacher_admin_analyze_panel();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="查 询")
            {
                try {
                    teacher_admin_query_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if (actionEvent.getActionCommand()=="新建课程")
            {
                try {
                    insertCourse();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if (actionEvent.getActionCommand()=="成绩查询")
            {
                try {
                    teacher_admin_query_grade_pane();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="成绩 查询")
            {
                try {
                    teacher_admin_query_grade_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="班级查询")
            {
                try {
                    teacher_admin_query_class_panel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="班级 查询_删除")
            {
                try {
                    teacher_admin_query_class_delete_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="课程查询")
            {
                try {
                    teacher_admin_query_course_panel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="课程 查询")
            {
                try {
                    teacher_admin_query_course_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="班级 查询")
            {
                try {
                    teacher_admin_query_class_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if (actionEvent.getActionCommand()=="修改_成绩查询")
            {
                try {
                    teacher_admin_query_grade_exe();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(actionEvent.getActionCommand()=="删除")
            {
                try {
                    teacher_admin_delete_panel();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if (actionEvent.getActionCommand()=="新建学生")
            {
                try {
                    insertStudent();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (actionEvent.getSource() instanceof JComboBox)
        {
            if (deptSelectBox.getSelectedItem()=="-------系  部-------")
            {
                this.classSelectbox.removeAllItems();
                this.classSelectbox.addItem("--班 级--");

            }
            else
            {
                String s = deptSelectBox.getSelectedItem().toString();

                ResultSet resultSet = controller.selectQuery("select DeptNo from dept where DeptName='" + s + "'");
                try {
                    resultSet.next();
                    if (classInsertfield[2]!=null){
                        this.classInsertfield[2].setText(resultSet.getString(1));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
    }

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
        if (mouseEvent.getSource()==side_buttons[0])
        {
            side_buttons[0].setBorder(BorderFactory.createRaisedBevelBorder());
        }else if (mouseEvent.getSource()==side_buttons[1])
        {
            side_buttons[1].setBorder(BorderFactory.createRaisedBevelBorder());
        }
        else if (mouseEvent.getSource()==side_buttons[2])
        {
            side_buttons[2].setBorder(BorderFactory.createRaisedBevelBorder());
        }
        else if (mouseEvent.getSource()==side_buttons[3])
        {
            side_buttons[3].setBorder(BorderFactory.createRaisedBevelBorder());
        }else if (mouseEvent.getSource()==analyze)
        {
            analyze.setBorder(BorderFactory.createRaisedBevelBorder());
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        if (mouseEvent.getSource()==side_buttons[0])
        {
            side_buttons[0].setBorder(null);
        }else if (mouseEvent.getSource()==side_buttons[1])
        {
            side_buttons[1].setBorder(null);
        }
        else if (mouseEvent.getSource()==side_buttons[2])
        {
            side_buttons[2].setBorder(null);
        }
        else if (mouseEvent.getSource()==side_buttons[3])
        {
            side_buttons[3].setBorder(null);
        }else if (mouseEvent.getSource()==analyze)
        {
            analyze.setBorder(null);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
            if (!listSelectionEvent.getValueIsAdjusting()) {
                //支持拖动多选
                row = jTable.getSelectedRow();
                // int[] cols = table.getSelectedColumns();//选中的列
               // System.out.println(rows);

                tableNamelist.clear();
                    for (int i=0;i<jTable.getColumnCount();i++)
                    {

                        System.out.println(nameTransReverse(jTable.getColumnName(i)));
                        tableNamelist.add(nameTransReverse(jTable.getColumnName(i)));
                    }
                System.out.println(getTableType(this.tableNamelist));
                    if (row!=-1) {
                        System.out.println("#方法一:\t" + jTable.getValueAt(row, 0) + "\t" + jTable.getValueAt(row, 1) + jTable.getColumnName(0) + "   sa");
                    }
            }
    }

    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {
        if(tableModelEvent.getType()==TableModelEvent.UPDATE)
        {

            this.col = tableModelEvent.getColumn();
            this.row = tableModelEvent.getFirstRow();


            if (col!=-1 && row!=-1)
            {
                System.out.println(tableModel.getValueAt(row,col));
                System.out.println(nameTransReverse(this.jTable.getColumnName(col)));
                System.out.println((String) this.tableModel.getValueAt(row,0));
                boolean b=false;
                try {
                    b = update_exe((String) this.tableModel.getValueAt(row, 3), (String) this.tableModel.getValueAt(row, 0), nameTransReverse(this.jTable.getColumnName(col)), (String) tableModel.getValueAt(row, col));

                } catch (SQLException e) {
                    //JOptionPane.showMessageDialog(this,"修改请求被拒绝");
                    tableModel.setValueAt(editValue,row,col);
                }
            }
        }
}
}
