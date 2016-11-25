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
         <h1 class="text-center login-title">Ajouter un voyage</h1>
         <div class="account-wall">
             <img class="profile-img" src="https://cdn4.iconfinder.com/data/icons/aviation-cobalt/512/avion_aviation_wings_flying_airlines_avia_airliner-128.png" alt="">
             
	         <form class="form-signin" method=post>
	             <input type="text" name=nom class="form-control" placeholder="Nom" required autofocus>
	             <input type="text" name=prix class="form-control" placeholder="Prix" required autofocus>
	             <textarea name="description" rows="4" cols="50" placeholder="Description" style="width:100%;"></textarea>
	             
	             <div class="input-group">
		             <input type="text" id="datedebut" placeholder="Date début" style="width:100%;" required autofocus>
		             <span class="input-group-addon">-></span>
		             <input type="text" id="datefin" placeholder="Date fin" style="width:100%;" required autofocus>
	             </div>
	             
	             <button class="btn btn-lg btn-primary btn-block" type="submit">
	                 Ajouter voyage</button>
	                       
             </form>
             
         </div>
     </div>
 </div>
 </div>

<script>
	$(function() {
		$("#datedebut").datepicker();
	});
	  
	$( function() {
		$("#datefin").datepicker();
	});
	
</script>

<%@ include file="foot.jsp" %>