<%--
  Created by IntelliJ IDEA.
  User: lichengjun
  Date: 2017/6/7
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%--用户注册页面--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sign up page</title>
</head>
<body>
<form action="register" method="post">
    <%--action 就是表单请求提交的位置--%>
    <input type="text" name="nick" placeholder="昵称"><br>
    <input type="text" name="mobile" placeholder="手机号"><br>
    <input type="password" name="password" placeholder="密码"><br>
        hobbies:
        <input type="checkbox" name="hobbies" value="TV">TV
        <input type="checkbox" name="hobbies" value="Movie">Movie
        <input type="checkbox" name="hobbies" value="Game">Game<br>
        cities:
        <select name="cities" multiple>
            <option value="beijing">beijing</option>
            <option value="shanghai">shanghai</option>
            <option value="shenzhen">shenzhen</option>
        </select><br>
    <input type="submit" value="注册">

</form>
<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
        out.print(message);
    }
    session.getAttribute("sen");
%>
<%--<%=session.getAttribute("sen")%>--%>
</body>
</html>
