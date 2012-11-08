<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <base href="<%=basePath%>">
	    <title>风流倜傥741，每日一题</title>
		<meta http-equiv="keywords" content="找工作,面试题,IT,计算机,算法,编程语言,java,c,c++,设计模式">
		<meta http-equiv="description" content="风流倜傥741，每日一天，天天抽点空闲时间做几个题目，希望毕业后能找份好工作。">
		<jsp:include page="../WEB-INF/templete/headerMeta.jsp"></jsp:include>
		
		
		<script type="text/javascript">
		function revalidEmail(){
		 var emailForm = $('#emailForm');
		 emailForm.find("input[name=email]").val($("#inputEmail").val());
		 emailForm.submit();
		 }
		</script>
	</head>
	<body>
	<div id="container"  class="tb_list2">
		<jsp:include page="../WEB-INF/templete/header.jsp"></jsp:include>

<table><tr><th>个人信息</th></tr></table>
<form action="admin/updateUserInfo.do" method="post">
    <table class="formTb">
    <tr><th>用户名：</th><td style="width:80%">${user.username}</td></tr>
    <tr><th>昵称：</th><td><input name="nickname" value="${user.nickname}"></td></tr>
    <tr><th>邮箱：</th>
    <td>
    	<c:if test="${user.valided}">
    		<input name="email" value="${user.email}" readonly="readonly"> 
    		<font color="green">通过验证！</font>
    	</c:if>
    	<c:if test="${!user.valided}">
	    		<input id="inputEmail" name="email" value="${user.email}">
	    		<font color="red">未通过验证，您不能参与讨论并且不能收到新题目的邮件提醒！</font> 
	    		<input type="button" onclick="revalidEmail()" value="重新验证邮箱">
    	</c:if>
    </td>
    </tr>
    <tr><th>邮件提醒：</th>
    <td>
    新文章提醒:
    <c:if test="${user.articleRemind}">
    	<input type="checkbox" value="1" name="articleRemind" checked="checked">
    </c:if>
    <c:if test="${!user.articleRemind}">
    	<input type="checkbox" value="1" name="articleRemind">
    </c:if>
    &nbsp;&nbsp;
    新题目提醒:
    <c:if test="${user.topicRemind}">
    	<input type="checkbox" value="1" name="topicRemind" checked="checked">
    </c:if>
    <c:if test="${!user.topicRemind}">
    	<input type="checkbox" value="1" name="topicRemind">
    </c:if>
    &nbsp;&nbsp;
    新评论提醒:
    <c:if test="${user.messageRemind}">
    	<input type="checkbox" value="1" name="messageRemind" checked="checked">
    </c:if>
    <c:if test="${!user.messageRemind}">
    	<input type="checkbox" value="1" name="messageRemind">
    </c:if>
    &nbsp;&nbsp;
    <i>大哥，慎重呀……</i>
    </td>
    </tr>
    <tr><th>个人描述：</th><td><textarea cols="5" rows="10" size="45" class="fullWidth" name="description">${user.description}</textarea></td></tr>
        <tr><td colspan="2">	<ul>
	    <c:forEach items="${errorList}" var="error">
			<li><font color="red"><c:out value="${error}"></c:out></font></li>
		</c:forEach>
		</ul>
		</td>
	</tr>
    <tr><th colspan="2" style="text-align: center;"><input type="submit" class="input_btn" value="更新">
    </table>
    </form>
    <form id="emailForm" action="./valid" method="post">
	    		<input type="hidden" name="email" value="">
	    		<input type="hidden" name="action" value="reValid">
    </form>
    <jsp:include page="../WEB-INF/templete/footer.jsp"></jsp:include>
    </div>
  </body>
</html>
