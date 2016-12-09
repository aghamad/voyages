package voyages.servlets;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.models.implementations.ProductModel;

/**
 * Servlet implementation class Ajout d'un voyage
 */
@WebServlet(
    name = "VoyageList",
    urlPatterns = {"/admin/voyages"})
public class VoyageList extends AdminServlet implements Servlet {
    private static final long serialVersionUID = 1L;

    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//super.doGet(request, response);
		
		ProductModel model = null;
		try {
			model = new ProductModel(getConnexion());
		} catch (ConnexionException e) {
			throw new ServletException(e);
		}
		
		try {
			request.setAttribute("products", model.getAll());
		} catch (DAOException e) {
			throw new ServletException(e);
		}
		
        request.getRequestDispatcher("/admin_voyages.jsp").forward(request,
            response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductModel productModel = null;
		long id = 0;
		
		String action = request.getParameter("action");
		
		if(action.equals("sponsor") || action.equals("unsponsor")) {
			
	    	try {
	    		id = Long.parseLong(request.getParameter("productCode"));
	    	} catch(NumberFormatException e) {
	    		throw new ServletException(e);
	    	}
	    	

	        if(id == 0) {
	            throw new ServletException("no id given");
	        }
	        try {
	        	productModel = new ProductModel(getConnexion());
	
	        	productModel.ProductId = id;
	        	productModel.read();
	        } catch(
	            ConnexionException
	            | DAOException e) {
	            throw new ServletException(e);
	        }
	        // String action = request.getParameter("action");
	 
			//if(action.equals("sponsor")) {
	            productModel.IsVedette = (action.equals("sponsor") ? 1 : 0);
	            /*
	            if(productModel == productModel)
	            throw new ServletException("ISVEDETTE  " + productModel.IsVedette);
	            */
	            
	            /*try {
	                productModel.bulk_unvedette();
	            } catch(DAOException daoException) {
	                throw new ServletException(daoException);
	            }*/
	            try {
	                productModel.update();
	            } catch(DAOException e) {
	                throw new ServletException(e);
	            }
	   //}
		}
	        
		doGet(request, response);
	}

}
