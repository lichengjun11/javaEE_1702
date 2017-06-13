<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.List" %>
<%@ page import="demo.model.Student" %><%--
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

<%--request.getAttribute  返回键对应的值 如果键和值不存在就会返回null--%>
<a href="second.jsp">去往第二页</a>
<p><a href="logout"></a></p>

<%
    if (session.getAttribute("nick") == null){
        response.sendRedirect("default.jsp");
    }
%>
<%=session.getAttribute("nick")%>
<a href="user?action=logout">注销</a>
<a href="default.jsp">回到登录页面</a>
<hr>
<form action="student" method="post">
    <input type="hidden" name="action" value="add">
    <input type="text" name="name" placeholder="姓名" ><br>
    <input type="text" name="gender" placeholder="性别"><br>
    <input type="date" name="dob" placeholder="出生日期"><br>
    <input type="submit" value="添加">
    <a href=""></a>
</form>
<hr>
<table border="1">
    <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>性别</th>
        <th>出生日期</th>
        <th colspan="2">操作</th>
        <%--colspan="2"  跨列--%>
    </tr>
<%
    List<Student> resultSet = (List<Student>)session.getAttribute("students");
    for (Student student : resultSet) {
        out.print("<tr>" + "<td>"+student.getId()+"</td>" + "<td>"+student.getName()+"</td>" +"<td>"+student.getGender()+"</td>" +"<td>"+student.getDob()+"</td>" + "<td><a href='student?action=queryById&id="+student.getId()+"'>编辑</a></td>"+"<td><a href='student?action=remove&id="+student.getId()+"'>删除</a></td>"+"</tr>");
    }
    %>
</table>
<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
        out.print(message);
    }

%>

</body>
</html>
