<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "voyages.models.implementations.ProductModel" %>
<%@ page import = "exceptions.DAOException" %>
<%@ page import = "voyages.models.implementations.OrderModel" %>
<%@ page import = "voyages.models.implementations.OrderDetailsModel" %>
<%@ page import = "voyages.models.implementations.User" %>
<%@ page import = "com.braintreegateway.Transaction" %>
<%@ page import = "voyages.db.Connexion" %>
<%@ page import = "java.io.InputStream" %>
<%@ page import = "java.io.FileInputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.Arrays" %>
<%@ include file="head.jsp" %>
<div class=container>
<div class=row>
<%

OrderModel orderModel = new OrderModel(Connexion.getConnexion());
ProductModel productModel = new ProductModel(orderModel);
OrderDetailsModel orderDetailsModel = new OrderDetailsModel(orderModel);

User authUser = User.getAuthenticatedUser(request);
    
if(authUser == null) {
    %>
    <p>Cannot get a list of orders if the user is not connected</p>
    <%
}
else {
    List<OrderModel> pastOrders = null;
    try {
        pastOrders = orderModel.findByCustomer(authUser);
    } catch(DAOException e) {
        throw new ServletException(e);
    }
if(pastOrders.isEmpty()) {
    out.print("<div class=jumbotron><div class=container><p>No orders yet.<br/>...<br/><br/><a href=products>Purchase</a> some fruits !</p></div></div>");
} else {
%>
<a href="/Commerce-Project1-context-root/products" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a>
<%
}

for(int i = 0; i < pastOrders.size(); i++) {
    OrderModel order = pastOrders.get(i);
    List<OrderDetailsModel> items = orderDetailsModel.findByOrder(order);
    %>
    <h1>Order #<%= order.OrderId %></h1>
 <table id="cart" class="table table-hover table-condensed">
    				<thead>
						<tr>
							<th style="width:50%">Produit</th>
							<th style="width:10%">Prix</th>
							<th style="width:8%">Nombre de Passagers</th>
							<th style="width:22%" class="text-center">Sous-total</th>
							<th style="width:10%">Paiement</th>
						</tr>
					</thead>
					<tbody>
<%
	double total = 0;
    for(int j = 0; j < items.size(); j++) {
        OrderDetailsModel item = (OrderDetailsModel) items.get(j);
        ProductModel product = new ProductModel(productModel);
        product.ProductId = item.ProductId;
        total += item.UnitPrice * item.Quantity;
        product = (ProductModel) productModel.read(product);
    
    %>
    <tr>
    <td data-th="Product">
			<div class="row">
				<div class="col-sm-2 hidden-xs">
					<!--<img style="width:100px" class="group list-group-image" src="data:image/gif;base64, product.getImageAsBase64()  " />-->
					<img style="width:50px" class="group list-group-image" src="images/<%= product.Image %>" />
				</div>
				<div class="col-sm-10">
					<h4 class="nomargin">
						<%= product.Name %>
					</h4>
					<p><%= product.Description %></p>
				</div>
			</div>
		</td>
		<td data-th="Price">
		<%=
		product.Price
		%>
		</td>
		
		<td data-th="Quantity">
			X <%= item.Quantity %>
		</td>
		<td data-th="Subtotal" class="text-center">
			<%= item.UnitPrice * item.Quantity %>
		</td>
		<% if(j == 0) {
			Transaction transaction = order.GetPayment(); 
			if(transaction != null) {
			%>
                <td data-th="Paiement" rowspan="<%= items.size() %>">
	                Montant : <%= transaction.getAmount() %> <br/>
	                Adresse : <%= transaction.getBillingAddress() %> <br/>
	                Type de carte : <%= transaction.getCreditCard().getCardType() %> <br/>
	                Numéro de carte : **** **** **** <%= transaction.getCreditCard().getLast4() %> <br/>
	                Propriétaire de la carte : <%= transaction.getCreditCard().getCardholderName() %> <br/>
	                Date d'expiration : <%= transaction.getCreditCard().getExpirationDate() %>/<%= transaction.getCreditCard().getExpirationMonth() %><br/>
                	
                </td>
        	<% } else {
        		%> Non indiqué <%
        	}
		}
		%>
                </tr>
                <!--
		<td class="actions" data-th="">
			<button type=submit class="btn btn-info btn-sm"><i class="fa fa-refresh"></i></button>
			<a href="<%= "cart?delete=" + product.ProductId %>" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></a>
                        
		</td>-->
    <%
    }
    %>
    </tbody>
	<tfoot>
		<tr class="visible-xs">
			<td class="text-center"><strong>Total <%= total %></strong></td>
		</tr>
		<tr>
							<td>
                                                        <form method=post >
                                                        <input type=hidden name=orderId value="<%= order.OrderId %>" />
                                                        <input type=hidden name=action value=reorder />
                                                        <button type=submit  class="btn btn-success btn-block">Reorder <i class="fa fa-angle-left"></i></button>
                                                        </form></td>
							<td colspan="2" class="hidden-xs"></td>
							<td class="hidden-xs text-center"><strong>Total $<%= total %></strong></td>
							<td>
							
                                                        <!--
                                                        <button type=submit  class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></button>
                                                        -->
                                                        </td>
						</tr>
					</tfoot>
				</table>
                                <div style="clear:both"></div>
    <%
}
}
%>
</div>
          </div>
<%@ include file="foot.jsp" %>