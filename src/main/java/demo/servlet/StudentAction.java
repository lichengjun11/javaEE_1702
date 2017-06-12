package demo.servlet;

import com.mysql.jdbc.Driver;
import demo.util.Db;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lichengjun on 2017/6/12.
 */
@WebServlet(urlPatterns = "/student")
public class StudentAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            add(req, resp);
            return;  //  如果没有return，  执行完if语句会执行下面两句语句吗
        }
        req.setAttribute("message", "出了一点问题");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String date = req.getParameter("dob");

        java.sql.Connection Connection = Db.getConnection();
        PreparedStatement statement = null;
        String sql = "INSERT INTO db_javaee.student VALUE (NULL ,?,?,?)";
        try {
            if (Connection != null) {
                statement = Connection.prepareStatement(sql);
            } else {
                req.setAttribute("message", "连接出现问题");
                req.getRequestDispatcher("home.jsp").forward(req, resp);
                return;
            }
            statement.setString(1, name);
            statement.setString(2, gender);
            statement.setString(3, date);
            statement.executeUpdate();
            resp.sendRedirect("home.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(null, statement, Connection);
        }

    }

   protected void queryAll (HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM db_javaee.student ORDER BY id";

       try {
           if (connection != null) {
               preparedStatement = connection.prepareStatement(sql);
           }else {
               req.setAttribute("message","出现问题");
               req.getRequestDispatcher("home.jsp").forward(req,resp);
               return;
           }
           resultSet = preparedStatement.executeQuery();

       } catch (SQLException e) {
           e.printStackTrace();
       }finally {
           Db.close(resultSet,preparedStatement,connection);
       }


   }
        @Override
        protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            doPost(req, resp);
        }
    }
