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
<jsp:include page="../WEB-INF/templete/header.jsp"></jsp:include>
  <div id="tb_list2" class="container">
		<table><tr><th>犀利解答</th></tr></table>
  		<table class="listTb">
    		<tr><th>题目名称：${topic.topicName }</th></tr>
	    	<tr><td  style="text-align: left">${topic.topicContent }</td></tr>
	    	<tr><td style="text-align: right">该题目由 <font color="red">${topic.pubUser.nickname }(${topic.pubUser.username })</font> 于${topic.pubTime }倾情奉献</td></tr>
	    	<tr><td colspan="2" style="text-align: right">
					    	<span style="float: left;">
						    	类别：<a href="./topiclist.do?type=class&classid=${topic.topicClass.classId}">${topic.topicClass.className }</a> 
						    	标签：
								<c:forEach items="${fn:split(fn:replace(topic.topicTags, '，', ','),',')}" var="tag" varStatus="vs">
						    		<a href="./topiclist.do?type=tag&tag=${tag}">${tag}</a>
						    		<c:if test="${!vs.last}">,</c:if>
						    	</c:forEach>
					    	</span>
			<a href="./discusspri.do?topicid=${topic.topicId }">参与讨论</a> 
			</td></tr>
	    	<c:if test="${topic.answered}">
		    	<tr><th>题目答案：</th></tr>
		    	<tr><td style="text-align: left">${topic.topicAnswer }</td></tr>
		    	<tr><td colspan="2" style="text-align: right">该答案由 <font color="red">${topic.answerUser.nickname }(${topic.answerUser.username})</font> 倾情奉献</td></tr>
		    </c:if>

		</table>
     <form action="subanswer.do?topicid=${topic.topicId }" onsubmit="return validateForm()" method="post">
       		<table>
    		<tr><th style="text-align: center;">提交你犀利的答案</th></tr>
	    	<tr><td><textarea id="editorContent" class="fullWidth" rows="15" cols="50" name="topicanswer"></textarea></td></tr>
     <tr><th style="text-align: center;"><input type="submit" class="input_btn" value="提交">
     <input type="button" value="返回" onclick="javaScript:window.history.back(-1)" class="input_btn" /></th></tr>
     </table>
     </form>
     <jsp:include page="../WEB-INF/templete/footer.jsp"></jsp:include>
     </div>
  </body>
</html>