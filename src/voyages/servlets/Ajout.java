
package voyages.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.models.implementations.EscaleModel;
import voyages.models.implementations.ProductModel;

/**
 * Servlet implementation class Ajout d'un voyage
 */
@WebServlet(
    name = "Ajout",
    urlPatterns = {"/admin/ajout"})
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

        final SimpleDateFormat escaleDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        HashMap<String, String> errors = new HashMap<>();

        try {
            new_voyage = new ProductModel(getConnexion());

            new_voyage.Name = request.getParameter("Nom");
            new_voyage.Price = Double.parseDouble(request.getParameter("Price"));
            new_voyage.Image = "";
            new_voyage.IsVedette = 0;
            new_voyage.Description = request.getParameter("Description");
            try {
                new_voyage.DateDebut = escaleDateFormatter.parse(request.getParameter("Datedebut"));
            } catch(java.text.ParseException e) {
                throw new ServletException(e);
                // errors.put("DateDebut", e.getMessage());
            }
            try {
                new_voyage.DateFin = escaleDateFormatter.parse(request.getParameter("Datefin"));
            } catch(java.text.ParseException e) {
                throw new ServletException(e);
                // errors.put("DateFin", e.getMessage());
            }

            EscaleModel premiereEscale = null;
            int nbEscales = Integer.parseInt(request.getParameter("nbescales"));
            // For every escale 
            for(int i = 0 ; i < nbEscales ; i++) {
                new_escale = new EscaleModel(getConnexion());
                // get CityId avec le nom de la city

                new_escale.NomActivite = request.getParameter("NomActivite"
                    + i);
                new_escale.DescriptionActivite = request.getParameter("DescriptionActivite"
                    + i);

                if(i == 0) {
                    premiereEscale = new_escale;
                    new_escale.NomActivite = "";
                    new_escale.DescriptionActivite = "";
                    new_escale.IsDepart = true;
                }

                try {
                    new_escale.DateEscale = escaleDateFormatter.parse(request.getParameter("DateEscale"
                        + i));
                } catch(java.text.ParseException e) {
                    errors.put("DateDebut",
                        e.getMessage());
                }

                if(!errors.isEmpty()) {
                    request.setAttribute("errors",
                        errors);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }

                if(new_voyage.ProductId == 0) {
                    new_voyage.create();
                }

                new_escale.ProductId = new_voyage.ProductId;
                new_escale.create();
            }
            if(premiereEscale != null) {
                // On crée la derniere escale pour revenir a la ville d'origine
                EscaleModel derniereEscale = new EscaleModel(getConnexion());
                derniereEscale.copy(premiereEscale);
                derniereEscale.DescriptionActivite = "";
                derniereEscale.NomActivite = "";
                derniereEscale.EscaleId = 0;
                derniereEscale.IsArrivee = true;
                derniereEscale.DateEscale = new_voyage.DateFin;
                derniereEscale.create();
            }
        } catch(DAOException e) {
            request.setAttribute("error",
                e);
        } catch(ConnexionException e) {
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
        response.setContentType(CONTENT_TYPE);
        /*
        List<CityModel> cities = null;
        try {
        	CityModel city_model = new CityModel(getConnexion());
        	cities = city_model.getAll();
        } catch (Exception e) {
        	e.printStackTrace();
        }

        request.setAttribute("cities", cities); */

        request.getRequestDispatcher("/ajout.jsp").forward(request,
            response);
    }
}
