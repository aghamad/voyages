
package voyages.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.DAOException;
import voyages.db.Connexion;
import voyages.models.implementations.User;

/**
 * Servlet implementation class Ajout d'un voyage
 */
@WebServlet(
    name = "AjoutEscale",
    urlPatterns = {"/ajoutescale"})
public class AjoutEscale extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE);
        // Add le voyage dans la bd
        
        // redirect to form escale if success
        request.getRequestDispatcher("/ajoutescale.jsp").forward(request,
                response);
        
        /*
        User new_user;

        try {
            new_user = new User(getConnexion());

            new_user.Password = request.getParameter("Password");
            new_user.FirstName = request.getParameter("FirstName");
            new_user.LastName = request.getParameter("LastName");
            new_user.Address = request.getParameter("Address");
            new_user.Email = request.getParameter("Email");

            new_user.create();
            response.sendRedirect("/Commerce-Project1-context-root/login");

        } catch(DAOException e) {
            request.setAttribute("error",
                e);
        } catch(
            ClassNotFoundException
            | SQLException e) {
            request.setAttribute("error",
                e);
        } catch(Exception e) {
            request.setAttribute("error",
                e);
        }
        request.getRequestDispatcher("/signup.jsp").forward(request,
            response);
       */
    }

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE);

        request.getRequestDispatcher("/ajoutEscale.jsp").forward(request,
            response);
    }
}
