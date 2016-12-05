package voyages.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import voyages.models.implementations.User;

public abstract class AdminServlet  extends BaseServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User authUser = (User) request.getAttribute("authUser");
		
		if(authUser == null || !authUser.IsAdmin) {
			this.redirected = true;
			request.getRequestDispatcher("/products").forward(request, response);
			return;
		}
	}
	private boolean redirected;
	protected boolean hasRedirected() {
		return redirected;
	} 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User authUser = (User) request.getAttribute("authUser");
		
		if(authUser == null || !authUser.IsAdmin) {
			this.redirected = true;
			request.getRequestDispatcher("/products").forward(request, response);
			return;
		}
	}
}
