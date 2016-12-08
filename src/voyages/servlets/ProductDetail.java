
package voyages.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.models.implementations.ProductModel;

/**
 * Servlet implementation class ProductDetail
 */
@WebServlet("/products/detail")
public class ProductDetail extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
    	long id = 0;
    	try {
    		id = Long.parseLong(request.getParameter("id"));
    	} catch(NumberFormatException e) {
    		throw new ServletException(e);
    	}
        if(id == 0) {
            throw new ServletException("no id given");
        }

        ProductModel model;
        try {
            model = new ProductModel(getConnexion());

            model.ProductId = id;
            model.read();
        } catch(
            ConnexionException
            | DAOException e) {
            throw new ServletException(e);
        }

        /*
        EscaleModel escaleModel = new EscaleModel(model);
        try {
        	request.setAttribute("escales", escaleModel.findByProduct(model) );
        } catch (DAOException e) {
        	throw new ServletException(e);
        } 
        */

        request.setAttribute("product",
            model);

        request.getRequestDispatcher("/productDetail.jsp").forward(request,
            response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        // TODO Auto-generated method stub
        doGet(request,
            response);
    }

}
