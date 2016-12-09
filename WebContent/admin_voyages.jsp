
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>

<%@ page import = "java.util.Date" %>
<%@ page import = "java.util.Collections" %>
<%@ page import = "voyages.models.implementations.User" %>
<%@ include file="head.jsp" %>

	<div class="container mt-50">
		<div class="list-group">
		<c:forEach items="${products}" var="voyage">
			<div action="/VoyageList" class="list-group-item pt-10 pb-20">		
			<form method=post>
				${voyage.name}
				<c:if test="${voyage.isVedette} == 1">
					<p>Country presentement en vedette<p>
				</c:if>
				<input type=hidden name=productCode value="${voyage.productId}"/>
				<button type=submit style="float:right;" class="btn btn-success">Sponsor</button>
			</form>
			</div>
		</c:forEach>
		</div>
	</div>
<%@ include file="foot.jsp" %>