<%@ page language="java" contentType="text/html;charset=windows-1252" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>
<%@ page import = "voyages.models.implementations.ProductModel" %>
<%@ page import = "voyages.models.implementations.User" %>
<%@ page import = "voyages.db.Connexion" %>
<%@ page import = "voyages.beans.Caddy" %>
<%@ page import = "voyages.beans.CaddyItem" %>
<%@ page import = "java.io.InputStream" %>
<%@ page import = "java.io.FileInputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.Arrays" %>
<%@ include file="head.jsp" %>
    <div class="container">
    <div class="well well-sm">
        <strong>Products</strong>
        <div class="btn-group">
            <a href="#" id="list" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-th-list">
            </span>List</a> <a href="#" id="grid" class="btn btn-default btn-sm"><span
                class="glyphicon glyphicon-th"></span>Grid</a>
        </div>
    </div>

    
    <div id="products" class="row list-group">
    <%
    ProductModel productModel = new ProductModel(Connexion.getOrSetUpConnexion(getServletContext()));
    
    List<ProductModel> initial_products = productModel.getAll();
    List<ProductModel> vedettes = productModel.findVedettes();
    
    ArrayList<Integer> cartCodes = (ArrayList<Integer>) request.getSession().getAttribute("cart");
    /*Caddy caddy = (Caddy) request.getSession().getAttribute("caddy");
    
    if(caddy == null)
        caddy = new Caddy();
        */
    ArrayList<ProductModel> cart = new ArrayList<ProductModel> ();
    //request.getSession().getAttribute("cart");
    
    /*if(cartCodes != null){
        cart = productModel.getAll(Connexion.getConnexion());
    } */
    
    ProductModel added = null;
    if(request.getAttribute("addedProduct") instanceof ProductModel)
        added = (ProductModel) request.getAttribute("addedProduct");
    
    List<ProductModel> products = initial_products;
    
    
    if(added != null){
		%>
			<div class="alert alert-success">
		'<%= added.Name %>' ajouté a votre panier !
		</div>

		<%
	}
        
        if(vedettes.size() > 0) {
        ProductModel vedette = vedettes.get(0);
    %>
    
    <div class="jumbotron">
        <div class="container">
            <div class=row>
                <div class="col-md-8">
                    <h1><%= vedette.Name %></h1>
                    <p><%=vedette.Description %></p>
                    <p>
                    <b><%=vedette.Price%> $</b> seulement <br/>
                    <form method=post>
                                    <input type=hidden name=action value=addtocart />
                                    <input type=hidden name=productCode value="<%= vedette.ProductId %>" />
                                        <button type=submit class="btn btn-success btn-lg">Add to cart</button>
                                        </form>
                    </p>
                </div>
                <div class="col-md-4">
                    <img style="width:350px" class="group list-group-image" src="images/<%= vedette.Image  %>"  />
                </div>
            </div>
        </div>
    </div>

    
    <%
    }
    for(int i = 0; i < products.size(); i++) {
        ProductModel product = products.get(i);
        //File picturefile = new File( getClass().getResource("/images/" + product.getImage()) );
        //InputStream input = getClass().getResourceAsStream("/images/" + product.getImage());
        //InputStream input = new FileInputStream(picturefile);
        //byte[] bf = new byte[(int)picturefile.length()];
        //input.read(bf);
        
    %><br/>
        <div class="item col-xs-4 col-lg-4">
            <div class="thumbnail">
            <!---<img class="group list-group-image" src="data:image/gif;base64, product.getImageAsBase64()  "  />-->
                <img class="group list-group-image" src="images/<%= product.Image  %>"  />
                <div class="caption">
                    <h4 class="group inner list-group-item-heading">
                        <%= product.Name %> - <%= product.ProductId %></h4>
                    <p class="group inner list-group-item-text">
                        <%= product.Description %></p>
                    <div class="row">
                        <div class="col-xs-12 col-md-6">
                            <p class="lead">
                                <%= product.Price %>
                                </p>
                        </div>
                        <div class="col-xs-12 col-md-6">
                        <form method=post>
                        <input type=hidden name=action value=addtocart />
                        <input type=hidden name=productCode value="<%= product.ProductId %>" />
                            <button type=submit class="btn btn-success">Add to cart</button>
                            </form>

                        <% if(authenticatedUser !=null && authenticatedUser.IsAdmin) { %>
                    <form method=post>
                    <input type=hidden name=action value=sponsor />
                        <input type=hidden name=productCode value="<%= product.ProductId %>" />
                            <button type=submit class="btn btn-warning">Sponsor !</button>
                            </form>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
        }
        %>
    </div>
<%@ include file="foot.jsp" %>