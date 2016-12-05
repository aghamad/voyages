
package voyages.servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.DAOException;
import voyages.models.implementations.User;

/**
 * Servlet implementation class Profile
 */
@WebServlet(
    name = "Profile",
    urlPatterns = {"/profile"})
public class Profile extends BaseServlet {
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
        User authUser = null;
        if(request.getSession().getAttribute("authUser") instanceof User) {
            authUser = (User) request.getSession().getAttribute("authUser");
        }

        try {
            authUser.FirstName = request.getParameter("FirstName");
            authUser.LastName = request.getParameter("LastName");
            authUser.Address = request.getParameter("Address");
            authUser.Email = request.getParameter("Email");
            authUser.CityId = Long.parseLong(request.getParameter("CityId"));

            authUser.update();
            request.getRequestDispatcher("/profile.jsp").forward(request,
                response);
        } catch(DAOException e) {
            request.setAttribute("error",
                e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {

        if(User.getAuthenticatedUser(request) == null) {
            response.sendRedirect("login");
            return;
        }

        request.setAttribute("authUser",
            User.getAuthenticatedUser(request));

        request.getRequestDispatcher("/profile.jsp").forward(request,
            response);

    }

}
