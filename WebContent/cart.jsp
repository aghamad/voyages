
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.io.InputStream" %>
<%@ page import = "java.io.FileInputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.Arrays" %>
<%@ page import = "voyages.beans.*" %>
<%@ include file="head.jsp" %>
        <style>
		.table>tbody>tr>td, .table>tfoot>tr>td
{
    vertical-align: middle;
}
@media screen and (max-width: 600px) {
    table#cart tbody td .form-control{
		width:20%;
		display: inline !important;
	}
	.actions .btn{
		width:36%;
		margin:1.5em 0;
	}
	
	.actions .btn-info{
		float:left;
	}
	.actions .btn-danger{
		float:right;
	}
	
	table#cart thead { display: none; }
	table#cart tbody td { display: block; padding: .6rem; min-width:320px;}
	table#cart tbody tr td:first-child { background: #333; color: #fff; }
	table#cart tbody td:before {
		content: attr(data-th); font-weight: bold;
		display: inline-block; width: 8rem;
	}
	
	
	
	table#cart tfoot td{display:block; }
	table#cart tfoot td .btn{display:block;}
		</style>
	
    <table id="cart" class="table table-hover table-condensed">
    				<thead>
						<tr>
							<th style="width:50%">Product</th>
							<th style="width:10%">Price</th>
							<th style="width:8%">Quantity</th>
							<th style="width:22%" class="text-center">Subtotal</th>
							<th style="width:10%"></th>
						</tr>
					</thead>
					<tbody>
					
    <%
    Caddy caddy = null;
    if(request.getSession().getAttribute("caddy") instanceof Caddy)
	caddy = (Caddy) request.getSession().getAttribute("caddy");
    
    if(caddy == null)
        caddy = new Caddy();
	
	int total = 0;
    for(int i =0 ; i < caddy.getItems().size(); i++) {
		CaddyItem item = caddy.getItems().get(i);
		
		total += item.getProduct().Price * item.getQuantity();
		
		%>
		<tr>
                
	<form method=POST>
		<td data-th="Product">
			<div class="row">
				<div class="col-sm-2 hidden-xs">
					<!--<img style="width:100px" class="group list-group-image" src="data:image/gif;base64, caddy.getItems().get(i).getProduct().getImageAsBase64() " />-->
                                        <img style="width:100px" class="group list-group-image" src="images/<%= caddy.getItems().get(i).getProduct().Image %>" />
				</div>
				<div class="col-sm-10">
					<h4 class="nomargin">
						<%= caddy.getItems().get(i).getProduct().Name %>
					</h4>
					<p><%= caddy.getItems().get(i).getProduct().Description %></p>
				</div>
			</div>
		</td>
		<td data-th="Prix"><%=
		caddy.getItems().get(i).getProduct().Price
		%>
		</td>
		
		<td data-th="Passagers">
			<input type="number" name="itemQuantity.<%= caddy.getItems().get(i).getProduct().ProductId %>" class="form-control text-center" value="<%= caddy.getItems().get(i).getQuantity() %>">
		</td>
		<td data-th="Sous-total" class="text-center"><%=
		caddy.getItems().get(i).getProduct().Price * caddy.getItems().get(i).getQuantity()
		%></td>
		<td class="actions" data-th="">
			<button type=submit class="btn btn-info btn-sm"><i class="fa fa-refresh"></i></button>
			<a href="<%= "cart?delete=" + caddy.getItems().get(i).getProduct().ProductId %>" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></a>						
		</td>
                                      
    </form>
    
		</tr>
    <%
    }
    %>
    	</tbody>
	<tfoot>
		<tr class="visible-xs">
			<td class="text-center"><strong>Total <%= total %></strong></td>
		</tr>
		<tr>
							<td><a href="<c:url value="/products"/>" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a></td>
							<td colspan="2" class="hidden-xs"></td>
							<td class="hidden-xs text-center"><strong>Total $<%= total %></strong></td>
							<td>
                                                        <form action='<c:url value="checkout"/>' method="get">
                                                        <button type=submit  class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></button>
                                                        </form>
                                                        </td>
						</tr>
					</tfoot>
				</table>
          
<%@ include file="foot.jsp" %>