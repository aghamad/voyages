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
    
<div class="container" style="padding-top:30px">
 
            <div class=row style="background-position: center center; min-height:500px; background-image: url('<c:url value='/images/${ product.image }' /> '); background-size:cover; ">
                <div class="col-md-4 col-md-offset-8" style="min-height:450px; color: white; text-decoration:bold;">
                	<div class="pull-down">
                    <h1>${product.name}</h1>
                    <p>${product.description}</p>
                    <p>
                    <b>${product.price} $</b> seulement <br/>
                    <form method=post>
                                    <input type=hidden name=action value=addtocart />
                                    <input type=hidden name=productCode value="${ product.productId  }" />
                                        <button type=submit class="btn btn-success btn-lg">Add to cart</button>
                                        </form>
                    </p>
                    </div>
                </div>
                
            </div>
        
<br/>
<h3>Votre Séjour</h3>
        <div class="list-group">
  
        <c:forEach items="${product.escales}" var="Escale">
        
  			<div class="list-group-item">
  				<c:if test="${ Escale.isDepart }">
		        	<h3>Départ de ${Escale.city.name}</h3>
		        </c:if>
		        
		        <c:if test="${ !Escale.isArrivee && !Escale.isDepart }">
		  			<img style="float:left; width: 200px; margin-right:20px;" src="<c:url value='/images/${Escale.image}' />"  />   
		        	<h1>${Escale.nomActivite} à ${Escale.city.name} </h1>
		        	<h3>${Escale.descriptionActivite}</h3>
		        	
	        	</c:if>
	        	<c:if test="${ Escale.isArrivee }">
		        	<h3>Retour à ${Escale.city.name}</h3>
		        </c:if>
        	</div>
        </c:forEach>
        
        <script>$('.pull-down').each(function() {
        	  var $this = $(this);
        	  $this.css('margin-top', $this.parent().height() - $this.height())
        	});</script>
    </div>
    
<%@ include file="foot.jsp" %>