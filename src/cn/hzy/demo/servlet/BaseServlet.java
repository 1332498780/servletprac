package cn.hzy.demo.servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public abstract class BaseServlet extends HttpServlet {

    private static boolean isInitDb = false;

    private String driver;
    private String url;
    private String username;
    private String password;

    @Override
    public void init(){
        synchronized (this){
            if(!isInitDb){
                initDb();
                isInitDb = true;
            }
        }
    }

    private void initDb(){
        ServletContext context = getServletContext();
        driver = context.getInitParameter("db.driver");
        url = context.getInitParameter("db.url");
        username = context.getInitParameter("db.username");
        password = context.getInitParameter("db.password");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
//            conn = DriverManager.getConnection(url, username, password);
            conn = getConnection1();
            ResultSet resultSe = conn.createStatement().executeQuery("select count(*) from information_schema.schemata where schema_name='bookdb'");
            if(resultSe.next()){
                long count = resultSe.getLong(1);
                if(count == 0){
                    createDb(conn);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createDb(Connection conn){
        Statement statement = null;
        try {
            statement = conn.createStatement();
//            statement.executeUpdate("drop database bookdb");
            statement.executeUpdate("create database bookdb");
            statement.executeUpdate("use bookdb");

            statement.executeUpdate("create table bookinfo(id int primary key,name varchar(64),author varchar(32),create_date date,price float)");

            statement.addBatch("insert into bookinfo values(1,'飞翔与坠落','张宏','2020-03-10 08:00:00',23.59)");
            statement.addBatch("insert into bookinfo values(2,'春风绿的时候','文文','2020-03-01 12:12:00',30.5)");
            statement.addBatch("insert into bookinfo values(3,'大妈手中的萝卜','秦风山','2020-03-29 08:00:00',59)");
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(statement!=null){
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void doGet(Connection connection,HttpServletRequest req,HttpServletResponse resp);

    protected abstract void doPost(Connection connection,HttpServletRequest req,HttpServletResponse resp);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        //获得连接
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Connection conn = null;
        try{
            conn = getConnection1();
            doGet(conn,request,response);
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        //获得连接
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url,username,password);
            doPost(conn,request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Connection getConnection(){
        try {
            return DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection1(){
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/bookdb");
            return dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
