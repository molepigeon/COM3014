<%-- 
    Document   : nameView
    Created on : 04-May-2014, 16:40:30
    Author     : Michael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <spring:nestedPath path="name">
            <form action="" method="post">
                Name:
                <spring:bind path="value">
                    <input type="text" 
                           name="${status.expression}" 
                           value="${status.value}"></input>
                </spring:bind>
                    <input type="submit" value="OK">
            </form>
        </spring:nestedPath>
    </body>
</html>
