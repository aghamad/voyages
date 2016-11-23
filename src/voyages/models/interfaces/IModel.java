
package voyages.models.interfaces;

import java.text.SimpleDateFormat;

import exceptions.DAOException;
import voyages.db.Connexion;

public interface IModel {

    //private abstract IModel extract(ResultSet rset) throws DAOException;
    Connexion connexion = null;
    
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
    
    //public abstract List<String> getColumns();
    //public void IModel(Connexion connexion);
    public void setConnexion(Connexion conn);

    public Connexion getConnexion();

    public IModel read(IModel model) throws DAOException;

    public int create(IModel model) throws DAOException;

    public void delete(IModel model) throws DAOException;

    public int update(IModel model) throws DAOException;

    public void create() throws DAOException;

    public void delete() throws DAOException;

    public void update() throws DAOException;

    public void read() throws DAOException;

    //public List<IModel> getAll() throws DAOException;

}