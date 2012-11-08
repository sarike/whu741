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
		<script>
        $(document).ready(function(){
            $("#topicForm").validationEngine();
           });
        </script>
		<script>
	        var editor;
			KindEditor.ready(function(K) {
				editor = K.create('#editorContent', {
					resizeType : 1,
					allowPreviewEmoticons : false,
					allowImageUpload : false,
					items : [
						'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons', 'image','code', 'link']
				});
			});
	</script>
	</head>
	<body>
	<div id="container"  class="tb_list2">
	<jsp:include page="../WEB-INF/templete/header.jsp"></jsp:include>

  <form action="pub.do?action=<c:out value="${action}" default="add"></c:out>" method="post" id="topicForm">
  	<table><tr><th colspan="2" style="text-align: center;">发布一个新的题目</th></tr></table>
    <table class="formTb">
    <tr><th>题目名称：</th><td><input value="${topic.topicName }" id="topicName" class="td_text_left validate[required]" type="text" name="topicname" size="50"></td></tr>
    <tr>
    	<th>题目类别：</th>
    	<td>
    		<select name="classes" id="classes" class="validate[required]">
    			<option value="1">算法</option>
    			<option value="2">操作系统</option>
    			<option value="3">计算机网络</option>
    			<option value="4">java</option>
    			<option value="5">C/C++</option>
    			<option value="6">WEB前端</option>
    			<option value="7">趣味题</option>
    			<option value="8">设计模式</option>
    		</select>
    	</td>
    </tr>
    <tr><th>题目内容：</th><td>
    <textarea id="editorContent" class="fullWidth validate[required]" rows="20" cols="117" name="topiccontent">${topic.topicContent }</textarea>
    </td></tr>
    <tr><th>标签：</th><td><input value="${topic.topicTags }" id="tags" class="td_text_left fullWidth validate[required]" type="text" name="tags" size="50"></td></tr>
    <tr><th></th><td><font color="red">${error }</font><input class="input_btn"  type="submit" value="提交">
    <input type="button" value="返回" onclick="javaScript:window.history.back(-1)" class="input_btn" /></td></tr>
    </table>
    <input type="hidden" name="topicId" value="${topic.topicId }">
  </form>
  <jsp:include page="../WEB-INF/templete/footer.jsp"></jsp:include>
  </div>
  </body>
</html>