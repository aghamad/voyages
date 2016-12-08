<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Arrays" %>
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

<%@ include file="head.jsp" %>

  
    <div class="content-body">
	Type : ${type}
    <c:if test='${type == "relevant"} '>>
    	<a href="?type=all">Afficher tous les voyages</a>
    </c:if>
    <c:if test='${type == "all"} '>
    	<a href="?type=relevant">Afficher seulement les voyages qui partent de votre ville : ${userCity.name} </a>
    </c:if>
    
  
	<c:if test="${addedProduct == null}">
		<div class="alert alert-success">
			'${addedProduct.Name}' ajouté  votre panier !
		</div>
    </c:if>

        
	<c:if test="${fn:length(vedettes) gt 0}">

    <!-- START BANNER -->
      <div class="tp-banner-container">
        <div class="tp-banner-slider">
          <ul>
				<c:forEach items="vedettes" var="current_vedette">
                <li 
                data-masterspeed="700"
                 data-slotamount="7"
                  data-transition="fade">
                  <img
                  	src="http://html.creaws.com/suntour/rs-plugin/assets/loader.gif" 
                  	data-lazyload="${current_vedette.image}"
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
		                <div class="sl-title">${current_vedette.name}></div>
		                <div class="sl-title-bot">Starting <span>${current_vedette.price}></span> per night</div>
		              </div>
		              
		              <!-- 
                    <h1>${vedette.Name}></h1>
                    <p>${vedette.Description}</p>
                    <p>
                    <b>${vedette.Price} $</b> seulement <br/>
                    <form method=post>
                                    <input type=hidden name=action value=addtocart />
                                    <input type=hidden name=productCode value="${vedette.productId}" />
                                        <button type=submit class="btn btn-success btn-lg">Add to cart</button>
                                        </form>
                    </p>
                 -->
                 
                <!-- 
                <div class="col-md-4">
                <a href='<c:url value="/products/detail/?id=${vedette.productId}" /> '>
                    <img style="width:350px" class="group list-group-image" src="images/${ vedette.image } "  />
                    
                    </a>
                </div> -->
                </li>
                </c:forEach>
                </ul>
            </div>
            <!-- slider info-->
	        <div class="slider-info-wrap clearfix">
	          <div class="slider-info-content">
				<c:forEach items="vedettes" var="current_vedette">
		            <div class="slider-info-item">
		              <div class="info-item-media">
		              	<img src="images/${current_vedette.image}"
		              	data-at2x="http://html.creaws.com/suntour/pic/slider-info-1@2x.jpg"
		              	alt="" />
		                <div class="info-item-text">
		                  <div class="info-price font-4"><span>start per night</span> $ ${current_vedette.price}</div>
		                  <div class="info-temp font-4"><span>local temperature</span> 36° / 96.8°</div>
		                  <p class="info-text">
		                  	${current_vedette.description}
		                  </p>
		                </div>
		              </div>
		              <div class="info-item-content">
		                <div class="main-title">
		                  <h3 class="title"><span class="font-4">${current_vedette.name}</span> </h3>
		                  <div class="price">
		                  	<span>$105</span> per night</div>
		                  
		                  	<a href="<c:url value="/products/detail/?id=${vedette.productId}" />" class="button">Details</a>
		                </div>
		              </div>
		            </div>
	            </c:forEach>
	        </div>
        </div>
    </div>
    </c:if>
    <!--  END BANNER -->


    <c:if test="${fn:length(products) gt 0}">
        <br/>
    
    <!-- page section-->
      <section class="page-section pb-0">
        <div class="container">
          <div class="row">
            <div class="col-md-8">
              <h6 class="title-section-top font-4">Special offers</h6>
              <h2 class="title-section"><span>Popular</span> Destinations</h2>
              <div class="cws_divider mb-25 mt-5"></div>
              <p>Nullam ac dolor id nulla finibus pharetra. Sed sed placerat mauris. Pellentesque lacinia imperdiet interdum. Ut nec nulla in purus consequat lobortis. Mauris lobortis a nibh sed convallis.</p>
            </div>
            <div class="col-md-4"><img src="http://html.creaws.com/suntour/pic/promo-1.png" data-at2x="http://html.creaws.com/suntour/pic/promo-1@2x.png" alt class="mt-md-0 mt-minus-70"></div>
          </div>
        </div>
        <div class="features-tours-full-width">
          <div class="features-tours-wrap clearfix">
          <c:forEach items="products" var="product">
            <div class="features-tours-item">
              <div class="features-media">
              	<img
              	src="images/${product.image}"
              	data-at2x="http://html.creaws.com/suntour/pic/tours/1@2x.jpg" alt>
                <div class="features-info-top">
                  <div class="info-price font-4"><span>start per night</span> ${product.price}</div>
                  <div class="info-temp font-4"><span>local temperature</span> 30° / 86°</div>
                  <p class="info-text">${product.description}.</p>
                </div>
                <div class="features-info-bot">
                  <h4 class="title"><span class="font-4">${product.name}</span> </h4>
                  <a
                   href='<c:url value="/products/detail/?id=${product.productId}" />'
                   class="button">Details</a>
                </div>
              </div>
            </div>
    <!--  --
        <div class="item col-xs-4 col-lg-4">
            <div class="thumbnail">
                <img class="group list-group-image" src="images/${product.image}"  />
                <div class="caption">
                    <h4 class="group inner list-group-item-heading">
                        ${product.name} - ${product.productId}</h4>
                    <p class="group inner list-group-item-text">
                        ${product.Description}</p>
                    <div class="row">
                        <div class="col-xs-12 col-md-6">
                            <p class="lead">
                                ${product.Price}
                                </p>
                        </div>
                        <div class="col-xs-12 col-md-6">
                        <form method=post>
                        <input type=hidden name=action value=addtocart />
                        <input type=hidden name=productCode value="${product.productId}" />
                            <button type=submit class="btn btn-success">Add to cart</button>
                            </form>

						<c:if test="${authUser == null && authUser.isAdmin}">
		                    <form method=post>
		                    <input type=hidden name=action value=sponsor />
		                        <input type=hidden name=productCode value="${product.productId}" />
		                            <button type=submit class="btn btn-warning">Sponsor !</button>
		                    </form>
	                    </c:if>        
                        </div>
                    </div>
                </div>
            </div>
        </div>
        -->
        </c:forEach>
        </div>
        </div>
        </section>
        </c:if>
    </div>
<%@ include file="foot.jsp" %>