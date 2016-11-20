package voyages;


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Cart
 */
@WebServlet(name = "Cart", urlPatterns = {"/cart"})
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Caddy caddy = null;
        if(request.getSession().getAttribute("caddy") instanceof Caddy)
            caddy = (Caddy) request.getSession().getAttribute("caddy");
        else {
            caddy = new Caddy();
                request.getSession().setAttribute("caddy", caddy);
            }
            
        Integer to_delete = null;
        if(request.getParameter("delete") != null)
            to_delete = Integer.parseInt((String)request.getParameter("delete"));
        
        if(caddy == null)
            caddy = new Caddy();
        for(int i =0; i < caddy.getItems().size(); i++) {
            ProductModel product = caddy.getItems().get(i).getProduct();
            if(request.getParameter("itemQuantity." + product.ProductId) != null) {
                caddy.getItems().get(i).setQuantity(Integer.parseInt((String)request.getParameter("itemQuantity." + product.ProductId)));
            }
            
            if(to_delete != null && product.ProductId == to_delete)
                caddy.getItems().remove(i);
        }
        
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

}
