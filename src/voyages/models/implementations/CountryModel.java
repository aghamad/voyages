
package voyages.models.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import exceptions.DAOException;
import voyages.db.Connexion;
import voyages.models.interfaces.IModel;

public class CountryModel implements IModel {

    private Connexion connexion = null;

    public String Name;

    public long CountryId;

    private static String columns = "Name, CountryId";

    private static String insert_columns = "Name";

    private static String TABLE = "Country";

    private static String GET_ALL = "SELECT "
        + columns
        + " FROM "
        + TABLE;

    private static String GET_BY_ID = "SELECT "
        + columns
        + " FROM "
        + TABLE
        + " WHERE CountryId = ?";

    private static String CREATE = "INSERT INTO "
        + TABLE
        + "("
        + insert_columns
        + ") VALUES(?, ?) ";

    private static String UPDATE = "UPDATE "
        + TABLE
        + " SET Name = ? WHERE CountryId = ?";

    private static String DELETE = "DELETE FROM "
        + TABLE
        + " WHERE CountryId = ?";

    @Override
    public void setConnexion(Connexion conn) {
        this.connexion = conn;
    }

    @Override
    public Connexion getConnexion() {
        return this.connexion;
    }

    public CountryModel(Connexion connexion) {
        setConnexion(connexion);
    }

    public CountryModel(IModel model) {
        setConnexion(model.getConnexion());
    }

    @Override
    public IModel read(IModel model) throws DAOException {
        try {
            CountryModel the_model = (CountryModel) model;

            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(CountryModel.GET_BY_ID);
            getByIdStatement.setLong(1,
                the_model.CountryId);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {
                if(rset.next()) {
                    return extract(rset);
                }
                throw new DAOException("Country with id "
                    + the_model.CountryId
                    + " does not exist.");
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int update(IModel model) throws DAOException {
        try {
            CountryModel city = (CountryModel) model;

            try(
                PreparedStatement updateStatement = getConnexion().getConnection().prepareStatement(UPDATE)) {
                updateStatement.setString(1,
                    city.Name);
                updateStatement.setLong(2,
                    city.CountryId);

                int affectedRows = updateStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Updating Country failed, no rows affected.");
                }

                return affectedRows;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int create(IModel model) throws DAOException {
        try {
            CountryModel city = (CountryModel) model;

            try(
                PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(CREATE)) {
                createStatement.setString(1,
                    city.Name);

                int affectedRows = createStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Creating country failed, no rows affected.");
                }

                try(
                    ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        city.CountryId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating country failed, no ID obtained.");
                    }
                }
                return affectedRows;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    private CountryModel extract(ResultSet rset) throws DAOException {
        try {
            CountryModel city = new CountryModel(this);
            city.CountryId = rset.getLong("CountryId");
            city.Name = rset.getString("Name");

            return city;
        } catch(SQLException sqlExcept) {
            throw new DAOException(sqlExcept);
        }
    }

    private List<CountryModel> extractAll(ResultSet rset) throws DAOException {
        List<CountryModel> cities = new ArrayList<>();
        try {
            while(rset.next()) {
                cities.add(extract(rset));
            }
            return cities;
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    public List<CountryModel> getAll() throws DAOException {
        try {
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(CountryModel.GET_ALL);

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
        try {
            CountryModel country = (CountryModel) model;

            try(
                PreparedStatement deleteStatement = getConnexion().getConnection().prepareStatement(DELETE)) {

                deleteStatement.setLong(1,
                    country.CountryId);

                int affectedRows = deleteStatement.executeUpdate();

                return affectedRows;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
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
        CountryModel the_model = (CountryModel) model;
        this.CountryId = the_model.CountryId;
        this.Name = the_model.Name;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public long getCountryId() {
        return this.CountryId;
    }

    public void setCountryId(long countryId) {
        this.CountryId = countryId;
    }
}
