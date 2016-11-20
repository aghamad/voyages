package voyages;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Signup
 */
@WebServlet(name = "Signup", urlPatterns = {"/signup"})
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        User new_user;
        
        try {
            new_user = new User(Connexion.getConnexion());
            
            new_user.Password = request.getParameter("Password");
            new_user.FirstName = request.getParameter("FirstName");
            new_user.LastName = request.getParameter("LastName");
            new_user.Address = request.getParameter("Address");
            new_user.Email = request.getParameter("Email");
            
            new_user.create();
            response.sendRedirect("/Commerce-Project1-context-root/login");
            
        } catch (DAOException e) {
            request.setAttribute("error", e);
        } catch (ClassNotFoundException | SQLException e) {
            request.setAttribute("error", e);
        } catch (Exception e) {
            request.setAttribute("error", e);
        } 
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }
}
