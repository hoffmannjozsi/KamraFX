package kamrafx;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Hoffmann József
 */
public class Configuration {

    //Alapértelmezett adatbázis kapcsolat
    private String DBType = "mysql";
    private String hostname = "localhost";
    private String port = "3306";
    private String dbName = "kamra";
    private String serverTimezone = "UTC";
    private String useSSL = "false";
    private String username = "root";
    private String password = "1234";
    private String logLevel = "1";
    

    
    public Configuration(String configFile) {
        try {
            Properties prop = new Properties();
            prop.load(new FileReader(configFile));  
            this.DBType = prop.getProperty("DBType");
            this.hostname = prop.getProperty("hostname");
            this.port = prop.getProperty("port");
            this.serverTimezone = prop.getProperty("serverTimezone"); 
            this.useSSL = prop.getProperty("useSSL");
            this.dbName = prop.getProperty("dbName");
            this.logLevel = prop.getProperty("logLevel");
             
        } catch (IOException ex) {
            System.out.println("Hiba a config.ini fájl beolvasásakor. Az adatbázis kapcsolati adatok alapértelmezettre állítva");
            System.out.println("Hibaüzenet : " + ex.getMessage());
        }
        
    }
    
    public String getDBType() {
        return DBType;
    }

    public String getHostname() {
        return hostname;
    }

    public String getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }

    public String getServerTimezone() {
        return serverTimezone;
    }

    public String getUseSSL() {
        return useSSL;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLogLevel() {
        return Integer.parseInt(logLevel);
    }

    

}

