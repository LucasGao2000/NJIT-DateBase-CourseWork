package sql;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import gui.LoginDialog;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class MainController {
    private Statement statement;
    private Connection connection;
    private DataSource ds;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String user;
    private String password;
    private String url;
    private final String dataBaseName ="education";
    private InputStream resourceAsStream = MainController.class.getResourceAsStream("/druid.properties");
    private JdbcTemplate template;
    private PreparedStatement preparedStatement;

    public void connect() throws Exception {
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        template = new JdbcTemplate(dataSource);
        connection = dataSource.getConnection();
    }

    public void connect(String[] loginInfo) throws Exception {
        user = loginInfo[0];
        password = loginInfo[1];

        Properties properties = new Properties();
        properties.load(resourceAsStream);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);


        url = "jdbc:mysql://"+loginInfo[2]+":"+loginInfo[3]+"/"+loginInfo[4];

        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
        template = new JdbcTemplate(dataSource);

    }

    public void connect(String user,String password) throws SQLException {
        this.user = user;
        this.password = password;
        url = "jdbc:mysql://"+"localhost"+":"+"3306"+"/"+dataBaseName;
        connection = DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
    }

    public int updateQuery(String updateSQL) throws SQLException {
        return statement.executeUpdate(updateSQL);
    }
    public ResultSet selectQuery(String sql){
        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            new JOptionPane("数据查询异常！");
        }
        return resultSet;
    }


    public void insert_student(String Sno,String Sname,String Ssex,String Sage,String ClassNo) throws SQLException {
        preparedStatement= connection.prepareStatement("insert student(Sno, Sname, Ssex, Sage, ClassNo) values (?,?,?,?,?)");
        preparedStatement.setString(1,Sno);
        preparedStatement.setString(2,Sname);
        preparedStatement.setString(3,Ssex);
        preparedStatement.setString(4,Sage);
        preparedStatement.setString(5,ClassNo);
        System.out.println(preparedStatement.toString());
        preparedStatement.executeUpdate();
    }

    public void insert_class(String ClassNo,String ClassName,String DeptNo) throws SQLException {
        preparedStatement= connection.prepareStatement("insert class(ClassNo, ClassName, DeptNo) VALUES (?,?,?)");
        preparedStatement.setString(1,ClassNo);
        preparedStatement.setString(2,ClassName);
        preparedStatement.setString(3,DeptNo);
        preparedStatement.executeUpdate();
    }
    public void insert_course(String Cno,String Cname,String Cpno,String Ccredit) throws SQLException {
        preparedStatement=connection.prepareStatement("insert course(cno, cname, cpno, ccredit) VALUES (?,?,?,?)");
        preparedStatement.setString(1,Cno);
        preparedStatement.setString(2,Cname);
        preparedStatement.setString(3,Cpno);
        preparedStatement.setString(4,Ccredit);
        System.out.println(preparedStatement.toString());
        preparedStatement.executeUpdate();
    }
    public void insert_grade(String Sno,String Cno,String Grade) throws SQLException {
        preparedStatement=connection.prepareStatement("insert sc(Sno, Cno, Grade) VALUES (?,?,?)");
        preparedStatement.setString(1,Sno);
        preparedStatement.setString(2,Cno);
        preparedStatement.setString(3,Grade);
        preparedStatement.executeUpdate();

    }

    public ResultSet login(String user,String password) throws SQLException {
        preparedStatement = connection.prepareStatement("select userType from users where user=? and password=?");
        preparedStatement.setString(1,user);
        preparedStatement.setString(2,user);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;


    }

    public MainController() {

    }
}
