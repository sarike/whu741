<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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
            $("#articleForm").validationEngine();
           });
        </script>
        	<script>
	        var editor;
			KindEditor.ready(function(K) {
				editor = K.create('#editorContent', {
					resizeType : 1,
					allowPreviewEmoticons : false,
					allowImageUpload : false
				});
			});
	</script>
	</head>
	<body>
	<div id="container"  class="tb_list2">
		<jsp:include page="../WEB-INF/templete/header.jsp"></jsp:include>
		<form action="./admin/pubarticles.do?action=<c:out value="${action}" default="add"></c:out>" onsubmit="" id="articleForm" method="post">
			<table><tr><th colspan="2" style="text-align: center;">发布一篇文章</th></tr></table>
		    <table class="formTb">
			    <tr><th>文章标题：</th><td><input value="${article.articleTitle }" type="text" class="validate[required] td_text_left" id="articleTitle" name="articleTitle" size="50"></td></tr>
			    <tr><th>文章类型：</th>
			    <td>
			    <c:if test="${article.articleType==1||article==null }">
				    原创<input type="radio" name="articleType" value="1" checked="checked"/>   
				    转载<input type="radio" name="articleType" value="2"/>
			    </c:if>
			    <c:if test="${article.articleType==2 }">
				    原创<input type="radio" name="articleType" value="1"/>   
				    转载<input type="radio" name="articleType" value="2" checked="checked"/>
			    </c:if>
			    </td></tr>
			    <tr>
			    	<th>文章分类：</th>
			    	<td>
			    		<select id="articleClass" name="articleClass">
			    			<option value="1">算法题目</option>
			    		</select>
			    	</td>
			    </tr>
			    <tr>
				    <th>是否为单页：</th>
				    <td>
				    <c:if test="${!article.singlePage||article==null}">
					    	是<input type="checkbox" name="singlePage" onchange="showPageUrlInput(this,'pageUrlInput')" value="1"/> 
					    	<span id="pageUrlInput" style="visibility: hidden;"> 页面连接<input id="pageUrl" type="text" size="40" name="pageUrl"><i>如：about.html</i></span>
				    </c:if>
				    <c:if test="${article.singlePage}">
					    	是<input type="checkbox" name="singlePage" onchange="showPageUrlInput(this,'pageUrlInput')" value="1" checked="checked"/> 
					    	<span id="pageUrlInput"> 页面连接<input id="pageUrl" value="${article.pageUrl}" type="text" size="40" name="pageUrl"><i>如：about.html</i></span>
				    </c:if>
				    </td>
			    </tr>
			    <tr>
				    <th></th>
				    <td>
				    <c:if test="${article.toped}">
				    	<label for="toped">置顶：</label><input id="toped" checked="checked" type="checkbox" name="toped" value="1"/>
				    </c:if>
				    <c:if test="${!article.toped||article==null}">
				    	<label for="toped">置顶：</label><input id="toped" type="checkbox" name="toped" value="1"/>
				    </c:if>
				    <c:if test="${article.advised}">
						&nbsp;&nbsp;&nbsp;&nbsp;<label for="advised">推荐：</label><input checked="checked" type="checkbox" id="advised" name="advised" value="1"/>  
					</c:if>
				    <c:if test="${!article.advised||article==null}">
						&nbsp;&nbsp;&nbsp;&nbsp;<label for="advised">推荐：</label><input type="checkbox" id="advised" name="advised" value="1"/>  
					</c:if>
				    </td>
			    </tr>
			    <tr><th>文章内容：</th><td>
			    <textarea id="editorContent" class="validate[required] fullWidth" rows="20" cols="117" name="articleContent">${article.articleContent }</textarea>
			    </td></tr>
			    <tr>
			    <th></th>
			    <td style="text-align: center;">
			    <input type="hidden" value="${article.articleId }" name="articleId">
				    <font color="red">${error }</font><input class="input_btn"  type="submit" value="提交">
				    <input type="button" value="返回" onclick="javaScript:window.history.back(-1)" class="input_btn" />
			    </td>
			    </tr>
		    </table>
	     </form>
	     <jsp:include page="../WEB-INF/templete/footer.jsp"></jsp:include>
	</div>
</body>
</html>