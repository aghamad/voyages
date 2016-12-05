package voyages.servlets;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import exceptions.ConnexionException;
import voyages.db.Connexion;

public abstract class BaseServlet extends HttpServlet {
	public Connexion getConnexion() throws ConnexionException {
		return Connexion.getOrSetUpConnexion(this.getServletContext());
	}
	
}
