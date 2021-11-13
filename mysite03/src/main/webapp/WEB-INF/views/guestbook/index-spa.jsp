<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath }/assets/ejs/ejs.js"></script>
<script>
var lastNo;
var startNo=-1;
var messageBox = function(title, message,callback){
	$("#dialog-message").attr('title',title);
	$("#dialog-message p").text(message);
	$("#dialog-message").dialog({
		modal:true,
		buttons:{
			"확인":function(){
				$(this).dialog("close");
			}
		},
		close:callback
	});	
}
var listEJS = new EJS({
	url:'${pageContext.request.contextPath }/assets/ejs/guestbook-list.ejs'
});
var fetch = function(){
	$.ajax({
		url:"${pageContext.request.contextPath }/guestbook/api/list",
		type:"get",
		dataType:"json",
		success:function(response){
			
			lastNo=response.data[0].no;
			var html = listEJS.render(response);
			$("#list-guestbook").html(html);
			
			startNo = $("#list-guestbook li").last().data('no') || 0;
		}
		
	});
}
$(function(){
	fetch();
	
	// 삭제 다이얼로그 객체 만들기
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen:false,
		modal:true,
		buttons:{
			"삭제":function(){
				var password = $("#password-delete").val();
				var deleteNo = $("#hidden-no").val();
			
				//ajax
				$.ajax({
					url:'${pageContext.request.contextPath }/guestbook/api/delete',
					type:'post',
					data:'password='+password+"&no="+deleteNo,
					dataType:'json',
					success:function(response){
						
						if(response.data==null){
							$(".validateTips-error").show();			
						} else{
							$("#list-guestbook li[data-no='"+deleteNo+"']").remove();
							fetch();
							dialogDelete.dialog('close');
						}
					}
				})
			},
			"취소":function(){
				$(this).dialog('close');
			}
		}
	});
	
	// 
	
	// 글삭제 버튼 (Live Event)
	$(document).on('click',"#list-guestbook li a", function(event){
		event.preventDefault();
		var no = $(this).data('no');
		$("#hidden-no").val(no);
		dialogDelete.dialog('open');
	})
	
	// form validation
	$("#add-form").submit(function(event){
		event.preventDefault();
		
		// 이름
		var name = $("#input-name").val();
		if(!name){
			messageBox('새글 작성','이름은 반드시 입력해야합니다.',function(){
				$("#input-name").focus();
			});
			return;
		}
		// 비밀번호
		var password = $("#input-password").val();
		if(!password){
			messageBox('새글 작성','비밀번호는 반드시 입력해야합니다.',function(){
				$("#input-password").focus();
			});
			return;
		}
		// 내용
		var content = $("#tx-content").val();
		if(!content){
			messageBox('새글 작성','내용 반드시 입력해야합니다.',function(){
				$("#tx-content").focus();
			});
			return;
		}
		
		$.ajax({
			url:'${pageContext.request.contextPath }/guestbook/api/add',
			type:'post',
			dataType:'json',
			data:'password='+password+'&name='+name+'&content='+content+'&lastNo='+lastNo,
			success:function(response){
				lastNo=response.data[0].no;
				var html = listEJS.render(response);
				$("#list-guestbook").prepend(html);
			}
			
		});
		$("#add-form")[0].reset();
		console.log("ajax insert!!!");
	});
	//첫 번째 리스트 가져오기
	
	// 스크롤
	$(window).scroll(function(){
		var $window = $(this);
		var $document = $(document);
		
		var windowHeight = $window.height();
		var documentHeight =$document.height();
		var scrollTop = $window.scrollTop();
		//console.log("windowHeight:"+windowHeight+" documentHeight:"+documentHeight+" scrollTop:"+scrollTop);
		if(scrollTop+windowHeight> documentHeight){
		
			$.ajax({
				url:'${pageContext.request.contextPath }/guestbook/api/pastList?startNo='+startNo,
				type:'get',
				dataType:'json',
				success:function(response){
					var html = listEJS.render(response);
					$("#list-guestbook").append(html);
					startNo = $("#list-guestbook li").last().data('no') || 0;		

				}
			});
			
		}
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="http://douzone.com" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
														
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips-error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="새 글 작성" style="display:none">
  				<p>이름은 반드시 입력해야 합니다.</p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>