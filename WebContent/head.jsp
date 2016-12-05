<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ page import = "voyages.models.implementations.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <title>products</title>
        <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <link rel="alternate" type="application/rss+xml" title="Latest snippets from Bootsnipp.com" href="http://bootsnipp.com/feed.rss">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.min.css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
	  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <style>body { padding-top: 50px; }
    body,html,.row-offcanvas {
  height:100%;
}


#sidebar {
  width: inherit;
  min-width: 220px;
  max-width: 220px;
  background-color:#f5f5f5;
  float: left;
  height:100%;
  position:relative;
  overflow-y:auto;
  overflow-x:hidden;
}
#main {
  height:100%;
  overflow:auto;
}

/*
 * off Canvas sidebar
 * --------------------------------------------------
 */
@media screen and (max-width: 768px) {
  .row-offcanvas {
    position: relative;
    -webkit-transition: all 0.25s ease-out;
    -moz-transition: all 0.25s ease-out;
    transition: all 0.25s ease-out;
    width:calc(100% + 220px);
  }
    
  .row-offcanvas-left
  {
    left: -220px;
  }

  .row-offcanvas-left.active {
    left: 0;
  }

  .sidebar-offcanvas {
    position: absolute;
    top: 0;
  }
}
</style>
    </head>
    <body>
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
      
       
        <div class="navbar-header">
            <div class="navbar-brand"><a href="<c:url value="/products"/>">Voyages Inc.</a></div>
           <!--  <span class="text-muted credit">
            Active Users
            </span> -->
        </div>

                
                
        <!-- DROPDOWN LOGIN STARTS HERE  -->
        <ul id="signInDropdown" class="nav navbar-nav navbar-right">
          <li><a href="<c:url value="/cart"/>"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>
</a></li>
<li><a href="orders"><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
</a></li>
		<%
            User authenticatedUser = User.getAuthenticatedUser(request);
          if(authenticatedUser == null) {
		%>
                <a href="<c:url value="/login"/>" type="button" class="btn btn-default navbar-btn">Sign in</a>
<!--
          <li class="dropdown">
            <button href="login" type="button" id="dropdownMenu1" data-toggle="dropdown" class="btn btn-default navbar-btn dropdown-toggle"><i class="glyphicon glyphicon-user color-blue"></i> Login <span class="caret"></span></button>
          <ul class="dropdown-menu">
            <li>
              <form method=post action="/login" class="navbar-form form" role="form">
                <div class="form-group">
                  <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-envelope color-blue"></i></span>
                    <!--EMAIL ADDRESS--
                    <input id="emailInput" placeholder="email address" class="form-control" type="email" oninvalid="setCustomValidity('Please enter a valid email address!')" onchange="try{setCustomValidity('')}catch(e){}" required="">
                  </div>
                </div>           
                <div class="form-group">
                  <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock color-blue"></i></span>
                    <!--PASSWORD--
                    <input id="passwordInput" placeholder="password" class="form-control" type="password" oninvalid="setCustomValidity('Please enter a password!')" onchange="try{setCustomValidity('')}catch(e){}" required="">
                  </div>
                </div>
                <!--  BASIC ERROR MESSAGE
                <div class="form-group">
                    <label class="error-message color-red">*Email &amp; password don't match!</label>
                </div>
                -->
                <div class="form-group">
                  <!--BUTTON--
                  <button type="submit" class="btn btn-primary form-control">Login</button></div>
                <div class="form-group">
                  <!--RESET PASSWORD LINK--
                  <span class="pull-right"><a href="#">Forgot Password?</a></span></div>
              </form>
            </li>
          </ul>
          </li>-->
            
          <li>
          <%
        } if(authenticatedUser != null) {
            %>
            <a href=<c:url value="/profile"/>><span class="glyphicon glyphicon-user"></span> <span class="navbar-text"> <%= authenticatedUser.FirstName %></span></a>
            <%
        } %>
                </li>
        </ul>
        <!-- DROPDOWN LOGIN ENDS HERE  -->
		
      </div>
    </nav>
    