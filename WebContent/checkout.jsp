<%@ page import="voyages.braintree.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="head.jsp" %>

<form action="<c:url value="/checkout"/>" id="checkout" method="POST">
  <div id="dropin-container"></div>
  <button type="submit">Payer</button>
</form>


<script>
$(document).ready(function() {
	braintree.setup("<c:out value='${Gateway.GenerateToken()}'/>", 'dropin', {
	  container: 'dropin-container',
	  paypal: {
	    singleUse: true,
	    amount: "<c:out value='${sessionScope.caddyPrice}'/>",
	    currency: 'USD'
	  }
	});
})
</script>
<%@ include file="foot.jsp" %>