
package voyages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private Connection connection = null;

    private static Connexion connexion = null;

    public Connexion() throws SQLException,
        ClassNotFoundException,
        Exception {
        java.sql.Connection conn = null;
        //try {
        // db parameters
        Class.forName("org.sqlite.JDBC");

        String url = "jdbc:sqlite:default.db";

        // create a connection to the database
        conn = DriverManager.getConnection(url);

        this.connection = conn;

        System.out.println("Connection to SQLite has been established.");
        //throw new Exception(""+conn);
        /*
        } catch (SQLException e) {
        System.out.println(e.getMessage());
        } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        connection = conn;
        }*/
    }

    public Connection getConnection() {
        return this.connection;
    }

    public static Connexion getConnexion() throws SQLException,
        ClassNotFoundException,
        Exception {
        if(connexion == null) {
            System.out.println("Get connexion");
            connexion = new Connexion();
        }

        return connexion;
    }

}