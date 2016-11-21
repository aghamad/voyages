
package voyages;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class User implements IModel {
    public long id;

    public String Email;

    public String Password;

    public String FirstName;

    public String LastName;

    public String Address;

    public boolean IsAdmin = false;

    private static String columns = "id, Email, Password, FirstName, LastName, Address, IsAdmin";

    private static String non_id_columns = "Email, Password, FirstName, LastName, Address, IsAdmin";

    private static String GET_USERS = "SELECT "
        + columns
        + " FROM Users";

    private static String GET_BY_ID = "SELECT "
        + columns
        + " FROM Users WHERE id = ?";

    private static String GET_BY_EMAIL_PASSWORD = "SELECT * FROM Users WHERE Email = ? AND Password = ?";

    private static String CREATE = "INSERT INTO Users ("
        + non_id_columns
        + ") VALUES(?, ?, ?, ?, ?) ";

    private static String UPDATE = "UPDATE Users SET Email = ?, Password = ?, FirstName = ?, LastName = ?, Address = ?, IsAdmin = ? WHERE id = ?";

    private Connexion connexion = null;

    @Override
    public void setConnexion(Connexion conn) {
        this.connexion = conn;
    }

    @Override
    public Connexion getConnexion() {
        return this.connexion;
    }

    public User(Connexion connexion) {
        setConnexion(connexion);
    }

    public static User getAuthenticatedUser(HttpServletRequest request) {
        if(request.getSession(true).getAttribute("authUser") != null
            && request.getSession(true).getAttribute("authUser") instanceof User) {
            return (User) request.getSession(true).getAttribute("authUser");
        }
        return null;
    }

    public User(Connexion connexion,
        String email,
        String password) {
        this.connexion = connexion;
        this.Email = email;
        this.Password = password;
    }

    private User extract(ResultSet rset) throws DAOException {
        try {
            User extracted = new User(getConnexion());
            extracted.setConnexion(getConnexion());
            extracted.id = rset.getLong("id");
            extracted.FirstName = rset.getString("FirstName");
            extracted.LastName = rset.getString("LastName");
            extracted.Email = rset.getString("Email");
            extracted.Address = rset.getString("Address");
            extracted.Password = rset.getString("Password");
            extracted.IsAdmin = rset.getBoolean("IsAdmin");

            return extracted;
        } catch(SQLException sqlExcept) {
            throw new DAOException(sqlExcept);
        }
    }

    @Override
    public void delete(IModel user) throws DAOException {
        throw new DAOException("Delete function not implemented yet.");
        //int id = the_user.id;
    }

    @Override
    public IModel read(IModel model) throws DAOException {
        try {
            User user = (User) model;
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(User.GET_BY_ID);
            getByIdStatement.setLong(1,
                user.id);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {
                if(rset.next()) {
                    return extract(rset);
                }
                throw new DAOException("User with id "
                    + user.id
                    + " does not exist.");
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    public User auth(String email,
        String password) throws DAOException,
        Exception {
        try {

            PreparedStatement authStatement = Connexion.getConnexion().getConnection().prepareStatement(User.GET_BY_EMAIL_PASSWORD);

            authStatement.setString(1,
                email);
            authStatement.setString(2,
                password);

            try(
                ResultSet rset = authStatement.executeQuery()) {

                if(rset.next()) {
                    return extract(rset);
                }
                return null;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<User> getAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement getAllStatement;

            getAllStatement = getConnexion().getConnection().prepareStatement(User.GET_USERS);

            try(
                ResultSet result = getAllStatement.executeQuery()) {

                while(result.next()) {
                    users.add(extract(result));
                }

                return users;
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    @Override
    public int create(IModel user) throws DAOException {
        try {
            User the_user = (User) user;
            try(
                PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(User.CREATE)) {
                createStatement.setString(1,
                    the_user.Email);
                createStatement.setString(2,
                    the_user.Password);
                createStatement.setString(3,
                    the_user.FirstName);
                createStatement.setString(4,
                    the_user.LastName);
                createStatement.setString(5,
                    the_user.Address);
                createStatement.setBoolean(6,
                    the_user.IsAdmin);

                int affectedRows = createStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try(
                    ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        this.id = generatedKeys.getLong(1);
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
    public int update(IModel user) throws DAOException {
        try {
            User the_user = (User) user;
            try(
                PreparedStatement updateStatement = getConnexion().getConnection().prepareStatement(User.UPDATE)) {
                updateStatement.setString(1,
                    the_user.Email);
                updateStatement.setString(2,
                    the_user.Password);
                updateStatement.setString(3,
                    the_user.FirstName);
                updateStatement.setString(4,
                    the_user.LastName);
                updateStatement.setString(5,
                    the_user.Address);
                updateStatement.setBoolean(6,
                    the_user.IsAdmin);
                updateStatement.setLong(7,
                    the_user.id);

                return updateStatement.executeUpdate();
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void create() throws DAOException {
        this.create(this);
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
        User the_model = (User) model;
        this.id = the_model.id;
        this.FirstName = the_model.FirstName;
        this.LastName = the_model.LastName;
        this.Address = the_model.Address;
        this.IsAdmin = the_model.IsAdmin;
        this.Password = the_model.Password;
        this.Email = the_model.Email;
    }
}