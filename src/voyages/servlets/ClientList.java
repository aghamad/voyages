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
 * Servlet implementation class ClientList
 */
public class ClientList extends AdminServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		if(hasRedirected())
			return;
		
		User model;
		try {
			model = new User(getConnexion());
			request.setAttribute("users", model.getAll());
		} catch (ConnexionException|DAOException e) {
			throw new ServletException(e);
		}
		
		request.getRequestDispatcher("/clients.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		
		User model;
		try {
			model = new User(getConnexion());
			request.setAttribute("users", model.getAll());
		} catch (ConnexionException|DAOException e) {
			throw new ServletException(e);
		}
		
		request.getRequestDispatcher("/clients.jsp").forward(request, response);
	}

}
