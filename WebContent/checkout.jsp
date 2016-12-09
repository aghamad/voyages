<%@ page import="voyages.braintree.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="head.jsp" %>

<form action="./checkout" id="checkout" method="POST">
  <div id="dropin-container"></div>
  <button type="submit">Payer</button>
</form>

<script src="https://js.braintreegateway.com/js/braintree-2.30.0.min.js"></script>
<script>
	braintree.setup("<c:out value='${Gateway.GenerateToken()}'/>", 'dropin', {
	  container: 'dropin-container',
	  paypal: {
	    singleUse: true,
	    amount: "<c:out value='${sessionScope.caddyPrice}'/>",
	    currency: 'USD'
	  }
	});
</script>
<%@ include file="foot.jsp" %>