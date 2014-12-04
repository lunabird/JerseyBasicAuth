<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    	
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <form method="post" name="LogIn" action="./Client" >
   用户名：<input	type = "text" name="username" value="用户名"/><br/><br/><br/><br/>
   密   码：<input 	type = "password" name = "pwd" value="密码" /><br/><br/><br/><br/><br/>
   U R L  :  <textarea style="width:900px" type = "text"  name = "url" ></textarea><br/><br/><br/><br/><br/>
   提   交：<input 	type = "submit" value = "submit"  value="提交"/>
   </form>
  </body>
</html>
