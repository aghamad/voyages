
package voyages.filters;

import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import exceptions.ConnexionException;
import exceptions.DAOException;
import voyages.db.Connexion;
import voyages.models.implementations.CityModel;
import voyages.models.implementations.CountryModel;
import voyages.models.implementations.User;

/**
 * Servlet Filter implementation class UsefulDataFilter
 */
@WebFilter("/UsefulDataFilter")
public class UsefulDataFilter implements Filter {

    /**
     * Default constructor.
     */
    public UsefulDataFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request,
        ServletResponse response,
        FilterChain chain) throws IOException,
        ServletException {
        // TODO Auto-generated method stub
        // place your code here

        Connexion connexion = null;
        try {
            connexion = Connexion.getOrSetUpConnexion(request.getServletContext());
        } catch(ConnexionException connectionException) {
            throw new ServletException(connectionException);
        }

        request.setAttribute("authUser",
            User.getAuthenticatedUser((HttpServletRequest) request));

        if(cities == null) {
            CityModel cityModel = new CityModel(connexion);

            try {
                cities = cityModel.getAll();
            } catch(DAOException e) {
                throw new ServletException(e);
            }

        }
        if(countries == null) {
            CountryModel countryModel = new CountryModel(connexion);
            try {
                countries = countryModel.getAll();
            } catch(DAOException e) {
                throw new ServletException(e);
            }
        }

        request.setAttribute("countries",
            countries);
        request.setAttribute("cities",
            cities);

        // pass the request along the filter chain
        chain.doFilter(request,
            response);
    }

    private static List<CityModel> cities = null;

    private static List<CountryModel> countries = null;

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
