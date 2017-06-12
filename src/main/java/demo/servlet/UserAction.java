package demo.servlet;

import demo.util.Db;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by lichengjun on 2017/6/12.
 */
@WebServlet(urlPatterns = "/user")
public class UserAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("login")) {
            login(req, resp);
        }
        if (action.equals("register")) {
            register(req,resp);
        }
        if (action.equals("logout")) {
            logout(req,resp);
        }

    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mobile = req.getParameter("mobile");
        String password = req.getParameter("password");

        java.sql.Connection Connection = Db.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {

            String sql = "SELECT * FROM db_javaee.user WHERE mobile = ? AND password = ?";
            if (Connection != null) {
                statement = Connection.prepareStatement(sql);
            } else {
                return;
            }
            statement.setString(1,mobile);
            statement.setString(2,password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                req.getSession().setAttribute("nick",resultSet.getString("nick"));
                resp.sendRedirect("home.jsp");
//                req.getRequestDispatcher("home.jsp").forward(req,resp);
            }else {
                req.setAttribute("message","用户名或密码错误");
//                req.getRequestDispatcher("index.jsp").forward(req,resp);
                resp.sendRedirect("index.jsp");
                // req.getRequestDispatcher("index.jsp").forward(req,resp);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally {
            Db.close(resultSet, statement,Connection);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nick = req.getParameter("nick").trim();
        String mobile = req.getParameter("mobile").trim();
        String password = req.getParameter("password");

        if (nick.length() == 0 || mobile.length() == 0 || password.length() == 0) {
            req.setAttribute("message","输入有误，请重新输入");
            req.getRequestDispatcher("signup.jsp").forward(req,resp);
        }

        String[] hobbies = req.getParameterValues("hobbies");
        String[] cities = req.getParameterValues("cities");

        java.sql.Connection connection = Db.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sqlnick = "SELECT * FROM db_javaee.user WHERE nick = ?";
            if(connection != null){
                statement = connection.prepareStatement(sqlnick);
            }else {
                req.getSession().setAttribute("sen","出现状况");
                req.getRequestDispatcher("signup.jsp").forward(req,resp);
                return;
            }
            statement.setString(1,nick);
            resultSet = statement.executeQuery();
            boolean isNickExist = resultSet.next();

            String sqlmobile = "SELECT * FROM db_javaee.user WHERE mobile = ?";
            statement = connection.prepareStatement(sqlmobile);
            statement.setString(1,mobile);
            resultSet = statement.executeQuery();
            boolean isMobileExist = resultSet.next();


            if (isNickExist) {
                req.setAttribute("message","昵称已经存在");
                req.getRequestDispatcher("signup.jsp").forward(req,resp);
            }else if (isMobileExist){
                req.getSession().setAttribute("message","手机号已经存在");
                req.getRequestDispatcher("signup.jsp").forward(req,resp);
            }else {
                String sql = "INSERT INTO db_javaee.user VALUE (NULL,?,?,?,?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1,nick);
                statement.setString(2,mobile);
                statement.setString(3,password);
                statement.setString(4, Arrays.toString(hobbies));
                statement.setString(5,Arrays.toString(cities));
                statement.executeUpdate();
                resp.sendRedirect("index.jsp");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Db.close(resultSet,statement,connection);
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.getSession().invalidate();
            resp.sendRedirect("index.jsp");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);// ??

    }
}
