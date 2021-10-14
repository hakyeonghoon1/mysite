<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
						<c:set var='count' value='${fn:length(list) }'/>
						<c:forEach items='${list }' var='vo' varStatus='status'>			
							<tr>
								<td>${vo.rownum }</td>
								<td style="text-align:left; padding-left:${vo.depth*20}px">
									<c:if test="${vo.depth>0 }">
										<img src='${pageContext.request.contextPath }/assets/images/reply.png'/>
									</c:if>
									<a href="${pageContext.request.contextPath }/board?a=viewform&no=${vo.no }">${vo.title }</a>
								</td>
								<td>${vo.userName }</td>
								<td>${vo.hit }</td>
								<td>${vo.regDate }</td>
								<c:if test="${authUser.no == vo.userNo }">
									<td><a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no }" class="del">삭제</a></td>
								</c:if>
							</tr>
						</c:forEach>

				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${totalPage > 5 }">
							<c:choose>
								<c:when test="${param.page == 1 }">
									<li><a href="${pageContext.request.contextPath }/board?page=1">◀</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath }/board?page=${param.page-1 }">◀</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:choose>
							<c:when test="${param.page >=5 && param.page >= totalPage}">
								<c:forEach begin='${totalPage-3}' end='${totalPage }' var='pageNum'>
									<c:choose>
										<c:when test="${pageNum == param.page }">
											<li class="selected"><a href="${pageContext.request.contextPath }/board?page=${pageNum }">${pageNum }</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageContext.request.contextPath }/board?page=${pageNum }">${pageNum }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>							
							</c:when>
							<c:when test="${param.page >=5 && param.page < totalPage}">
								<c:forEach begin='${totalPage-3}' end='${totalPage }' var='pageNum'>
									<c:choose>
										<c:when test="${pageNum == param.page }">
											<li class="selected"><a href="${pageContext.request.contextPath }/board?page=${pageNum }">${pageNum }</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageContext.request.contextPath }/board?page=${pageNum }">${pageNum }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>								
							</c:when>
							<c:otherwise>
								<c:forEach begin='1' end='5' var='pageNum'>
									<c:choose>
										<c:when test="${pageNum == param.page }">
											<li class="selected"><a href="${pageContext.request.contextPath }/board?page=${pageNum }">${pageNum }</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageContext.request.contextPath }/board?page=${pageNum }">${pageNum }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:otherwise>
						</c:choose>

						<c:if test="${totalPage > 5 }">
							<c:choose>
								<c:when test="${param.page == totalPage }">
									<li><a href="${pageContext.request.contextPath }/board?page=${param.page }">▶</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath }/board?page=${param.page+1 }">▶</a></li>
								</c:otherwise>
							</c:choose>						
						</c:if>
					
						
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${!empty authUser }">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>