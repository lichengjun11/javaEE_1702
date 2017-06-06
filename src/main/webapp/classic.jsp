<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!--这是一个典型的JSP，它包含了JSP中常用的元素-->

<%!
    String getDate(){
        return new java.util.Date().toString();
    }
    int count = 10;
%>

<html>
<head>
    <title>一个典型的JSP</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div style="text-align: right">
    <table style="margin: 0 auto;">
        <tr style="background: #777;">
            <th>-------------</th>
        </tr>
        <%
            // color
            String c1 = "red",c2 = "#8c3";
            for (int i = 0; i < count; i++) {
                String color;
                if (i % 2 == 0){
                    color = c1;
                }else {
                    color = c2;
                }
                out.println("<tr style=\"background:" + color + ";\"><td>-</td></tr>");
            }

        %>
    </table>
    <hr/>
    当前的时间是：
    <%-- 下面是使用表达式的例子 --%>
    <%=getDate()%>
</div>

</body>
</html>