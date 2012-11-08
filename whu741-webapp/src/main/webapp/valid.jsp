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
    
    <title>注册成功</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="./css/style.css">
  </head>
  <body>
  <div id="container"  class="tb_list2">
    <div style="width:800px;margin: 100px auto;">
	    <table>
		    <tr><th colspan="2" style="text-align: center;">邮箱验证信息</th></tr>
		    <tr><td colspan="2" style="text-align: center;">${validInfo }</td></tr>
		   
		    <tr><td colspan="2" style="text-align: center;">
		     <c:if test="${valided}">
		    	<a href="login.jsp">立刻登录</a>
		    </c:if>
		    <a href="index.rusb">返回主页</a></td></tr>
		    
	    </table>
    </div>
    </div>
  </body>
</html>
