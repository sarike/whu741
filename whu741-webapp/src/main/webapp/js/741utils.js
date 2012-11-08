function showPageUrlInput(checkbox,spanId){
	var span = document.getElementById(spanId);
	if(checkbox.checked){
		span.style.visibility="visible";
	}else{
		span.style.visibility="hidden";
	}
}

function displaySubMenu() { 
	var subMenu = document.getElementById("secondMenu"); 
	subMenu.style.display = "block"; 
} 
function hideSubMenu() { 
	var subMenu = document.getElementById("secondMenu"); 
	subMenu.style.display = "none"; 
} 

function isNull(obj){
	if(obj.value==null||obj.value=="")
		return true;
	return false;
}

function dealWithMsgScore(data){
	eval("var jsonObj =" + data);
	if(jsonObj.note=='nolog'){
		if(confirm("如果您真心觉得这句评论给力，那就先登录吧！(点击”确定“跳转到登录页面)")){
			location.href="./login.jsp";
		}
	}else if(jsonObj.note=='success'){
		$('#scoreLable'+jsonObj.messageid).html(jsonObj.score);
		alert("评分成功，为你能如此认真地在本站学习而高兴，祝早日高就！")
	}else if(jsonObj.note=='uped'){
		alert("兄弟，你已经赞过了，谢谢你这么热心！")
	}
}