package voyages.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.models.implementations.User;

/**
 * Servlet implementation class ClientProfile
 */

public class ClientProfile extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User authUser = (User) request.getAttribute("authUser");
		
		if(authUser == null || !authUser.IsAdmin) {
			request.getRequestDispatcher("/products").forward(request, response);
			return;
		}
		
		int clientId = Integer.parseInt(request.getParameter("id"));
		User client;
		try {
			client = new User(getConnexion());

			client.id = clientId;
			client.read();
		} catch (ConnexionException | DAOException e) {
			throw new ServletException(e);
		}
		
		request.setAttribute("client", client);
		
		request.getRequestDispatcher("/clientProfile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
