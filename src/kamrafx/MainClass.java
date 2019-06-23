package kamrafx;

/**
 *
 * @author Hoffmann József
 */
public class MainRepo {
    public static Logging naplo;
    Configuration conf;
    public String message ="";
    
    public MainRepo() {
        conf = new Configuration("config.ini");
        // Naplózás beállítása
        naplo = new Logging(conf.getLogLevel());
    }
    
   
}
