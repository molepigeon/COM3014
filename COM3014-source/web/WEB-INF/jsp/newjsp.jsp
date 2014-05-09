<%-- 
    Document   : newjsp
    Created on : 09-May-2014, 14:31:43
    Author     : Michael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="fileupload.htm" enctype="multipart/form-data" method="POST">
            <input type="file" name="imageFile">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
