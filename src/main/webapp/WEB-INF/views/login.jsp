<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Yahoo!!</title>
</head>
<body>
    <p><font color="red">${errorMessage}</font></p>
    <form action="login" method="POST">
        Name : <input name="name" type="text" /> Password : <input name="password" type="password" /> <input type="submit" />
    </form>
</body>
</html>