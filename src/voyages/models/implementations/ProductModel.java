
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

public class ProductModel implements IModel {

    private Connexion connexion = null;

    public Long ProductId;

    public String Name;

    public String Description;

    public String Image;

    public double Price;

    public int IsVedette = 0;

    public Date DateDebut;

    public Date DateFin;

    private static String columns = "ProductId, Name, Description, Image, Price, IsVedette, DateDebut, DateFin";

    private static String non_id_columns = "Name, Description, Image, Price, IsVedette, DateDebut, DateFin";

    private static String TABLE = "Products";

    private static String FIND_BY_DEPARTURE_CITY = "SELECT * FROM "
        + TABLE
        + ", Escale "
        + "WHERE Escale.ProductId = "
        + TABLE
        + ".ProductId  "
        + "AND Escale.CityId = ? AND Escale.IsDepart = 1 AND datetime("
        + TABLE
        + ".DateDebut) > datetime(?) "
        + " GROUP BY "
        + TABLE
        + ".ProductId";

    private static String FIND_BY_MIN_TRIP_START_DATE = "SELECT * FROM "
        + TABLE
        + "WHERE datetime("
        + TABLE
        + ".DateDebut) > datetime(?) ";

    private static String GET_BY_ID = "SELECT "
        + columns
        + " FROM Products WHERE ProductId = ?";

    private static String GET_ALL_BY_ID = "SELECT "
        + columns
        + " FROM Products WHERE ProductId IN ?";

    private static String GET_ALL = "SELECT "
        + columns
        + " FROM Products";

    private static String CREATE = "INSERT INTO Products ("
        + non_id_columns
        + ") VALUES(?, ?, ?, ?, ?, ?, ?) ";

    private static String DELETE = "DELETE FROM Products WHERE ProductId = ?";

    private static String UPDATE = "UPDATE Products SET Name = ?, Description = ?, Image = ?, Price = ?, IsVedette = ?, DateDebut = ?, DateFin = ? WHERE ProductId = ?";

    private static String GET_VEDETTES = "SELECT "
        + columns
        + " FROM Products WHERE IsVedette = 1";

    private static String BULK_UNVEDETTE = "UPDATE Products SET IsVedette = 0";

    @Override
    public void setConnexion(Connexion conn) {
        this.connexion = conn;
    }

    @Override
    public Connexion getConnexion() {
        return this.connexion;
    }

    public ProductModel(Connexion connexion) {
        setConnexion(connexion);
    }

    public ProductModel(IModel model) {
        setConnexion(model.getConnexion());
    }

