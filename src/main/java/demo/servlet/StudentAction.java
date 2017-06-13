package demo.servlet;

import com.mysql.jdbc.Driver;
import demo.model.Student;
import demo.util.Db;
import sun.swing.BakedArrayList;

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
import java.util.ArrayList;
import java.util.List;

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
        if ("queryAll".equals(action)) {
            queryAll(req, resp);
            return;
        }
        req.setAttribute("message", "出了一点问题");
        req.getRequestDispatcher("default.jsp").forward(req, resp);
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
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                return;
            }
            statement.setString(1, name);
            statement.setString(2, gender);
            statement.setString(3, date);
            statement.executeUpdate();
            resp.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(null, statement, Connection);
        }

    }

    private void queryAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM db_javaee.student ORDER BY id";

        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            } else {
                req.setAttribute("message", "出现问题");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                return;
            }
            resultSet = preparedStatement.executeQuery();// 先把resultset 的值取出来，放到集合里
            List<Student> students = new ArrayList<>();
            while (resultSet.next()){

            Student student = new Student(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("gender"),
                    resultSet.getString("dob"));
                students.add(student);
            }
            req.getSession().setAttribute("students", students);
            resp.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(resultSet, preparedStatement, connection);
        }  //     resultset  一旦被关闭，里面内容就都没有了


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
