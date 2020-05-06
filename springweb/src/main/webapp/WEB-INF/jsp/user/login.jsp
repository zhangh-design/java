<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<title>系统-登录页</title>
	<script type="text/javascript" src="<%=basePath%>/resource/jquery-1.11.3.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/vue"></script>
</head>
<body>
	username: <input type="text" id="username"><br><br>
	password: <input type="password" id="password"><br><br>
	<button id="loginbtn">登录</button>
</body>
<script type="text/javascript">
	${jsvalue}
</script>
</html>