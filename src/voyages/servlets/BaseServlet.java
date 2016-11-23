package voyages.servlets;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import voyages.db.Connexion;

public abstract class BaseServlet extends HttpServlet {
	public Connexion getConnexion() throws ClassNotFoundException, SQLException, Exception {
		return Connexion.getOrSetUpConnexion(this.getServletContext());
	}
}
