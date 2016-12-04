
package voyages.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.DAOException;
import voyages.models.implementations.EscaleModel;
import voyages.models.implementations.ProductModel;
import voyages.models.utils.DateParser;

/**
 * Servlet implementation class Ajout d'un voyage
 */
@WebServlet(
    name = "Ajout",
    urlPatterns = {"/ajout"})
public class Ajout extends BaseServlet {
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
        ProductModel new_voyage;
        EscaleModel new_escale;
        try {
        	new_voyage = new ProductModel(getConnexion());
        	
        	new_voyage.Name = request.getParameter("Nom");
        	new_voyage.Price = Double.parseDouble(request.getParameter("Price"));
        	new_voyage.Image = "";
        	new_voyage.IsVedette = 0;
        	new_voyage.Description = request.getParameter("Description");
        	new_voyage.DateDebut = DateParser.parse(request.getParameter("Datedebut"));
        	new_voyage.DateFin = DateParser.parse(request.getParameter("Datefin"));
        	new_voyage.create();
        	
        	int nbEscales = Integer.parseInt(request.getParameter("nbescales"));
        	// For every escale 
        	for(int i = 0; i < nbEscales; i++){     
	        	new_escale = new EscaleModel(getConnexion());
	        	new_escale.ProductId = new_voyage.ProductId;
	        	// get CityId avec le nom de la city
	        	new_escale.NomActivite = request.getParameter("NomActivite" + i);
	        	new_escale.DescriptionActivite = request.getParameter("DescriptionActivite" + i);
	        	new_escale.DateEscale = DateParser.parse(request.getParameter("DateEscale" + i));
	        	
	        	new_escale.create();
        	}
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
        
        request.getRequestDispatcher("/ajout.jsp").forward(request,
                response);
        
    }

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE );

        request.getRequestDispatcher("/ajout.jsp").forward(request,
            response);
    }
}


