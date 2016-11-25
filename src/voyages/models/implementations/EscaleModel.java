
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
    
    public String Cityname;

    public String Name;

    public String Description;
    
    public Date DateEscale;
    
    public long ProductId;
    
    private static String columns = "EscaleId, CityId, Cityname, Name, Description, DateEscale, ProductId";

    private static String non_id_columns = "Cityname, Name, Description, DateEscale";

    private static String GET_BY_ID = "SELECT "
        + columns
        + " FROM Escale WHERE EscaleId = ?";

    private static String GET_ALL_BY_ID = "SELECT "
        + columns
        + " FROM Escale WHERE EscaleId IN ?";

    private static String GET_ALL = "SELECT "
        + columns
        + " FROM Escale";

    private static String CREATE = "INSERT INTO Escale ("
        + non_id_columns
        + ") VALUES(?, ?, ?, ?) ";

    private static String UPDATE = "UPDATE Escale Set SET Cityname = ?, Name = ?, Description = ?, DateEscale = ? WHERE ProductId = ?";

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

    private EscaleModel extract(ResultSet rset) throws DAOException {
        try {
            EscaleModel escale = new EscaleModel(this);
            escale.EscaleId = rset.getInt("EscaleId");
            escale.CityId = rset.getInt("CityId");
            escale.Cityname = rset.getString("Cityname");
            escale.Name = rset.getString("Name");
            escale.Description = rset.getString("Description");
            escale.ProductId = rset.getInt("ProductId");
            
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
    
    /* Continue de la */

    private List<EscaleModel> extractAll(ResultSet rset) throws DAOException {
        List<EscaleModel> products = new ArrayList<>();
        try {
            while(rset.next()) {
                products.add(extract(rset));
            }
            return products;
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

    public List<EscaleModel> getAll(Array productIds) throws DAOException {
        try {
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(EscaleModel.GET_ALL_BY_ID);
            getByIdStatement.setArray(1,
                productIds);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {

                return extractAll(rset);
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int create(IModel model) throws DAOException {
        try {
            EscaleModel the_product = (EscaleModel) model;

            try(
                PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(CREATE)) {
                createStatement.setString(1,
                    the_product.Name);
                createStatement.setString(2,
                    the_product.Description);
                createStatement.setString(3,
                    the_product.Image);
                createStatement.setDouble(4,
                    the_product.Price);
                createStatement.setInt(5,
                    the_product.IsVedette);
                createStatement.setString(6, dateFormatter.format(the_product.DateEscale));
                createStatement.setString(7, dateFormatter.format(the_product.DateFin));

                int affectedRows = createStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try(
                    ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        this.ProductId = generatedKeys.getLong(1);
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
        try {
            EscaleModel the_product = (EscaleModel) model;

            try(
                PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(UPDATE)) {
                createStatement.setString(1,
                    the_product.Name);
                createStatement.setString(2,
                    the_product.Description);
                createStatement.setString(3,
                    the_product.Image);
                createStatement.setDouble(4,
                    the_product.Price);
                createStatement.setInt(5,
                    the_product.IsVedette);
                createStatement.setLong(6,
                    the_product.ProductId);
                createStatement.setString(7,
                        dateFormatter.format(the_product.DateEscale));
                createStatement.setString(8,
                        dateFormatter.format(the_product.DateFin));
                int affectedRows = createStatement.executeUpdate();

                return affectedRows;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    public int bulk_unvedette() throws DAOException {
        try(
            PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(BULK_UNVEDETTE)) {

            int affectedRows = createStatement.executeUpdate();

            return affectedRows;
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(IModel model) throws DAOException {
        throw new DAOException("Not implemented");
    }

    public byte[] getImageAsBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /*
        File file = new File("images/" + this.Image);
        FileInputStream fis = null;
        InputStream in = new FileInputStream(file);
          */

        try(
            InputStream in = EscaleModel.class.getResourceAsStream("images/"
                + this.Image)) {
            if(in == null) {
                throw new IOException("Cannot find resource images/"
                    + this.Image);
            }
            byte[] buffer = new byte[4096];
            for(;;) {
                int nread = in.read(buffer);
                if(nread <= 0) {
                    break;
                }
                baos.write(buffer,
                    0,
                    nread);
            }
            byte[] data = baos.toByteArray();
            /*new String(data, TODO WTF?
                "Windows-1252");*/
            byte[] asByteObjects = new byte[data.length];
            for(int i = 0 ; i < data.length ; ++i) {
                asByteObjects[i] = data[i];
            }
            return asByteObjects;
        }
    }

    public static String encode(byte[] data) {
        char[] tbl = {'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            '+',
            '/'};

        StringBuilder buffer = new StringBuilder();
        int pad = 0;
        for(int i = 0 ; i < data.length ; i += 3) {

            int b = ((data[i]
                & 0xFF) << 16)
                & 0xFFFFFF;
            if(i
                + 1 < data.length) {
                b |= (data[i
                    + 1]
                    & 0xFF) << 8;
            } else {
                pad++;
            }
            if(i
                + 2 < data.length) {
                b |= (data[i
                    + 2]
                    & 0xFF);
            } else {
                pad++;
            }

            for(int j = 0 ; j < 4
                - pad ; j++) {
                int c = (b
                    & 0xFC0000) >> 18;
                buffer.append(tbl[c]);
                b <<= 6;
            }
        }
        for(int j = 0 ; j < pad ; j++) {
            buffer.append("=");
        }

        return buffer.toString();
    }

    public String getImageAsBase64() throws IOException {
        byte[] imgData = this.getImageAsBytes();
        String imgDataBase64 = new String(encode(imgData));
        return imgDataBase64;
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
        this.IsVedette = the_model.IsVedette;
        this.Price = the_model.Price;
        this.Name = the_model.Name;
        this.Description = the_model.Description;
    }
}