<%--
  Created by IntelliJ IDEA.
  User: lichengjun
  Date: 2017/6/7
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>主页</h1>
<p><%=session.getAttribute("nick")%></p>
<%--request.getAttribute  返回键对应的值 如果键和值不存在就会返回null--%>
<a href="second.jsp">去往第二页</a>
<p><a href="logout"></a></p>

<a href="logout.jsp">注销</a>
<a href="index.jsp">回到登录页面</a>
</body>
</html>
