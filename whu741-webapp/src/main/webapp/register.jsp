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
    
    <title>注册 | 风流倜傥741，每日一题</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="./css/style.css">

  </head>
  
  <body>
    
    <form action="reg.do" method="post">
    <div id="container"  class="tb_list2" style="width:400px;margin: 100px auto;">
    <table class="formTb">
    <tr><th colspan="2" style="text-align: center;">注册为新的用户</th></tr>
    <tr><th>用户名:</th><td><input type="text" size="45" class="fullWidth" name="username" value="${user.username}"></td></tr>
    <tr><th>密码:</th><td><input type="password" size="45" class="fullWidth" name="password"></td></tr>
    <tr><th>确认密码:</th><td><input type="password" size="45" class="fullWidth" name="pswagain"></td></tr>
    <tr><th>昵称:</th><td><input type="text"  name="nickname" value="${user.nickname}"></td></tr>
    <tr><th>邮箱:</th><td><input type="text"  name="email" value="${user.email}"></td></tr>
    <tr><th>个人描述:</th><td><textarea cols="5" rows="10" size="45" class="fullWidth" name="description" value="${user.description}"></textarea></td></tr>
    <tr><td colspan="2">	<ul>
	    <c:forEach items="${errorList}" var="error">
			<li class="li2"><font color="red"><c:out value="${error}"></c:out></font></li>
		</c:forEach>
		</ul>
		</td>
	</tr>
    <tr><th colspan="2" style="text-align: center;"><input type="submit" class="input_btn" value="注册">
    <input class="input_btn" type="reset" value="重置">
    <a href="./index.rusb">返回首页</a>
    </th></tr> 
	</table>
	</div>
	</form>
  </body>
</html>
