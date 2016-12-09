
package voyages.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.beans.Caddy;
import voyages.db.Connexion;
import voyages.models.implementations.CityModel;
import voyages.models.implementations.ProductModel;
import voyages.models.implementations.User;

@WebServlet(
    name = "Products",
    urlPatterns = {"/products"})
public class Products extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void writeResponse(HttpServletResponse response,
        String body) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>ECommerce</title></head>");
        out.println("<body>");
        out.println(body);
        //out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }

    @Override
    public void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        if(request.getParameter("productCode") == "") {
            throw new ServletException("fuck you");
        }
        Long code = new Long(Long.parseLong(request.getParameter("productCode")));

        /*if(code == null) {
            throw new ServletException("fuck you "
                + request.getParameter("productCode"));
        }*/

        // Add to cart the product with the corresponding code

        String action = request.getParameter("action");

        ProductModel productModel;
        try {
            productModel = new ProductModel(getConnexion());
        } catch(ConnexionException e) {
            throw new ServletException(e);
        }
        productModel.setProductId(code);

        try {
            productModel.read();

        } catch(DAOException e) {
            throw new ServletException(e);
        }
        if(action.equals("addtocart")) {
        	/*
            List<Long> carts = null;
            if(request.getSession().getAttribute("cart") instanceof List<?>) {
                carts = (List<Long>) request.getSession().getAttribute("cart");
            }

            if(carts == null) {
                carts = new ArrayList<>();
            }

            carts.add(code);

            request.getSession().setAttribute("cart",
                carts);*/

            ProductModel added;
            added = productModel;

            Caddy caddy = null;
            if(request.getSession().getAttribute("caddy") instanceof Caddy) {
                caddy = (Caddy) request.getSession().getAttribute("caddy");
            }
            if(caddy == null) {
                caddy = new Caddy();
                request.getSession().setAttribute("caddy",
                    caddy);
            }

            caddy.add(added);

            
            request.setAttribute("addedProduct",
                added);
        } else if(action.equals("sponsor")) {
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
        
        try {
			this.transmitInformations(request);
		} catch (ConnexionException e) {
			throw new ServletException(e);
		} catch (DAOException e) {
			throw new ServletException(e);
		}
        request.getRequestDispatcher("products.jsp").forward(request,
            response);

    }

    private void transmitInformations(HttpServletRequest request) throws ConnexionException, DAOException {
    	ProductModel productModel;
		
    	productModel = new ProductModel(Connexion.getOrSetUpConnexion(getServletContext()));

    	User authUser = User.getAuthenticatedUser(request);
    	CityModel userCity = null;
    	if(authUser != null)
    		userCity = User.getAuthenticatedUser(request).getCity();

        List<ProductModel> initial_products = Collections.emptyList();
        String type = request.getParameter("type");
            
        if(userCity == null) {
            type = "all";
            
        }
        if(type == null) {
        	type = "relevant";
        }
        if(type.equals("all"))
        	initial_products = productModel.getAll();
        else if(type.equals("relevant"))
        	initial_products = productModel.findByDepartureCity(userCity, new Date());

        List<ProductModel> vedettes = productModel.findVedettes();
        
        /*ProductModel added = null;
        if(request.getAttribute("addedProduct") instanceof ProductModel)
            added = (ProductModel) request.getAttribute("addedProduct");*/
        
        List<ProductModel> products = initial_products;
            
        request.setAttribute("vedettes", vedettes);
        request.setAttribute("type", type);
        request.setAttribute("products", products);
        request.setAttribute("userCity", userCity);
    }
    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE);

        if(User.getAuthenticatedUser(request) == null) {
            response.sendRedirect("login");
        } else {
			try {
				this.transmitInformations(request);
	            
	            request.getRequestDispatcher("products.jsp").forward(request,
	                response);
			} catch (ConnexionException e) {
				throw new ServletException(e);
			} catch (DAOException e) {
				throw new ServletException(e);
			}
        }
    }
}
