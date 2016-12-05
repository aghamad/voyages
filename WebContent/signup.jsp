<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.io.InputStream" %>
<%@ page import = "java.io.FileInputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.Arrays" %>
<%@ page import = "voyages.*" %>
<%@ include file="head.jsp" %>
        <div class="container">
         <div class="row"><%
        if(request.getAttribute("error") != null){
         %>
         <%= request.getAttribute("error").toString() %>
         <% } %>
         
            <div class="col-sm-6 col-md-4 col-md-offset-4">
                <h1 class="text-center login-title">Sign up</h1>
                <div class="account-wall">
                    
                    <form class="form-signin" method=post>
						<div class="input-container">
							<label for=Email>Email</label>
							<input type="text" id=Email name=Email class="form-control" placeholder="Email" required autofocus>
						</div>
						
						<div class="input-container">
							<label for=Password>Password</label>
							<input class="form-control" id=Password type="password" name="Password" placeholder="Password" required>
						</div>
						
						<div class="input-container">
							<label for=FirstName>First Name :</label>
							<input  class="form-control" type="text" name="FirstName" id="FirstName" placeholder="First Name" required>
						</div>

						<div class="input-container">
							<label for=LastName>Last Name :</label>
							<input class="form-control" type="text" name="LastName" id="FirstName" placeholder="Last name" required>
						</div>
						
						
						<div class="input-container">
							<label for=Address>Address</label>
							<input class="form-control" type="text" name="Address" id="Address" placeholder="Addres" required>
						</div>
						
						<div class="input-container">
							<label for=CityId>City</label>
							<select name=CityId id=CityId class="form-control" required autofocus>
						   		<c:forEach items="${cities}" var="City">
									<option  value="${City.cityId}" >${City.name}</option>
								</c:forEach>>
						  	</select>
					  	</div>
						<br/>
						<button class="btn btn-lg btn-primary btn-block" type="submit">
							Sign up</button>
						
						<span class="clearfix"></span>
                    </form>
                </div>
                <!--<a href="#" class="text-center new-account">Create an account </a>-->
            </div>
        </div>
        </div>
    </body>
</html>
    