
package voyages.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.models.implementations.CityModel;
import voyages.models.implementations.User;

/**
 * Servlet implementation class Signup
 */
@WebServlet(
    name = "Signup",
    urlPatterns = {"/signup"})
public class Signup extends BaseServlet {
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
        User new_user;

        try {
            new_user = new User(getConnexion());

            new_user.Password = request.getParameter("Password");
            new_user.FirstName = request.getParameter("FirstName");
            new_user.LastName = request.getParameter("LastName");
            new_user.Address = request.getParameter("Address");
            new_user.Email = request.getParameter("Email");

            new_user.create();

            request.setAttribute("fail",
                Boolean.TRUE);

            request.getRequestDispatcher("/ajout.jsp").forward(request,
                response);

        } catch(DAOException e) {
            request.setAttribute("error",
                e);
        } catch(ConnexionException e) {
            request.setAttribute("error",
                e);
        } catch(Exception e) {
            request.setAttribute("error",
                e);
        }
        request.getRequestDispatcher("/signup.jsp").forward(request,
            response);
    }

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE);

        CityModel cityModel;
        List<CityModel> cities = Collections.emptyList();

        try {
            cityModel = new CityModel(getConnexion());
            cities = cityModel.getAll();
        } catch(
            ConnexionException
            | DAOException e) {
            throw new ServletException(e);
        }

        request.setAttribute("cities",
            cities);
        request.getRequestDispatcher("/signup.jsp").forward(request,
            response);
    }
}
