<%--
  Created by IntelliJ IDEA.
  User: lichengjun
  Date: 2017/6/8
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    session.invalidate();
//    使session 失效，网页注销的方式
    response.sendRedirect("index.jsp");
%>
</body>
</html>
