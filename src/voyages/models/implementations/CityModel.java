
package voyages.models.implementations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import exceptions.DAOException;
import voyages.db.Connexion;
import voyages.models.interfaces.IModel;

public class CityModel implements IModel {

    private Connexion connexion = null;

    public long CityId;

    public String Name;

    public long CountryId;

    private static String columns = "CityId, Name, CountryId";

    private static String insert_columns = "Name, CountryId";

    private static String TABLE = "City";

    private static String GET_ALL = "SELECT "
        + columns
        + " FROM "
        + TABLE;

    private static String GET_BY_ID = "SELECT "
        + columns
        + " FROM "
        + TABLE
        + " WHERE CityId = ?";

    private static String FIND_BY_COUNTRY = "SELECT "
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
        + " SET Name = ?, CountryId = ? WHERE CityId = ?";

    private static String DELETE = "DELETE FROM "
        + TABLE
        + " WHERE CityId = ?";

    @Override
    public void setConnexion(Connexion conn) {
        this.connexion = conn;
    }

    @Override
    public Connexion getConnexion() {
        return this.connexion;
    }

    public CityModel(Connexion connexion) {
        setConnexion(connexion);
    }

    public CityModel(IModel model) {
        setConnexion(model.getConnexion());
    }

    @Override
    public IModel read(IModel model) throws DAOException {
        try {
            CityModel the_model = (CityModel) model;

            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(CityModel.GET_BY_ID);
            getByIdStatement.setLong(1,
                the_model.CityId);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {
                if(rset.next()) {
                    return extract(rset);
                }
                throw new DAOException("City with id "
                    + the_model.CityId
                    + " does not exist.");
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int update(IModel model) throws DAOException {
        try {
            CityModel city = (CityModel) model;

            try(
                PreparedStatement updateStatement = getConnexion().getConnection().prepareStatement(UPDATE)) {
                updateStatement.setString(1,
                    city.Name);
                updateStatement.setLong(2,
                    city.CountryId);
                updateStatement.setLong(3,
                    city.CityId);

                int affectedRows = updateStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Updating Cityfailed, no rows affected.");
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
            CityModel city = (CityModel) model;

            try(
                PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(CREATE)) {
                createStatement.setString(1,
                    city.Name);
                createStatement.setLong(2,
                    city.CountryId);

                int affectedRows = createStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Creating city failed, no rows affected.");
                }

                try(
                    ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        city.CityId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating city failed, no ID obtained.");
                    }
                }
                return affectedRows;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    private CityModel extract(ResultSet rset) throws DAOException {
        try {
            CityModel city = new CityModel(this);
            city.CityId = rset.getLong("CityId");
            city.CountryId = rset.getLong("CountryId");
            city.Name = rset.getString("Name");

            return city;
        } catch(SQLException sqlExcept) {
            throw new DAOException(sqlExcept);
        }
    }

    private List<CityModel> extractAll(ResultSet rset) throws DAOException {
        List<CityModel> cities = new ArrayList<>();
        try {
            while(rset.next()) {
                cities.add(extract(rset));
            }
            return cities;
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    public List<CityModel> getAll() throws DAOException {
        try {
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(CityModel.GET_ALL);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {

                return extractAll(rset);
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<CityModel> findByCountry(CountryModel country) throws DAOException {
        try {
            try(
                PreparedStatement getByVoyageStatement = getConnexion().getConnection().prepareStatement(CityModel.FIND_BY_COUNTRY)) {
                getByVoyageStatement.setLong(1,
                    country.CountryId);

                try(
                    ResultSet rset = getByVoyageStatement.executeQuery()) {

                    return extractAll(rset);
                }
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int delete(IModel model) throws DAOException {
        try {
            CityModel city = (CityModel) model;

            try(
                PreparedStatement deleteStatement = getConnexion().getConnection().prepareStatement(DELETE)) {

                deleteStatement.setLong(1,
                    city.CityId);

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
        CityModel the_model = (CityModel) model;
        this.CityId = the_model.CityId;
        this.CountryId = the_model.CountryId;
        this.Name = the_model.Name;
    }

    public long getCityId() {
        return this.CityId;
    }

    public void setCityId(long cityId) {
        this.CityId = cityId;
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
