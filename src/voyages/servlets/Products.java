
package voyages.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.beans.Caddy;
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

            List<Long> carts = null;
            if(request.getSession().getAttribute("cart") instanceof List<?>) {
                carts = (List<Long>) request.getSession().getAttribute("cart");
            }

            if(carts == null) {
                carts = new ArrayList<>();
            }

            carts.add(code);

            request.getSession().setAttribute("cart",
                carts);

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

        request.getRequestDispatcher("/products.jsp").forward(request,
            response);

    }

    @Override
    public void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException,
        IOException {
        response.setContentType(CONTENT_TYPE);

        if(User.getAuthenticatedUser(request) == null) {
            response.sendRedirect("/login");

        } else {
            request.setAttribute("authUser",
                User.getAuthenticatedUser(request));
            request.getRequestDispatcher("/products.jsp").forward(request,
                response);
        }

        /*
           try {
               ArrayList<Product> products = Product.getProducts();
               String body = "<table><tr><th>Code</th><td>Nom</td><td>Description</td><td>Image</td></tr>";

               for(int i = 0; i < products.size(); i++) {
                   Product product = products.get(i);
                   body += "<tr>";

                   body += "<td>";
                   body += product.getCode();
                   body += "</td>";

                   body += "<td>";
                   body += product.getNom();
                   body += "</td>";

                   body += "<td>";
                   body += product.getDescription();
                   body += "</td>";

                   body += "<td>";
                   String picturefile = getServletContext().getRealPath("/" + product.getImage());
                   String imgPath = request.getContextPath() + "/"  + product.getImage();
                   System.out.println(picturefile);
                   System.out.println(imgPath);
                   body += "<img src='" + imgPath+"' />";

                   body += "</td>";

                   body += "</tr>";
               }

               this.writeResponse(response, body);

           } catch (ProductException e) {
               throw new ServletException(e);
        }*/
    }

}
