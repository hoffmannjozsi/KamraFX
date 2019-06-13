package kamrafx;

/**
 *
 * @author Hoffmann József
 */
public class KamraDAOException extends Exception {

    public KamraDAOException() {
    }

    public KamraDAOException(String message) {
        super(message);
        
    }

    public KamraDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public KamraDAOException(Throwable cause) {
        super(cause);
    }
    
}