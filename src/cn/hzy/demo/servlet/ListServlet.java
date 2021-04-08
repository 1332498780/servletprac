package cn.hzy.demo.servlet;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.Writer;
import java.sql.*;

public class ListServlet extends BaseServlet {

    private static final Log log = LogFactory.getLog(ListServlet.class);

    @Override
    public void init(){
        super.init();
    }

    protected void doGet(Connection connection, HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    protected void doPost(Connection connection, HttpServletRequest req, HttpServletResponse resp) {
        String condition = req.getParameter("condition");
        log.info("condition is:"+condition);
        Statement sta = null;
        try {
            if(condition!=null){
                String fromSql = "select * from bookinfo ";
                if(condition.equals("all")){
                    sta = connection.createStatement();
                    ResultSet resultSet = sta.executeQuery(fromSql);
                    printBookinfo(resultSet,resp.getWriter());
                }else if(condition.equals("precise")){
                    String name = req.getParameter("name");
                    String author = req.getParameter("author");
                    StringBuilder sb = new StringBuilder();
                    if(name != null){
                        sb.append(" where name = ?").append("'"+name+"'");
                    }
                    if(sb.length()==0){
                        if(author != null){
                            sb.append(" where author = ").append("'"+author+"'");
                        }
                    }else{
                        sb.append(" and author = ").append("'"+author+"'");
                    }
                    sta = connection.createStatement();
                    ResultSet resultSet = sta.executeQuery(fromSql+sb.toString());
                    printBookinfo(resultSet,resp.getWriter());
                }else if(condition.equals("keyword")){

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(sta!=null){
                try {
                    sta.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void printBookinfo(ResultSet rs, Writer writer){
        try {
            writer.write("<html><head><title>图书列表</title></head>");
            writer.write("<body>");
            writer.write("<table><tr><th>书名</th><th>作者</th><th>价格</th></tr>");
            while(rs.next()){
                writer.write("<tr>");
                writer.write("<td>"+rs.getString("name")+"</td>");
                writer.write("<td>"+rs.getString("author")+"</td>");
                writer.write("<td>"+rs.getFloat("price")+"</td>");
                writer.write("</tr>");
            }
            writer.write("</table>");
            writer.write("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
