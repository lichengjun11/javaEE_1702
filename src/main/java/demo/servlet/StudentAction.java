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
        if ("queryById".equals(action)) {
            queryById(req, resp);
            return;
        }
        if ("modify".equals(action)) {
            modify(req, resp);
            return;
        }
        if ("remove".equals(action)) {
            remove(req, resp);
            return;
        }
        if ("batchRemove".equals(action)) {
            batchRemove(req, resp);
            return;
        }
        req.setAttribute("message", "连接出现问题");
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
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                return;
            }
            statement.setString(1, name);
            statement.setString(2, gender);
            statement.setString(3, date);
            statement.executeUpdate();
            resp.sendRedirect("student?action=queryAll");
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
//    编辑分两步操作，先根据ID 查询当前一条的所有数据，然后再update
    private void queryById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM db_javaee.student WHERE id = ?";
        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            }else {
                req.setAttribute("message", "出现问题");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                return;
            }
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Student student = new Student(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("gender"),
                    resultSet.getString("dob")
            );
            req.getSession().setAttribute("student",student);
            resp.sendRedirect("edit.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Db.close(resultSet,preparedStatement,connection);
        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String dob = req.getParameter("dob");
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "UPDATE db_javaee.student SET name = ? , gender = ? , dob = ? WHERE id = ?";
        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            } else {
                req.setAttribute("message", "出现问题");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                return;
            }
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);
            preparedStatement.setString(3,dob);
            preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();
            resp.sendRedirect("student?action=queryAll");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Db.close(null,preparedStatement,connection);
        }
    }

    private void removeById(int id){
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM db_javaee.student WHERE id = ?";
        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            }
            if (preparedStatement != null) {
                preparedStatement.setInt(1,id);
            }
            if (preparedStatement != null) {
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Db.close(null,preparedStatement,connection);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
            removeById(id);
            resp.sendRedirect("student?action=queryAll");
    }

    private void batchRemove(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] ids = req.getParameterValues("ids");
        for (String idString : ids) {
            int id = Integer.parseInt(idString);
        removeById(id);
        }
        resp.sendRedirect("student?action=queryAll");
    }
    private boolean isConnected(Connection connection,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (connection == null) {
            req.setAttribute("message", "出现问题");
            req.getRequestDispatcher("default.jsp").forward(req, resp);
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
