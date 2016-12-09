
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>

<%@ page import = "java.util.Date" %>
<%@ page import = "java.util.Collections" %>
<%@ page import = "voyages.models.implementations.User" %>
<%@ include file="head.jsp" %>
<%@ include file="admin_head.jsp" %>

	<div class="">
	<h2>Liste des voyages</h2>
		<div class="list-group">
		<c:forEach items="${products}" var="voyage">
			<div action="<c:url value="/VoyageList"/>" class="list-group-item pt-10 pb-20">		
			<form method=post>
				${voyage.name}
				<c:if test="${voyage.isVedette == 1}">
					<a class="really_big big_butt">En vedette<a>
				</c:if>
				<input type=hidden name=productCode value="${voyage.productId}"/>
				
				<c:if test="${voyage.isVedette == 0}">
				<input type=hidden name=action value=sponsor />
					<button type=submit style="float:right;" class="btn btn-success">
					Sponsor
					</button>
				</c:if>
				
				<c:if test="${voyage.isVedette == 1}">
				<input type=hidden name=action value=unsponsor />
					<button type=submit style="float:right;" class="btn btn-success">
					Unsponsor
					</button>
				</c:if>

			</form>
			</div>
		</c:forEach>
		</div>
	</div>
	<%@ include file="foot.jsp" %>
	
<%@ include file="admin_foot.jsp" %>