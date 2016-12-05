
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>

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
    <div class="container" style="padding-top:30px">
    <!-- 
    <div class="well well-sm">
        <strong>Products</strong>
        <div class="btn-group">
            <a href="#" id="list" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-th-list">
            </span>List</a> <a href="#" id="grid" class="btn btn-default btn-sm"><span
                class="glyphicon glyphicon-th"></span>Grid</a>
        </div>
    </div>
 	-->
    
    <div id="products" class="row list-group">
    <%
    ProductModel productModel = new ProductModel(Connexion.getOrSetUpConnexion(getServletContext()));
    
    
    CityModel userCity = new CityModel(productModel);
    userCity.CityId = authenticatedUser.getCityId();
    userCity.read();
    
    
    List<ProductModel> initial_products = Collections.emptyList();
    String type = (String) request.getParameter("type");
    if(type == null) type = "relevant";
    if(type.equals("relevant")) {
    	%> <a href="?type=all">Afficher tous les voyages</a><%
    	initial_products = productModel.findByDepartureCity(userCity, new Date());	
    }
    if(type.equals("all")) {
    	%> <a href="?type=relevant">Afficher seulement les voyages qui partent de votre ville : <%= userCity.getName()%> </a><%
    	initial_products = productModel.getAll();	
    }
    
    
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
        request.setAttribute("vedette", vedette);
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
                                    <input type=hidden name=productCode value="${vedette.productId}" />
                                        <button type=submit class="btn btn-success btn-lg">Add to cart</button>
                                        </form>
                    </p>
                </div>
                <div class="col-md-4">
                <a href='<c:url value="/products/detail/?id=${vedette.productId}" /> '>
                    <img style="width:350px" class="group list-group-image" src="images/${ vedette.image } "  />
                    
                    </a>
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