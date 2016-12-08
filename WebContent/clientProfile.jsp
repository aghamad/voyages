<%@ page language="java" contentType="text/html;charset=windows-1252" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.util.Collections" %>
<%@ page import = "voyages.models.implementations.ProductModel" %>
<%@ page import = "voyages.models.implementations.CityModel" %>
<%@ page import = "voyages.models.implementations.User" %>
<%@ page import = "voyages.db.Connexion" %>
<%@ page import = "voyages.beans.Caddy" %>
<%@ page import = "voyages.beans.CaddyItem" %>
<%@ page import = "java.io.InputStream" %>
<%@ page import = "java.io.FileInputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.Arrays" %>
<%@ include file="head.jsp" %>
    <div class=container>
    	<h2>Client <b>${client.firstName} ${client.lastName}</b></h2>
    	<br/>
    	<h3>Profile</h3>
    	<div class="list-group">
			<div class="list-group-item">First Name : ${client.firstName}</div>
			<div class="list-group-item">Last Name : ${client.lastName}</div>
			<div class="list-group-item">Email : ${client.email}</div>
			<div class="list-group-item">Address : ${client.address}</div>
			<div class="list-group-item">Location : ${client.city.name}</div>
		</div>
		<br/>
		<br/>
		<h3>Orders History</h3>
		<c:set var="total" value="${0}"/>
		
		<c:forEach items="${client.orders}" var="order">
			<h1>Order ${order.orderId}</h1>
			
			
 <table id="cart" class="table table-hover table-condensed">
    				<thead>
						<tr>
							<th style="width:50%">Trip</th>
							<th style="width:10%">Price</th>
							<th style="width:8%">Quantity</th>
							<th style="width:22%" class="text-center">Subtotal</th>
							<th style="width:10%"></th>
						</tr>
					</thead>
					<tbody>

<c:forEach items="${order.items}" var="item">
    <c:set var="total" value="${total + item.quantity * item.unitPrice}" />
    <tr>
    
    <td data-th="Product">
			<div class="row">
				<div class="col-sm-2 hidden-xs">
                                        <img style="width:50px" class="group list-group-image" src="images/${item.product.image}" />
				</div>
				<div class="col-sm-10">
					<h4 class="nomargin">
						${item.product.name}
					</h4>
					<p>${item.product.description}</p>
				</div>
			</div>
		</td>
		<td data-th="Price">
		${item.product.price}
		</td>
		
		<td data-th="Quantity">
                        for ${item.quantity} People
		</td>
		<td data-th="Subtotal" class="text-center">
		${item.unitPrice * item.quantity}
		</td>
                
                </tr>
    </c:forEach>
    </tbody>
	<tfoot>
		<tr class="visible-xs">
			<td class="text-center"><strong>Total ${total}</strong></td>
		</tr>
		<tr>
			<td>
			</td>
			<td colspan="2" class="hidden-xs"></td>
			<td class="hidden-xs text-center"><strong>Total $ ${total} </strong></td>
			<td>
			</td>
		</tr>
	</tfoot>
	</table>
		</c:forEach>
    </div>
<%@ include file="foot.jsp" %>