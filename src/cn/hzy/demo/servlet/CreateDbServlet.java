package cn.hzy.demo.servlet;

import javax.naming.spi.DirectoryManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库初始化servlet
 */
public class CreateDbServlet extends BaseServlet {

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void doGet(Connection conn,HttpServletRequest req,HttpServletResponse resp){
//        Statement statement = null;
//        try {
//            statement = conn.createStatement();
//            statement.executeUpdate("drop database bookdb");
//            statement.executeUpdate("create database bookdb");
//            statement.executeUpdate("use bookdb");
//
//            statement.executeUpdate("create table bookinfo(id int primary key,name varchar(64),author varchar(32),create_date date,price float)");
//
//            statement.addBatch("insert into bookinfo values(1,'飞翔与坠落','张宏','2020-03-10 08:00:00',23.59)");
//            statement.addBatch("insert into bookinfo values(2,'春风绿的时候','文文','2020-03-01 12:12:00',30.5)");
//            statement.addBatch("insert into bookinfo values(3,'大妈手中的萝卜','秦风山','2020-03-29 08:00:00',59)");
//            statement.executeBatch();
//
//            PrintWriter writer = resp.getWriter();
//            writer.write("数据库创建完成！！");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if(statement!=null){
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    protected void doPost(Connection connection, HttpServletRequest req, HttpServletResponse resp) {

    }

}
