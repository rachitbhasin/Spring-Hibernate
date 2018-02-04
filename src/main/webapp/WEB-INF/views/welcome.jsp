<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>	
<html>
<head>
<base href="/" />
<title>Welcome!!</title>
</head>
<body>
	<div>
		<span>Welcome ${greetings}</span>
		<span><a href="<c:url value="/logout" />">Logout</a></span>
	</div>
</body>
</html>