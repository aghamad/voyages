
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>

<%@ page import = "java.util.Date" %>
<%@ page import = "java.util.Collections" %>
<%@ page import = "voyages.models.implementations.User" %>
<%@ include file="head.jsp" %>
<%@ include file="admin_head.jsp" %>
    <div>
	    <div class="list-group">
		    <c:forEach items="${clients}" var="client">
		    <div class="list-group-item">
				<a href="<c:url value='/client/detail?id=${client.id}'/>">
					${client.firstName} ${client.lastName}
				</a>
			</div>
			</c:forEach>
		</div>
	    
	</div>
<%@ include file="foot.jsp" %>
<%@ include file="admin_foot.jsp" %>