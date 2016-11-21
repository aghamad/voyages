
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
 * Servlet implementation class Login
 */
@WebServlet(
    name = "Login",
    urlPatterns = {"/login"})
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @SuppressWarnings("boxing")
    @Override
    public void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connexion connexion = null;
        try {
            connexion = Connexion.getConnexion();
        } catch(
            ClassNotFoundException
            | SQLException e) {
            throw new ServletException(e);
        } catch(Exception e) {
            throw new ServletException(e);
        }

        User userDAO = new User(connexion);
        User authUser;

        try {
            authUser = userDAO.auth(email,
                password);
            //request.setAttribute("authUser", authUser);

            if(authUser != null) {
                request.getSession(true).setAttribute("authUser",
                    authUser);

                response.sendRedirect("/Commerce-Project1-context-root/products");
            } else {
                int attempt = 0;
                if(request.getAttribute("attempt") != null) {
                    attempt = Integer.parseInt((String) request.getAttribute("attempt"));
                }
                request.setAttribute("attempt",
                    attempt
                        + 1);
                request.setAttribute("fail",
                    true);

                request.getRequestDispatcher("/login.jsp").forward(request,
                    response);
            }

        } catch(IOException e) {
            throw new ServletException(e);
        } catch(DAOException e) {
            throw new ServletException(e);
        } catch(Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE);

        request.setAttribute("authUser",
            User.getAuthenticatedUser(request));
        request.getRequestDispatcher("/login.jsp").forward(request,
            response);

        /*PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Login</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();*/
    }
}
