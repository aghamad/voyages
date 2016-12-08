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
 

			<!-- START BANNER -->
		      <div class="tp-banner-container">
		        <div class="tp-banner-slider">
		          <ul>
		                <li 
		                data-masterspeed="700"
		                 data-slotamount="7"
		                  data-transition="fade">
		                  <img
		                  	src="http://html.creaws.com/suntour/rs-plugin/assets/loader.gif" 
		                  	data-lazyload="<c:url value="/images/${product.image}"/>"
		                  	data-bgposition="center" 
		                  	alt=""
		                  	data-kenburns="on"
		                  	data-duration="30000" 
		                  	data-ease="Linear.easeNone" 
		                  	data-scalestart="100" 
		                  	data-scaleend="120" 
		                  	data-rotatestart="0" 
		                  	data-rotateend="0" 
		                  	data-offsetstart="0 0" 
		                  	data-offsetend="0 0" 
		                  	data-bgparallax="10">
		                  	<div 
		                  		data-x="['center','center','center','center']"
		                  		data-y="center" 
		                  		data-transform_in="x:-150px;opacity:0;s:1500;e:Power3.easeInOut;" 
		                  		data-transform_out="x:150px;opacity:0;s:1000;e:Power2.easeInOut;s:1000;e:Power2.easeInOut;"
		                  		data-start="400"
		                  		class="tp-caption sl-content">
				                <div class="sl-title-top">Welcome to</div>
				                <div class="sl-title">${product.name} </div>
				                <div class="sl-title-bot">Starting <span>${product.price}$</span> </div>
				                <br/>
				                <form method=post action="<c:url value="/products"/>">
                                    <input type=hidden name=action value=addtocart />
                                    <input type=hidden name=productCode value="${ product.productId  }" />
                                    <button type=submit class="btn btn-success btn-lg">Add to cart</button>
                                </form>
				              </div>
				              
		                </li>
		                </ul>
		            </div>
		            </div>

 <!--  
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
        -->
<br/>

        <div class="list-group">
  
        <!-- recomended section-->
      <section class="small-section bg-gray">
        <div class="container">
          <div class="row">
            <div class="col-md-8">
              <h6 class="title-section-top font-4">Top rated</h6>
              <h2 class="title-section"><span>Votre</span> Séjour</h2>
              <div class="cws_divider mb-25 mt-5"></div>
              <p>
              Offrez ce voyage comme cadeau à votre famille et vivez un séjour sans précédent.
              </p>
            </div>
            <div class="col-md-4"><i class="flaticon-suntour-hotel title-icon"></i></div>
          </div>
          <div class="row">
            <!-- Recomended item-->
            <div class="col-md-12">
           		<c:forEach items="${product.escales}" var="Escale">
           		  	<c:if test="${ Escale.isDepart }">
		        		<h3 style="text-align:center"><i class="fa fa-fighter-jet" aria-hidden="true"></i>
		        		Départ de ${Escale.city.name}</h3>
		        		<br/>
		        	</c:if>
		        	
		        	<c:if test="${ !Escale.isArrivee && !Escale.isDepart }">
		            	<div class="recom-item">
			                <div class="recom-media">
			                	<a href="hotels-details.html">
				                    <div class="http://html.creaws.com/suntour/pic">
				                    <img 
					                    src="<c:url value='/images/${ Escale.image }' />"
					                    data-at2x="http://html.creaws.com/suntour/pic/recomended/1@2x.jpg"
					                    alt >
				                    </div>
			                    </a>
			                	<div class="location">
			                		<i class="flaticon-suntour-map"></i> ${Escale.city.name}
			                		</div>
			                </div>
			                <!-- Recomended Content-->
			                <div class="recom-item-body"><a href="hotels-details.html">
			                    <h6 class="blog-title">${Escale.city.name}</h6></a>
			                  <div class="stars stars-4"></div>
			                  <!-- <div class="recom-price"><span class="font-4">$90</span> per night</div>-->
			                  <p class="mb-30">${Escale.descriptionActivite}</p>
			                  <!-- <a href="hotels-details.html" class="recom-button">Read more</a>-->
								<form method=post action="<c:url value="/products"/>">
                                    <input type=hidden name=action value=addtocart />
                                    <input type=hidden name=productCode value="${ product.productId  }" />
                                    <button type=submit class="cws-button small alt">Book now</button>
                                </form>
			                  <!--  <a href="hotels-details.html" class="cws-button small alt">Book now</a>-->
			                  <!-- <div class="action font-2">20%</div>-->
			                </div>
			                <!-- Recomended Image-->
						</div>
						
		        	</c:if>
		        	
		        	<c:if test="${ Escale.isArrivee }">
		        		<br/>
			        	<h3 style="text-align:center">
			        	<i class="fa fa-fighter-jet" aria-hidden="true"></i>
			        	 Retour à ${Escale.city.name}</h3>
			        </c:if>
				</c:forEach>
			</div>
		</div>
		</div>
	</section>
            
        <!--<c:forEach items="${product.escales}" var="Escale">
        
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
        </c:forEach>-->
        
        <script>/*$('.pull-down').each(function() {
        	  var $this = $(this);
        	  $this.css('margin-top', $this.parent().height() - $this.height())
        	});*/
        </script>
    </div>
    </div>
<%@ include file="foot.jsp" %>