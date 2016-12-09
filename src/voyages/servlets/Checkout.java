
package voyages.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.beans.Caddy;
import voyages.beans.CaddyItem;
import voyages.braintree.Gateway;
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

        Result<Transaction> result = Gateway.DoTransaction(request.getParameter("payment_method_nonce"),
            (BigDecimal) request.getSession(true).getAttribute("caddyPrice"));

        if(result.isSuccess()) {
            Transaction transaction = result.getTarget();

            Caddy caddy = null;
            if(request.getSession().getAttribute("caddy") instanceof Caddy) {
                caddy = (Caddy) request.getSession().getAttribute("caddy");
            }

            User authUser = User.getAuthenticatedUser(request);
            if(authUser == null) {
                request.getRequestDispatcher("login").forward(request,
                    response);
                return;
            }

            List<CaddyItem> items = caddy.getItems();
            OrderModel orderModel = null;
            try {
                orderModel = new OrderModel(getConnexion());
            } catch(ConnexionException e) {
                throw new ServletException(e);
            }

            orderModel.CustomerId = authUser.id;
            orderModel.setPaymentId(transaction.getId());

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
                orderItem.ProductId = productModel.ProductId.longValue();
                orderItem.Quantity = quantity;
                orderItem.OrderId = orderModel.OrderId;
                orderItem.UnitPrice = productModel.Price;

                try {
                    orderItem.create();
                } catch(DAOException e) {
                    throw new ServletException(e);
                }
            }

            request.getRequestDispatcher("orders").forward(request,
                response);

        } else {
            response.getWriter().write(result.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE);

        Caddy caddy = null;
        if(request.getSession().getAttribute("caddy") instanceof Caddy) {
            caddy = (Caddy) request.getSession().getAttribute("caddy");
        }

        User authUser = User.getAuthenticatedUser(request);
        if(authUser == null) {
            request.getRequestDispatcher("login").forward(request,
                response);
            return;
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

        int price = 0;
        for(int i = 0 ; i < items.size() ; i++) {
            ProductModel productModel = items.get(i).getProduct();
            price += productModel.Price;
        }

        request.getSession(true).setAttribute("caddyPrice",
            new BigDecimal(price));

        request.getRequestDispatcher("checkout.jsp").forward(request,
            response);
    }

}
