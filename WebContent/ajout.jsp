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
  <div class="row"><%
        if(request.getAttribute("error") != null){
         %>
         <%= request.getAttribute("error").toString() %>
         <% } %>
     <div class="col-sm-6 col-md-4 col-md-offset-4">
         <h1 class="text-center login-title">Ajouter un voyage</h1>
         <div class="account-wall">
             <img class="profile-img" src="https://cdn4.iconfinder.com/data/icons/aviation-cobalt/512/avion_aviation_wings_flying_airlines_avia_airliner-128.png" alt="">
             
	         <form class="form-signin" method=post>
	         
	         	 <!-- Add voyage -->
	             <input type="text" name=Nom class="form-control" placeholder="Nom" required autofocus>
	             <input type="text" name=Price class="form-control" placeholder="Prix" required autofocus>
	             <textarea name="Description" rows="4" cols="50" placeholder="Description" style="width:100%;"></textarea>
	             <div class="input-group" >
		             <input type="text" name="Datedebut" id="datedebut" placeholder="Date début" style="width:100%;" required autofocus>
		             <span class="input-group-addon">-></span>
		             <input type="text" name="Datefin" id="datefin" placeholder="Date fin" style="width:100%;" required autofocus>
	             </div>
	                              
		         <!-- angular app -->  
		         <div ng-app="myApp" ng-controller="MainCtrl">
		         	<h3>Escale</h3>
		         	<fieldset  data-ng-repeat="choice in choices">
		         		<div class="input-group" style="margin-bottom:5%;">
				      	 <input type="text" ng-model="choice.name" name="CityName" placeholder="Nom de la ville">
				      	 <input type="text" ng-model="choice.name" name="ActivitieName" placeholder="Nom de l'activité">
				      	 <textarea name="DescriptionActivitie" rows="4" cols="50" placeholder="Description de l'activité" style="width:100%;"></textarea>
				      	 <label for="dateEscale">Date de l'escale:  </label>
				      	 <input type="text" name="DateEscale" id="dateEscale" placeholder="DD-MM-YYYY">
				      	 <br />
				      	 <button class="remove btn-danger" ng-show="$last" ng-click="removeChoice()">Delete</button>
				      	</div>
				   	</fieldset>  
		       		<button class="addfields" ng-click="addNewChoice($event)" style="margin-bottom:5%;">Add escale</button>  	   
	          	</div> 
	         	<!-- end of myapp  -->
	         	<button class="btn btn-lg btn-primary btn-block" type="submit">Ajouter voyage</button>  
  
	      	</form> 
	      	<!-- /endform -->
         
         </div>
     </div>
 </div>
 </div>

<script type="text/javascript">
	$(function() {
		$('#datedebut').datepicker({ dateFormat: 'dd-mm-yy' }).val();
	});
	  
	$( function() {
		$('#datefin').datepicker({ dateFormat: 'dd-mm-yy' }).val();
	});

	
	// Angular App and Controller 
	
	var app = angular.module('myApp', []);
	  app.controller('MainCtrl', function($scope) {

	  $scope.choices = [];
	  
	  $scope.addNewChoice = function(event) {
		event.preventDefault();
	    var newItemNo = $scope.choices.length + 1;
	    $scope.choices.push({'id':'choice'+newItemNo});
	  };
	    
	  $scope.removeChoice = function() {
	    var lastItem = $scope.choices.length - 1;
	    $scope.choices.splice(lastItem);
	  };	
	  
	});
	
</script>

<%@ include file="foot.jsp" %>