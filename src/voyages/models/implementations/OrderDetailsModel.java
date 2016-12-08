
package voyages.models.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import exceptions.DAOException;
import voyages.db.Connexion;
import voyages.models.interfaces.IModel;

public class OrderDetailsModel implements IModel {

    private Connexion connexion = null;

    public long OrderDetailId;

    public ProductModel product;

    public long OrderId;

    public long ProductId;

    public double UnitPrice;

    public int Quantity;

    private static String columns = "OrderDetailId, OrderId, ProductId, UnitPrice, Quantity";

    private static String insert_columns = "OrderId, ProductId, UnitPrice, Quantity";

    private static String GET_BY_ID = "SELECT "
        + columns
        + " FROM OrderDetails WHERE OrderDetailId = ?";

    private static String FIND_BY_ORDER = "SELECT "
        + columns
        + " FROM OrderDetails WHERE OrderId = ?";

    private static String CREATE = "INSERT INTO OrderDetails ("
        + insert_columns
        + ") VALUES(?, ?, ?, ?) ";

    private static String UPDATE = "UPDATE OrderDetails SET OrderId = ?, ProductId =?, UnitPrice = ?, Quantity = ? WHERE OrderDetailId = ?";

    @Override
    public void setConnexion(Connexion conn) {
        this.connexion = conn;
    }

    @Override
    public Connexion getConnexion() {
        return this.connexion;
    }

    public OrderDetailsModel(Connexion connexion) {
        setConnexion(connexion);
    }

    public OrderDetailsModel(IModel model) {
        setConnexion(model.getConnexion());
    }

    public ProductModel getProduct() throws DAOException {
        if(this.product == null) {
            ProductModel model = new ProductModel(this);
            model.ProductId = new Long(this.ProductId);
            model.read();
            this.product = model;
        }
        return this.product;
    }

    public void setProduct(ProductModel p) {
        this.product = p;
    }

    @Override
    public IModel read(IModel model) throws DAOException {
        try {
            OrderDetailsModel the_model = (OrderDetailsModel) model;
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(OrderDetailsModel.GET_BY_ID);
            getByIdStatement.setLong(1,
                the_model.OrderDetailId);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {
                if(rset.next()) {
                    return extract(rset);
                }

                throw new DAOException("OrderDetails with id "
                    + the_model.OrderDetailId
                    + " does not exist.");
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int create(IModel model) throws DAOException {
        try {
            OrderDetailsModel the_order = (OrderDetailsModel) model;

            try(
                PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(CREATE)) {
                createStatement.setLong(1,
                    the_order.OrderId);
                createStatement.setLong(2,
                    the_order.ProductId);
                createStatement.setDouble(3,
                    the_order.UnitPrice);
                createStatement.setInt(4,
                    the_order.Quantity);

                int affectedRows = createStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Creating order item failed, no rows affected.");
                }

                try(
                    ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        this.OrderDetailId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating order item failed, no ID obtained.");
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
        try {
            OrderDetailsModel the_order = (OrderDetailsModel) model;

            try(
                PreparedStatement updateStatement = getConnexion().getConnection().prepareStatement(UPDATE)) {
                updateStatement.setLong(1,
                    the_order.OrderId);
                updateStatement.setLong(2,
                    the_order.ProductId);
                updateStatement.setDouble(3,
                    the_order.UnitPrice);
                updateStatement.setInt(4,
                    the_order.Quantity);
                updateStatement.setLong(5,
                    the_order.OrderDetailId);

                int affectedRows = updateStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Updating Order Details failed, no rows affected.");
                }

                return affectedRows;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int delete(IModel model) throws DAOException {
        throw new DAOException("Not implemented");
    }

    private OrderDetailsModel extract(ResultSet rset) throws DAOException {
        try {
            OrderDetailsModel orderDetails = new OrderDetailsModel(getConnexion());

            orderDetails.OrderDetailId = rset.getInt("OrderDetailId");
            orderDetails.OrderId = rset.getInt("OrderId");
            orderDetails.ProductId = rset.getInt("ProductId");
            orderDetails.UnitPrice = rset.getDouble("UnitPrice");
            orderDetails.Quantity = rset.getInt("Quantity");

            return orderDetails;
        } catch(SQLException sqlExcept) {
            throw new DAOException(sqlExcept);
        }
    }

    public List<OrderDetailsModel> findByOrder(OrderModel order) throws DAOException {
        try {
            try(
                PreparedStatement getByCustomerStatement = getConnexion().getConnection().prepareStatement(OrderDetailsModel.FIND_BY_ORDER)) {
                getByCustomerStatement.setLong(1,
                    order.OrderId);

                try(
                    ResultSet rset = getByCustomerStatement.executeQuery()) {

                    return extractAll(rset);
                }
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }

    }

    private List<OrderDetailsModel> extractAll(ResultSet rset) throws DAOException {
        List<OrderDetailsModel> list = new ArrayList<>();
        try {
            while(rset.next()) {
                list.add(extract(rset));
            }
            return list;
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
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

    @Override
    public void read() throws DAOException {
        this.copy(this.read(this));
    }

    private void copy(IModel model) {
        OrderDetailsModel the_model = (OrderDetailsModel) model;
        this.ProductId = the_model.ProductId;
        this.OrderDetailId = the_model.OrderDetailId;
        this.OrderId = the_model.OrderId;
        this.Quantity = the_model.Quantity;
        this.UnitPrice = the_model.UnitPrice;
    }

    public long getOrderDetailId() {
        return this.OrderDetailId;
    }

    public void setOrderDetailId(long orderDetailId) {
        this.OrderDetailId = orderDetailId;
    }

    public long getOrderId() {
        return this.OrderId;
    }

    public void setOrderId(long orderId) {
        this.OrderId = orderId;
    }

    public long getProductId() {
        return this.ProductId;
    }

    public void setProductId(long productId) {
        this.ProductId = productId;
    }

    public double getUnitPrice() {
        return this.UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.UnitPrice = unitPrice;
    }

    public int getQuantity() {
        return this.Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }
}