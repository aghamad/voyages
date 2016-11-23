<%@ page language="java" contentType="text/html;charset=windows-1252" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.io.InputStream" %>
<%@ page import = "java.io.FileInputStream" %>
<%@ page import = "java.io.File" %>
<%@ page import = "java.util.Arrays" %>
<%@ page import = "voyages.models.implementations.*" %>
<%@ include file="head.jsp" %>
        <div class="container">
         <div class="row">
         
         <%
        //User authenticatedUser = (User) request.getAttribute("authUser");
        if(request.getAttribute("error") != null){
         %>
         <%= request.getAttribute("error").toString() %>
         <% } %>
         
            <div class="col-sm-6 col-md-4 col-md-offset-4">
                <h1 class="text-center login-title">Profile</h1>
                <div class="account-wall">
                    
                    <form class="form-signin" method=post>
						<div class="input-container">
							<label for=Email>Email</label>
							<input value='<%= authenticatedUser.Email %>' type="text" id=Email name=Email class="form-control" placeholder="Email" required autofocus>
						</div>
                                            
						
						<div class="input-container">
							<label for=FirstName>First Name :</label>
							<input value='<%= authenticatedUser.FirstName %>' class="form-control" type="text" name="FirstName" id="FirstName" placeholder="First Name" required>
						</div>

						<div class="input-container">
							<label for=LastName>Last Name :</label>
							<input value='<%= authenticatedUser.LastName %>' class="form-control" type="text" name="LastName" id="FirstName" placeholder="Last name" required>
						</div>
						<div class="input-container">
							<label for=Address>Address</label>
							<input value='<%= authenticatedUser.Address %>' class="form-control" type="text" name="Address" id="Address" placeholder="Addres" required>
						</div>
						<button class="btn btn-lg btn-primary btn-block" type="submit">
							Save</button>
						
						<span class="clearfix"></span>
                    </form>
                </div>
                <!--<a href="#" class="text-center new-account">Create an account </a>-->
            </div>
        </div>

        </div>
<%@ include file="foot.jsp" %>
    