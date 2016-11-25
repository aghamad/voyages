<%@ page language="java" contentType="text/html;charset=windows-1252" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.nio.file.Path" %>
<%@ page import = "java.nio.file.Paths" %>
<%@ page import = "java.io.InputStream" %>
<%@ page import = "java.io.FileInputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.Arrays" %>
<%@ page import = "voyages.models.implementations.*" %>

<%@ include file="head.jsp" %>

 <div class="container">
  <div class="row">
     <div class="col-sm-6 col-md-4 col-md-offset-4">
         <h1 class="text-center login-title">Ajouter un escale</h1>
         <div class="account-wall">
             <img class="profile-img" src="https://cdn4.iconfinder.com/data/icons/aviation-cobalt/512/airplane_aircraft_airport_avion_flight_aeroplane_airlines-128.png" alt="">
             
	         <form class="form-signin" method=post>
	             Ajouter un escale pour le voyage #<select name="voyages"></select>
	             <input type="text" name=prix class="form-control" placeholder="Prix" required autofocus>
	             
	             <input type="text" id="datescale" placeholder="Date escale" required autofocus>
	             
	             <button class="btn btn-lg btn-primary btn-block" type="submit">
	                 Ajouter l'escale</button>
	                       
             </form>
             
         </div>
     </div>
 </div>
 </div>
 
<script>
	$(function() {
		$("#datescale").datepicker();
	});
	
</script>


<%@ include file="foot.jsp" %>