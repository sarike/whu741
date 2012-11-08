<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

		    <div id="header">风流倜傥741，每日一题 —— 今天是：${dateOfToday }
		    </div>
	    	<div class="nav">
		    	<ul>
		    		<li><a href="./index.rusb">网站首页</a></li>
					<li><a href="./topiclist.do">历史题目</a></li>
					<li><a href="./articlelist.do">文章列表</a></li>
					<c:forEach items="${pageList}" var="page">
					<li><a href="pages/${page.pageUrl }">${page.articleTitle}</a></li>
					</c:forEach>
					<c:if test="${user!=null}">
						<li class="right"><a href="./logoff.do">退出登录</a></li>
						<li id="welcome">欢迎回来！</li>
						<li id="menu" onmouseout="hideSubMenu()" onmouseover="this.style.cursor='pointer';displaySubMenu()">
						<font color="red">${user.nickname }(${user.username })</font>
							<ul id="secondMenu"> 
								<li><a href="./admin/userInfo.jsp">个人信息</a></li> 
								<c:if test="${user.valided}">
									<li><a href="./topiclist.do?type=personal">我发布的题目</a></li> 
									<li><a href="./admin/pubtopic.jsp">发布一个新题目</a></li> 
								</c:if>
								<c:if test="${user.authority==1}"><li><a href="./admin/pubarticles.jsp">发布一篇文章</a></li></c:if>
							</ul> 
						</li>
					</c:if>
					<c:if test="${user==null}">
								<li><a href="./login.jsp">登录</a></li>
					</c:if>
				</ul>
			</div>
	    	
	    	
