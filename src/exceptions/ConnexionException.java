package exceptions;

public class ConnexionException  extends Exception {

    private static final long serialVersionUID = 1L;

    public ConnexionException(String message,
        Throwable cause) {
        super(message,
            cause);
    }

    public ConnexionException(String message) {
        super(message);
    }

    public ConnexionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}