    @Override
    public IModel read(IModel model) throws DAOException {
        try {
            ProductModel the_model = (ProductModel) model;

            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(ProductModel.GET_BY_ID);
            getByIdStatement.setLong(1,
                the_model.ProductId.longValue());

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {
                if(rset.next()) {
                    return extract(rset);
                }
                throw new DAOException("Product with id "
                    + the_model.ProductId
                    + " does not exist.");
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    private ProductModel extract(ResultSet rset) throws DAOException {
        try {
            ProductModel product = new ProductModel(this);

            product.ProductId = new Long(Long.parseLong(rset.getString("ProductId")));
            product.Name = rset.getString("Name");
            product.Description = rset.getString("Description");
            product.Image = rset.getString("Image");
            product.Price = rset.getDouble("Price");
            product.IsVedette = rset.getInt("IsVedette");

            try {
                Date parsed = DateParser.parse(rset.getString("DateDebut"));
                product.DateDebut = parsed;
            } catch(ParseException e) {
                System.err.println("Cannot parse DateDebut for Product # "
                    + product.ProductId);
            }
            try {
                product.DateFin = DateParser.parse(rset.getString("DateFin"));
            } catch(ParseException e) {
                System.err.println("Cannot parse DateFin for Product # "
                    + product.ProductId);
            }

            return product;
        } catch(SQLException sqlExcept) {
            throw new DAOException(sqlExcept);
        }
    }

    private List<ProductModel> extractAll(ResultSet rset) throws DAOException {
        List<ProductModel> products = new ArrayList<>();
        try {
            while(rset.next()) {
                products.add(extract(rset));
            }
            return products;
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    public List<ProductModel> getAll() throws DAOException {
        try {
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(ProductModel.GET_ALL);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {

                return extractAll(rset);
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<ProductModel> findVedettes() throws DAOException {
        try {
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(ProductModel.GET_VEDETTES);

            try(
                ResultSet rset = getByIdStatement.executeQuery()) {

                return extractAll(rset);
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<ProductModel> getAll(Array productIds) throws DAOException {
        try {
            PreparedStatement getByIdStatement = getConnexion().getConnection().prepareStatement(ProductModel.GET_ALL_BY_ID);
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
        // Name, Description, Image, Price, IsVedette, DateDebut, DateFin
        try {
            ProductModel the_product = (ProductModel) model;

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
                createStatement.setString(6,
                    DateParser.format(the_product.DateDebut));
                createStatement.setString(7,
                    DateParser.format(the_product.DateFin));

                int affectedRows = createStatement.executeUpdate();
                if(affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try(
                    ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        this.ProductId = new Long(Long.parseLong(generatedKeys.getString(1)));
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

    public List<ProductModel> findByDepartureCity(CityModel city,
        Date tripStartDate) throws DAOException {
        try {
            PreparedStatement findByStatement = getConnexion().getConnection().prepareStatement(FIND_BY_DEPARTURE_CITY);
            findByStatement.setLong(1,
                city.CityId);
            findByStatement.setString(2,
                DateParser.format(tripStartDate));

            try(
                ResultSet rset = findByStatement.executeQuery()) {

                return extractAll(rset);
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<ProductModel> findByDepartureDateGreater(Date tripMinStartDate) throws DAOException {
        try {
            PreparedStatement findByStatement = getConnexion().getConnection().prepareStatement(FIND_BY_MIN_TRIP_START_DATE);

            findByStatement.setString(1,
                DateParser.format(tripMinStartDate));

            try(
                ResultSet rset = findByStatement.executeQuery()) {

                return extractAll(rset);
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int update(IModel model) throws DAOException {
        try {
            ProductModel the_product = (ProductModel) model;

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
                    the_product.ProductId.longValue());
                createStatement.setString(7,
                    DateParser.format(the_product.DateDebut));
                createStatement.setString(8,
                    DateParser.format(the_product.DateFin));
                int affectedRows = createStatement.executeUpdate();

                return affectedRows;
            }
        } catch(SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int delete(IModel model) throws DAOException {
        try {
            ProductModel the_product = (ProductModel) model;

            try(
                PreparedStatement createStatement = getConnexion().getConnection().prepareStatement(DELETE)) {

                createStatement.setLong(1,
                    the_product.ProductId.longValue());

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

    public byte[] getImageAsBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /*
        File file = new File("images/" + this.Image);
        FileInputStream fis = null;
        InputStream in = new FileInputStream(file);
          */

        try(
            InputStream in = ProductModel.class.getResourceAsStream("images/"
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

    public List<EscaleModel> getEscales() throws DAOException {
        EscaleModel model = new EscaleModel(this);
        return model.findByProduct(this);
    }

    private void copy(IModel model) {
        ProductModel the_model = (ProductModel) model;
        this.ProductId = the_model.ProductId;
        this.Image = the_model.Image;
        this.IsVedette = the_model.IsVedette;
        this.Price = the_model.Price;
        this.Name = the_model.Name;
        this.Description = the_model.Description;
    }

    public Long getProductId() {
        return this.ProductId;
    }

    public void setProductId(Long productId) {
        this.ProductId = productId;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getImage() {
        return this.Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public double getPrice() {
        return this.Price;
    }

    public void setPrice(double price) {
        this.Price = price;
    }

    public int getIsVedette() {
        return this.IsVedette;
    }

    public void setIsVedette(int isVedette) {
        this.IsVedette = isVedette;
    }

    public Date getDateDebut() {
        return this.DateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.DateDebut = dateDebut;
    }

    public Date getDateFin() {
        return this.DateFin;
    }

    public void setDateFin(Date dateFin) {
        this.DateFin = dateFin;
    }
}