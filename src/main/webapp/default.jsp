<%--
  Created by IntelliJ IDEA.
  User: lichengjun
  Date: 2017/6/6
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>index page</title>
  </head>
  <body>
  <form action="user" method="post">
    <%--无论任何时候。表单提交都使用post方式--%>
    <%--action 就是表单请求提交的位置--%>
      <input type="hidden" name="action" value="login">
      <%--hidden  隐藏--%>
      <input type="text" name="mobile" placeholder="手机号"><br>
    <input type="password" name="password" placeholder="密码"><br>
    <input type="submit" value="登录">
  </form>
  <a href="signup.jsp">注册</a>
  <p>
  <%--<%--%>
    <%--String message = (String) request.getAttribute("message");--%>
    <%--if(message != null){--%>
        <%--out.print(message);--%>
    <%--}--%>
  <%--%>--%>

    ${requestScope.message}

  </p>
  </body>
</html>
