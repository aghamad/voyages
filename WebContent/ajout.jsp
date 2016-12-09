
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.nio.file.Path" %>
<%@ page import = "java.nio.file.Paths" %>
<%@ page import = "java.io.InputStream" %>
<%@ page import = "java.io.FileInputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.Arrays" %>
<%@ page import = "voyages.models.implementations.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="head.jsp" %>

<%@ include file="admin_head.jsp" %>
 
  <div class="row">
     <div class="col-sm-6 col-md-4 col-md-offset-4">
         <h1 class="text-center login-title">Ajouter un voyage</h1>
         <div class="account-wall">
             <img class="profile-img" src="https://cdn4.iconfinder.com/data/icons/aviation-cobalt/512/avion_aviation_wings_flying_airlines_avia_airliner-128.png" alt="">
             
	         <form class="form-signin" method=post>
	         	                              
		         <!-- angular app -->  
		         <div ng-app="myApp" ng-controller="MainCtrl">
		         
		         	 <!-- Add voyage -->
		             <input type="text" name=Nom class="form-control" placeholder="Nom" required autofocus>
		             <input type="text" name=Image class="form-control" placeholder="Chemin vers l'image" required autofocus>
		             <input type="text" name=Price class="form-control" placeholder="Prix" required autofocus>
		             <textarea name="Description" rows="4" cols="50" placeholder="Description" style="width:100%;"></textarea>
		             
		             <div class="input-group" >
			             <input type="text" ng-model="dateStart" name="Datedebut" id="datedebut" placeholder="Date début" style="width:100%;" required autofocus>
			             <span class="input-group-addon">-></span>
			             <input type="text" name="Datefin" id="datefin" placeholder="Date fin" style="width:100%;" required autofocus>
		             </div>
	                               
		         	<fieldset  data-ng-repeat="item in items track by $index">	
		         		<h3>Escale # {{$index + 1}} </h3>
		         		<div class="input-group" style="margin-bottom: 5%;" >	         		
		         		 <label for="SelCity{{$index}}">La ville:</label>
		         		 <br />
					     <select class="form-control" name="SelCity{{$index}}" required autofocus>
					   		<c:forEach items="${cities}" var="City">
								<option value="${City.cityId}" >${City.name}</option>
							</c:forEach>>
					  	</select>
					 
						 <br />
						 <label for="NomActivite{{$index}}">Nom de l'activité</label>
						 <br />
				      	 <input name="NomActivite{{$index}}" type="text" ng-model="item.NomActivite" required autofocus>				      	 
				      	 <br />
				      	 <label for="ImageActivite{{$index}}">Image de l'activité</label>
				      	 <br />
						 <input type="text" name=Image class="form-control" placeholder="Chemin vers l'image" required="{{$index != 0}}" autofocus>
						    	 
				      	 <label for="DescriptionActivite{{$index}}">Description de l'activité</label>
				      	 <textarea ng-model="item.DescriptionActivite" name="DescriptionActivite{{$index}}" rows="4" cols="50" style="width:100%;" required autofocus></textarea>				      	 
				      	 <br />
				      	 <label for="DateEscale{{$index}}">Date de l'escale au format DD-MM-YYYY: </label>
				      	 <br />
				      	 <input name="DateEscale{{$index}}" class=dateInput id="EscaleDate{{$index}}" type="text" required autofocus>
				      	 <br />
				      	 <button class="remove btn-danger" ng-show="$last" ng-click="removeItem($event)">Delete</button>
				      	</div>		
				   	</fieldset>  
				   	
		       		<button class="addfields" ng-click="addNewItem($event)">Add escale</button>  	
		       		<input name="nbescales" ng-value="items.length" style="visibility: hidden;">   
	          	</div> 
	         	<!-- end of myapp  -->
	         
	         	<button class="btn btn-lg btn-primary btn-block" type="submit" style="margin-bottom:15%;">Ajouter voyage</button>  
  
	      	</form> 
	      	<!-- /endform -->
         
         </div>
     </div>
 </div>
 <%@ include file="admin_foot.jsp" %>
 

<script type="text/javascript">

	
	$(function() {
		$('#datedebut').datepicker({ dateFormat: 'dd-mm-yy' }).val();
	});
	  
	$( function() {
		$('#datefin').datepicker({ dateFormat: 'dd-mm-yy' }).val();
	});
	
	
	  
	var app = angular.module('myApp', []);
	  app.controller('MainCtrl', function($scope) {
		  
	  $scope.items = [];
	  
	  $scope.addNewItem = function(event) {
		event.preventDefault();
		
		
	    var newItemNo = $scope.items.length + 1;
	    
	    if (newItemNo == 1){
	    	// Montreal par defaut
		    $scope.items.push({
		    	id: "item" + newItemNo,
		    	NomActivite: "Ville de départ et d'arrivée",
		    	DescriptionActivite: "Notez qu'il n'est pas nécessaire de rajouter cette ville en tant qu'escale de fin. Cela se fera automatiquement.",
		    	DateEscale: $('#datedebut').datepicker({ dateFormat: 'dd-mm-yy' }).val()
		    });
	    } else {
		    $scope.items.push({
		    	id: "item" + newItemNo,
		    	CityName: "",
		    	NomActivite: "",
		    	DescriptionActivite: "",

		    });
	    }
	    setTimeout(500, function() {
	    	$('#EscaleDate' + (newItemNo-1)).datepicker({ dateFormat: 'dd-mm-yy' }).val();
	    });
		
	  };
	    
	  // Deletes the last item 
	  $scope.removeItem = function(event) {
		event.preventDefault();
	    var lastItem = $scope.items.length - 1;
	    $scope.items.splice(lastItem);
	  };	
	  
	});
	
</script>

<%@ include file="foot.jsp" %>