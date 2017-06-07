<%@ page import="com.mysql.jdbc.Driver" %>
<%@ page import="com.mysql.jdbc.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="com.mysql.jdbc.PreparedStatement" %><%--
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
    String sql = "INSERT INTO db_javaee.user VALUE (NULL,?,?,?)";
    java.sql.PreparedStatement PreparedStatement = Connection.prepareStatement(sql);
    PreparedStatement.setString(1,nick);
    PreparedStatement.setString(2,mobile);
    PreparedStatement.setString(3,password);
    PreparedStatement.executeUpdate();

    PreparedStatement.close();
    Connection.close();

    response.sendRedirect("index.jsp");


%>
</body>
</html>
