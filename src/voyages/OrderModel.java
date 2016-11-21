
package voyages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel implements IModel {

    private Connexion connexion = null;

    public long OrderId;

    public long CustomerId;

    public String OrderDate;

    private static String columns = "OrderId, CustomerId, OrderDate";

    private static String insert_columns = "CustomerId";

    private static String GET_ALL = "SELECT "
        + columns
        + " FROM Orders";

    private static String GET_BY_ID = "SELECT "
        + columns
        + " FROM Orders WHERE OrderId = ?";

    private static String FIND_BY_CUSTOMER = "SELECT "
        + columns
        + " FROM Orders WHERE CustomerId = ? ORDER BY OrderId DESC";

    private static String CREATE = "INSERT INTO Orders ("
        + insert_columns
        + ") VALUES(?) ";

    @Override
    public void setConnexion(Connexion conn) {
        this.connexion = conn;
    }

    @Override
    public Connexion getConnexion() {
        return this.connexion;
    }

    public OrderModel(Connexion connexion) {
        setConnexion(connexion);
    }

    public OrderModel(IModel model) {
        setConnexion(model.getConnexion());
    }

    @Override
    public IModel read(IModel model) throws DAOException {
        try {
            OrderModel the_model = (OrderModel) model;
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(OrderModel.GET_BY_ID);
            getByIdStatement.setLong(1,
                the_model.OrderId);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {
                if(rset.next()) {
                    return extract(rset);
                }
                throw new DAOException("Order with id "
                    + the_model.OrderId
                    + " does not exist.");
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int create(IModel model) throws DAOException {
        try {
            OrderModel the_order = (OrderModel) model;

            try(
                PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(CREATE)) {
                createStatement.setLong(1,
                    the_order.CustomerId);

                int affectedRows = createStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try(
                    ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        this.OrderId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                return affectedRows;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int update(IModel model) throws DAOException {

        throw new DAOException("To update an Order is not allowed.");
        //return 0;
    }

    @Override
    public void create() throws DAOException {
        this.create(this);
        this.read();

    }

    @Override
    public void delete() throws DAOException {
        this.delete(this);
    }

    @Override
    public void update() throws DAOException {
        this.update(this);
        this.read();
    }

    public List<OrderModel> findByCustomer(User user) throws DAOException {
        try {
            PreparedStatement getByCustomerStatement = getConnexion().getConnection().prepareStatement(OrderModel.FIND_BY_CUSTOMER);
            getByCustomerStatement.setLong(1,
                user.id);

            try(
                ResultSet rset = getByCustomerStatement.executeQuery()) {

                return extractAll(rset);
            }

        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<OrderModel> getAll() throws DAOException {
        try {
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(OrderModel.GET_ALL);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {

                return extractAll(rset);
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(IModel model) throws DAOException {
        throw new DAOException("Not implemented");

    }

    private OrderModel extract(ResultSet rset) throws DAOException {
        try {
            OrderModel order = new OrderModel(this);
            order.OrderId = rset.getInt("OrderId");
            order.CustomerId = rset.getInt("CustomerId");
            //DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
            order.OrderDate = rset.getString("OrderDate");

            return order;
        } catch(SQLException sqlExcept) {
            throw new DAOException(sqlExcept);
        }
    }

    private List<OrderModel> extractAll(ResultSet rset) throws DAOException {
        List<OrderModel> orders = new ArrayList<>();
        try {
            while(rset.next()) {
                orders.add(extract(rset));
            }
            return orders;
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    @Override
    public void read() throws DAOException {
        this.copy(this.read(this));
    }

    private void copy(IModel model) {
        OrderModel the_model = (OrderModel) model;
        this.CustomerId = the_model.CustomerId;
        this.OrderId = the_model.OrderId;
        this.OrderDate = the_model.OrderDate;
    }
}