
package voyages.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.beans.Caddy;
import voyages.beans.CaddyItem;
import voyages.models.implementations.OrderDetailsModel;
import voyages.models.implementations.OrderModel;
import voyages.models.implementations.ProductModel;
import voyages.models.implementations.User;

/**
 * Servlet implementation class Checkout
 */
@WebServlet(
    name = "Checkout",
    urlPatterns = {"/checkout"})
public class Checkout extends BaseServlet {
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

        Caddy caddy = null;
        if(request.getSession().getAttribute("caddy") instanceof Caddy) {
            caddy = (Caddy) request.getSession().getAttribute("caddy");
        }

        User authUser = User.getAuthenticatedUser(request);
        if(authUser == null) {
            response.sendRedirect("login");
        } else {
            //request.getRequestDispatcher("/orders").forward(request, response);
        }

        List<CaddyItem> items = caddy.getItems();
        OrderModel orderModel = null;
        try {
            orderModel = new OrderModel(getConnexion());
        } catch(ConnexionException e) {
            throw new ServletException(e);
        }

        orderModel.CustomerId = authUser.id;
        try {
            orderModel.create();
        } catch(DAOException e) {
            throw new ServletException(e);
        }

        for(int i = 0 ; i < items.size() ; i++) {
            CaddyItem item = items.get(i);

            ProductModel productModel = item.getProduct();
            int quantity = item.getQuantity();

            OrderDetailsModel orderItem = new OrderDetailsModel(orderModel);
            orderItem.ProductId = productModel.ProductId;
            orderItem.Quantity = quantity;
            orderItem.OrderId = orderModel.OrderId;
            orderItem.UnitPrice = productModel.Price;

            try {
                orderItem.create();
            } catch(DAOException e) {
                throw new ServletException(e);
            }
        }

        request.getSession(true).setAttribute("caddy",
            new Caddy());

        response.sendRedirect("orders");
    }

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Checkout</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }

}
