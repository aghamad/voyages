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

    <%
    boolean loginFail = false;
    if(request.getAttribute("fail") != null) {
        %>
        <div class="alert alert-info">
          <strong>Wrong credentials!/ <%= request.getParameter("email") %>, <%= request.getParameter("password") %> </strong> Please try again
        </div>
        <%
        loginFail = true;
    }
    
    //out.print(System.getProperty("user.dir"));
         
    %>

        <div class="container">
         <div class="row">
            <div class="col-sm-6 col-md-4 col-md-offset-4">
                <h1 class="text-center login-title">Sign in</h1>
                <div class="account-wall">
                    <img class="profile-img" src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=120"
                        alt="">
                    <form class="form-signin" method=post>
                    <input type="text" name=email class="form-control" placeholder="Email" required autofocus>
                    <input type="password" name="password" class="form-control" placeholder="Password" required>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">
                        Sign in</button>
                    <label class="checkbox pull-left">
                        <input type="checkbox" value="remember-me">
                        Remember me
                    </label>
                    <a href="#" class="pull-right need-help">Need help? </a><span class="clearfix"></span>
                    <a href="signup" class="pull-right need-help">Sign up </a><span class="clearfix"></span>
                    
                    </form>
                </div>
                <!--<a href="#" class="text-center new-account">Create an account </a>-->
            </div>
        </div>
        </div>

<%@ include file="foot.jsp" %>