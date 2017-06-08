<%@ page import="com.mysql.jdbc.Driver" %>
<%@ page import="com.mysql.jdbc.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="com.mysql.jdbc.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: lichengjun
  Date: 2017/6/7
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String nick = request.getParameter("nick");
    out.print(nick);
    String mobile = request.getParameter("mobile");
    out.print(mobile);
    String password = request.getParameter("password");
    out.print(password);


    new Driver();
    java.sql.Connection Connection = DriverManager.getConnection("jdbc:mysql:///?user=root&password=system");
    String sqlNick = "SELECT * FROM db_javaee.user WHERE nick=?";
    java.sql.PreparedStatement PreparedStatement = Connection.prepareStatement(sqlNick);
    PreparedStatement.setString(1,nick);
    ResultSet resultSet = PreparedStatement.executeQuery();
    boolean isNickExist = resultSet.next();

    String sqlmobile = "SELECT * FROM db_javaee.user WHERE mobile=?";
    PreparedStatement = Connection.prepareStatement(sqlmobile);
    PreparedStatement.setString(1,mobile);
    resultSet = PreparedStatement.executeQuery();
    boolean isMobileExist = resultSet.next();

    if (isNickExist) {
        request.setAttribute("message","昵称已经存在");
        request.getRequestDispatcher("signup.jsp").forward(request,response);
    } else if (isMobileExist){
        request.setAttribute("message","手机号已经存在");
        request.getRequestDispatcher("signup.jsp").forward(request,response);
    }
    else {


    String sql = "INSERT INTO db_javaee.user VALUE (NULL,?,?,?)";
    PreparedStatement = Connection.prepareStatement(sql);
    PreparedStatement.setString(1,nick);
    PreparedStatement.setString(2,mobile);
    PreparedStatement.setString(3,password);
    PreparedStatement.executeUpdate();

    response.sendRedirect("index.jsp");

    }

    resultSet.close();
    PreparedStatement.close();
    Connection.close();
%>
</body>
</html>
