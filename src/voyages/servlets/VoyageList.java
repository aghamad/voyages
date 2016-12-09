package voyages.servlets;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.models.implementations.ProductModel;

/**
 * Servlet implementation class VoyageList
 */

public class VoyageList extends AdminServlet implements Servlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public VoyageList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductModel model = null;
		super.doGet(request, response);
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
		super.doPost(request, response);
		ProductModel productModel = null;
		try {
			productModel = new ProductModel(getConnexion());
		} catch (ConnexionException e) {
			throw new ServletException(e);
		}
        String action = request.getParameter("action");
 
		if(action.equals("sponsor")) {
	            productModel.IsVedette = 1;
	            try {
	                productModel.bulk_unvedette();
	            } catch(DAOException daoException) {
	                throw new ServletException(daoException);
	            }
	            try {
	                productModel.update();
	            } catch(DAOException e) {
	                throw new ServletException(e);
	            }
	        }
	        
		doGet(request, response);
	}

}
