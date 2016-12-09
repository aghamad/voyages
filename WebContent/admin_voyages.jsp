
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>

<%@ page import = "java.util.Date" %>
<%@ page import = "java.util.Collections" %>
<%@ page import = "voyages.models.implementations.User" %>
<%@ include file="head.jsp" %>
    <div class=container>
    <div class="list-group">
    <c:forEach items="${products}" var="voyage">
    <div class="list-group-item">
    
	
	
	${voyage.nom}
	
	</div>
	</c:forEach>
	</div>
    
       </div>
<%@ include file="foot.jsp" %>