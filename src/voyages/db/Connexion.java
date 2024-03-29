
package voyages.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import exceptions.ConnexionException;

public class Connexion {
    private Connection connection = null;

    private static Connexion connexion = null;

    public Connexion(String path) throws ConnexionException {
        if(path == null) {
            throw new ConnexionException("le path ne doit pas être vide");
        }

        java.sql.Connection conn = null;
        // try {
        // db parameters

        // throw new Exception(path);
        try {
            Class.forName("org.sqlite.JDBC");
        } catch(ClassNotFoundException e) {
            throw new ConnexionException(e);
        }

        String url = "jdbc:sqlite:"
            + "default.db"; //utiliser path?

        // create a connection to the database
        try {
            conn = DriverManager.getConnection(url);
        } catch(SQLException e) {
            throw new ConnexionException(e);
        }

        this.connection = conn;

        System.out.println("Connection to SQLite has been established.");
    }

    public Connection getConnection() {
        return this.connection;
    }

    public static Connexion setUpConnexion(ServletContext context) throws ConnexionException {
        if(context != null) {
            String path = "default.db";
            // context.getClass().getResource("default.db").getPath();
            connexion = new Connexion(path);

        }
        return connexion;
    }

    public static Connexion getOrSetUpConnexion(ServletContext context) throws ConnexionException {
        if(connexion == null
            && context != null) {

            String path = "default.db";
            // context.getClass().getResource("default.db").getPath();
            connexion = new Connexion(path);
        }
        return connexion;
    }

    public static Connexion getConnexion() {

        return connexion;
    }

}