
package voyages.models.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import exceptions.DAOException;
import voyages.db.Connexion;
import voyages.models.interfaces.IModel;
import voyages.models.utils.DateParser;

public class OrderModel implements IModel {

    private Connexion connexion = null;

    public long OrderId;

    public long CustomerId;

    public Date OrderDate;

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
                    throw new SQLException("Creating order failed, no rows affected.");
                }

                try(
                    ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        this.OrderId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
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
    public int delete(IModel model) throws DAOException {
        throw new DAOException("Not implemented");

    }

    private OrderModel extract(ResultSet rset) throws DAOException {
        try {
            OrderModel order = new OrderModel(this);
            order.OrderId = rset.getInt("OrderId");
            order.CustomerId = rset.getInt("CustomerId");

            try {
                order.OrderDate = DateParser.parse(rset.getString("OrderDate"));
            } catch(ParseException e) {
                order.OrderDate = null;
                // On laisse OrderDate a null
            }

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

    public List<OrderDetailsModel> getItems() throws DAOException {
        OrderDetailsModel model = new OrderDetailsModel(this);
        return model.findByOrder(this);

    }

    public long getOrderId() {
        return this.OrderId;
    }

    public void setOrderId(long orderId) {
        this.OrderId = orderId;
    }

    public long getCustomerId() {
        return this.CustomerId;
    }

    public void setCustomerId(long customerId) {
        this.CustomerId = customerId;
    }

    public Date getOrderDate() {
        return this.OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.OrderDate = orderDate;
    }
}