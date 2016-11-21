
package voyages;

public class DAOException extends Exception {
    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    public DAOException(String message,
        Throwable cause) {
        super(message,
            cause);
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}