package voyages.models.implementations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
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

public class EscaleModel implements IModel {


    private Connexion connexion = null;

    public long EscaleId;

    public long CityId;
    
    public long ProductId;
    
    public String NomActivite;
    
    public String DescriptionActivite;

    public boolean IsArrivee;
    
    public boolean IsDepart;
    
    public Date DateEscale;
    
    private String Image;

    private static String columns = "EscaleId, CityId, IsDepart, NomActivite, DescriptionActivite, DateEscale, ProductId, Image, IsArrivee";

    private static String insert_columns = "CityId, IsDepart, NomActivite, DescriptionActivite, DateEscale, ProductId, Image, IsArrivee";

    private static String TABLE = "Escale";
    
    private static String GET_ALL = "SELECT "
            + TABLE
            + " FROM Orders";
    private static String GET_BY_ID = "SELECT "
        + columns
        + " FROM " + TABLE + " WHERE EscaleId = ?";

    private static String FIND_BY_PRODUCT = "SELECT "
        + columns
        + " FROM " 
        + TABLE 
        + " WHERE ProductId = ?";
    
    private static String FIND_BY_CITY = "SELECT "
            + columns
            + " FROM " 
            + TABLE 
            + " WHERE CityId = ?";
    
    private static String FIND_BY_DATE_GREATER = "SELECT "
            + columns
            + " FROM " 
            + TABLE 
            + " WHERE DateEscale > ?"; 

    private static String CREATE = "INSERT INTO " + TABLE + "("
        + insert_columns
        + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";

    private static String UPDATE = "UPDATE " + TABLE + " SET CityId = ?, IsDepart = ?, NomActivite = ?, DescriptionActivite = ?, DateEscale = ?, ProductId = ?, Image = ?, IsArrivee = ? WHERE EscaleId = ?";

    @Override
    public void setConnexion(Connexion conn) {
        this.connexion = conn;
    }

    @Override
    public Connexion getConnexion() {
        return this.connexion;
    }

    public EscaleModel(Connexion connexion) {
        setConnexion(connexion);
    }

    public EscaleModel(IModel model) {
        setConnexion(model.getConnexion());
    }

    @Override
    public IModel read(IModel model) throws DAOException {
        try {
            EscaleModel the_model = (EscaleModel) model;

            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(EscaleModel.GET_BY_ID);
            getByIdStatement.setLong(1,
                the_model.EscaleId);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {
                if(rset.next()) {
                    return extract(rset);
                }
                throw new DAOException("Escale with id "
                    + the_model.EscaleId
                    + " does not exist.");
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }
    
    @Override
    public int update(IModel model) throws DAOException {
        try {
            EscaleModel escale = (EscaleModel) model;

            try(PreparedStatement updateStatement = getConnexion().getConnection().prepareStatement(UPDATE)) {
            	 
            	updateStatement.setLong(1, escale.CityId);
            	updateStatement.setLong(2, escale.IsDepart ? 1 : 0);
            	updateStatement.setString(3, escale.NomActivite);
            	updateStatement.setString(4, escale.DescriptionActivite);
            	updateStatement.setString(5, DateParser.format(escale.DateEscale));
            	updateStatement.setLong(6, escale.ProductId);
            	updateStatement.setString(7, escale.Image);
            	updateStatement.setInt(8, escale.IsArrivee ? 1 : 0);
            	updateStatement.setLong(9, escale.EscaleId);

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
    public int create(IModel model) throws DAOException {
        try {
            EscaleModel escale = (EscaleModel) model;

            try(
                PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(CREATE)) {

            	createStatement.setLong(1, escale.CityId);
            	createStatement.setInt(2, escale.IsDepart ? 1 : 0);
            	createStatement.setString(3, escale.NomActivite);
            	createStatement.setString(4, escale.DescriptionActivite);
            	createStatement.setString(5, DateParser.format(escale.DateEscale));
            	createStatement.setLong(6, escale.ProductId);
            	createStatement.setString(7, escale.Image);
            	createStatement.setInt(8, escale.IsArrivee ? 1 : 0);
            	
            	int affectedRows = createStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Creating escale failed, no rows affected.");
                }

                try(
                    ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                    	escale.EscaleId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating escale failed, no ID obtained.");
                    }
                }
                return affectedRows;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }
    private EscaleModel extract(ResultSet rset) throws DAOException {
        try {
            EscaleModel escale = new EscaleModel(this);
            escale.EscaleId = rset.getLong("EscaleId");
            escale.CityId = rset.getLong("CityId");
            escale.IsArrivee = rset.getBoolean("IsArrivee");
            escale.IsDepart = rset.getBoolean("IsDepart");
            escale.Image = rset.getString("Image");
            escale.ProductId = rset.getLong("ProductId");
            escale.NomActivite = rset.getString("NomActivite");
            escale.DescriptionActivite = rset.getString("DescriptionActivite");
            
            try {
            	Date parsed = DateParser.parse(rset.getString("DateEscale"));
            	escale.DateEscale = parsed;
			} catch (ParseException e) {
				System.err.println("Cannot parse DateEscale for Escale # " + escale.ProductId);
			}

            return escale;
        } catch(SQLException sqlExcept) {
            throw new DAOException(sqlExcept);
        }
    }
    
    private List<EscaleModel> extractAll(ResultSet rset) throws DAOException {
        List<EscaleModel> escales = new ArrayList<>();
        try {
            while(rset.next()) {
            	escales.add(extract(rset));
            }
            return escales;
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    public List<EscaleModel> getAll() throws DAOException {
        try {
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(EscaleModel.GET_ALL);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {

                return extractAll(rset);
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }
    
    public List<EscaleModel> findByProduct(ProductModel product) throws DAOException {
        try {
            try(
                PreparedStatement getByVoyageStatement = getConnexion().getConnection().prepareStatement(EscaleModel.FIND_BY_PRODUCT)) {
            	getByVoyageStatement.setLong(1,
                    product.ProductId);

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
    public void delete(IModel model) throws DAOException {
        throw new DAOException("Not implemented");
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
        EscaleModel the_model = (EscaleModel) model;
        this.ProductId = the_model.ProductId;
        this.Image = the_model.Image;
        this.CityId = the_model.CityId;
        this.DateEscale = the_model.DateEscale;
        this.IsArrivee = the_model.IsArrivee;
        this.IsDepart = the_model.IsDepart;
        this.EscaleId = the_model.EscaleId;
        this.DescriptionActivite = the_model.DescriptionActivite;
        this.NomActivite = the_model.NomActivite;
    }

}
