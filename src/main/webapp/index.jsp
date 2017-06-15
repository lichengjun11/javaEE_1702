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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
    <script>
        function del() {
            return confirm('DEL?');
        }
    </script>
</head>
<body>
<h1>主页</h1>

<%--request.getAttribute  返回键对应的值 如果键和值不存在就会返回null--%>
<a href="second.jsp">去往第二页</a>
<p><a href="logout"></a></p>

<%--if后面的test是必选项--%>
<c:if test="${sessionScope.nick eq null}">
    <c:redirect url="default.jsp"/>
</c:if>
<p>${sessionScope.nick}</p>
<%=session.getAttribute("nick")%>

<a href="user?action=logout">注销</a>
<a href="default.jsp">回到登录页面</a>
<hr>
<form action="student" method="post">
    <input type="hidden" name="action" value="add">
    <input type="text" name="name" placeholder="姓名"><br>
    <input type="text" name="gender" placeholder="性别"><br>
    <input type="date" name="dob" placeholder="出生日期"><br>
    <input type="submit" value="添加">
    <a href=""></a>
</form>
<hr>

    <form action="student" method="post">
        <input type="hidden" name="action" value="batchRemove">
<table border="1">
    <c:choose>
        <c:when test="${sessionScope.students[0] eq null}">当前没有记录</c:when>
        <%--<c:when test="${fn:length(sessionScope).students eq 0}">当前没有记录</c:when>--%>
        <%--choose没有任何属性，它是套在when test 和  otherwise外面的--%>
        <%--when test  当 ... 的时候 ，when test  可以有多个  otherwise  否则--%>
        <c:otherwise>
    <tr>
        <th>序号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>出生日期</th>
        <th colspan="2">操作</th>
        <%--colspan="2"  跨列--%>
    </tr>
        </c:otherwise>
    </c:choose>
    <%--<%--%>
    <%--List<Student> resultSet = (List<Student>)session.getAttribute("students");--%>
    <%--for (Student student : resultSet) {--%>
    <%--out.print("<tr>" + "<td>"+student.getId()+"</td>" + "<td>"+student.getName()+"</td>" +"<td>"+student.getGender()+"</td>" +"<td>"+student.getDob()+"</td>" + "<td><a href='student?action=queryById&id="+student.getId()+"'>编辑</a></td>"+"<td><a href='student?action=remove&id="+student.getId()+"'>删除</a></td>"+"</tr>");--%>
    <%--}--%>
    <%--%>--%>

    <c:forEach var="student" items="${sessionScope.students}" varStatus="vs" >
        <%--step  步长  默认值是1 begin 起始于第几条 ，默认是0--%>
        <%--var 变量   items  项目   students 对应模型类里面域的名字 varStatus  变量的状态--%>
        <%--forEach自动会对students进行迭代，把每次迭代出来的一个student对象赋值给var，--%>
        <%--然后可以通过var里的student取出所有内容--%>
        <tr>
            <td><input type="checkbox" name="ids" value="${student.id}">${vs.count}</td>
            <td>${student.name}</td>
            <td>${student.gender}</td>
            <td>${student.dob}</td>
            <td><a href="student?action=queryById&id=${student.id}">编辑</a></td>
            <td><a href="student?action=remove&id=${student.id}">删除</a></td>
        </tr>
    </c:forEach>
</table>
        <input type="submit" value="删除">
    </form>

${requestScope.message}

</body>
</html>
