package demo.servlet;

import demo.util.Db;
import org.omg.CORBA.Request;
import sun.rmi.transport.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lichengjun on 2017/6/9.
 */
@WebServlet(urlPatterns = "/login")
public class LonginServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                resp.sendRedirect("index.jsp");
//                req.getRequestDispatcher("index.jsp").forward(req,resp);
            }else {
                req.getSession().setAttribute("message","用户名或密码错误");
                resp.sendRedirect("default.jsp");
//                req.getRequestDispatcher("default.jsp").forward(req,resp);
            }
    }
    catch (SQLException e){
            e.printStackTrace();
    }finally {
            Db.close(resultSet, statement,Connection);
        }
}
        }
