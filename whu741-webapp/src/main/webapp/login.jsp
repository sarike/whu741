<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>风流倜傥741，每日一题——登陆</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="./css/style.css">

  </head>
  
  <body>
  <div id="container"  class="tb_list2">
    <form action="login.do" method="post">
    <div style="width:400px;margin: 100px auto;">
	    <table>
		    <tr><th colspan="2" style="text-align: center;">登录 </th></tr>
		    <tr><th style="text-align: right;">用户名：</th><td><input size="50" type="text" name="username" value="${user.username}"></td></tr>
		    <tr><th style="text-align: right;">密码：</th><td><input size="50" type="password" name="password"></td></tr>
		    <tr><th colspan="2" style="text-align: center;"><input type="submit" class="input_btn" value="提交">
		    	<a href="./register.jsp">注册</a>
		    	<a href="./index.rusb">返回首页</a>
		    </th></tr>
	    </table>
    </div>
    </form>
    <font color="red"><c:out value="${error}"></c:out></font>
    </div>
  </body>
</html>
