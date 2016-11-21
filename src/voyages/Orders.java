
package voyages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Orders
 */
@WebServlet(
    name = "Orders",
    urlPatterns = {"/orders"})
public class Orders extends HttpServlet {
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

        String action = request.getParameter("action") == null ? "" : (String) request.getParameter("action");

        if(action == "reorder") {
            Long.parseLong(request.getParameter("orderId"));
            OrderModel orderModel;
            try {
                orderModel = new OrderModel(Connexion.getConnexion());
            } catch(
                ClassNotFoundException
                | SQLException e) {
                throw new ServletException(e);
            } catch(Exception e) {
                throw new ServletException(e);
            }
            //orderModel.OrderId = orderId;
            try {
                //orderModel.read();

                //OrderModel new_order = orderModel.create(orderModel);

                OrderDetailsModel orderDetailsModel = new OrderDetailsModel(orderModel);
                List<OrderDetailsModel> items = orderDetailsModel.findByOrder(orderModel);
                orderModel.create(orderModel);
                for(int i = 0 ; i < items.size() ; i++) {
                    OrderDetailsModel item = items.get(i);
                    item.OrderId = orderModel.OrderId;
                    item.create();
                }
            } catch(DAOException e) {
                throw new ServletException(e);
            }

        }

        request.getRequestDispatcher("/orders.jsp").forward(request,
            response);
    }

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE);

        request.getRequestDispatcher("/orders.jsp").forward(request,
            response);
    }

}
