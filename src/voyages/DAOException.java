package voyages;


public class DAOException extends Exception {
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    public DAOException(String message) {
        super(message);
    }
    public DAOException(Throwable cause) {
        super(cause);
    }
    public String getMessage() {
        return super.getMessage();
        }
}