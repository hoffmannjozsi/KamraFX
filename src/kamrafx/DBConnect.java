package kamrafx;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hoffmann József
 */
public class DBConnect extends MainRepo {

    private Connection conn = null;
    private final String DerbyURL = "jdbc:derby:KamraDB;create=true";
    //private String message;

    //public static Logging naplo;
    //Configuration conf;
    //Létrehozzuk a kapcsolatot (hidat)
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;

    public DBConnect() {
        //conf = new Configuration("configMySQL.ini");
        // Naplózás beállítása
        //naplo = new Logging(conf.getLogLevel());

        if (conf.getDBType().equalsIgnoreCase("Derby")) {

            //Megpróbáljuk életre kelteni a kapcsolatot
            try {
                this.conn = DriverManager.getConnection(DerbyURL);
                super.message = "A híd a Derby adatbáziskezelővel létrejött";
                naplo.addLog(message, 1);
                Messages.setMessageBoxSzoveg(message);

            } catch (SQLException ex) {
                super.message= "Valami baj van a Derby Connection (híd) létrehozásakor.";
                naplo.addLog(message + ex, 2);
                Messages.setMessageBoxSzoveg(message);

            }

            //Ha életre kelt, csinálunk egy megpakolható teherautót
            if (conn != null) {

                //Megnézzük, hogy üres-e az adatbázis? 
                try {
                    dbmd = conn.getMetaData();
                } catch (SQLException ex) {
                    super.message= "Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..";
                    naplo.addLog(message + ex, 2);
                    Messages.setMessageBoxSzoveg(message);
                }

                // Megnézzük, létezik-e az adott adattábla. Ha nem, létrhozzuk                
                try { //ResultSet -hez

                    // ALAPANYAG tábla ellenőrzése / létrehozása
                    ResultSet rs = dbmd.getTables(null, "APP", "ALAPANYAG", null); //Tábla adatok kiolvasása
                    if (!rs.next()) { // Ha nincs adat, tábla létrehozása
                        try {
                            PreparedStatement createAlapanyag = conn.prepareStatement("CREATE TABLE alapanyag "
                                    + "(id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                                    + "nev varchar(45) DEFAULT NULL, me varchar(45) DEFAULT NULL, "
                                    + "minimum double DEFAULT NULL, PRIMARY KEY (id))");
                            createAlapanyag.executeUpdate();
                            super.message= "ALAPANYAG tábla létrehozva";
                            naplo.addLog(message, 1);
                            Messages.setMessageBoxSzoveg(message);
                        } catch (SQLException ex) {
                            super.message= "Valami baj van az ALAPANYAG tábla létrehozásakor.";
                            naplo.addLog(message + ex, 2);
                            Messages.setMessageBoxSzoveg(message);
                        }
                    }

                    // ETEL tábla ellenőrzése / létrehozása
                    rs = dbmd.getTables(null, "APP", "ETEL", null);  //Tábla adatok kiolvasása
                    if (!rs.next()) { // Ha nincs adat, tábla létrehozása
                        try {
                            PreparedStatement createEtel = conn.prepareStatement("CREATE TABLE etel "
                                    + "(id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                                    + "nev varchar(45) DEFAULT NULL, elad_ar int DEFAULT NULL, PRIMARY KEY (id))");
                            createEtel.executeUpdate();
                            super.message= "ETEL tábla létrehozva";
                            naplo.addLog(message, 1);
                            Messages.setMessageBoxSzoveg(message);
                        } catch (SQLException ex) {
                            super.message= "Valami baj van az ETEL tábla létrehozásakor.";
                            naplo.addLog(message + ex, 2);
                            Messages.setMessageBoxSzoveg(message);
                        }
                    }

                    // BESZERZES tábla ellenőrzése / létrehozása
                    rs = dbmd.getTables(null, "APP", "BESZERZES", null);  //Tábla adatok kiolvasása
                    if (!rs.next()) { // Ha nincs adat, tábla létrehozása
                        try {
                            PreparedStatement createBeszerzes = conn.prepareStatement("CREATE TABLE beszerzes "
                                    + "(id int NOT NULL NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), alap_id int NOT NULL, "
                                    + "mennyiseg double DEFAULT NULL, datum date DEFAULT NULL, atvezetve int DEFAULT 0, me varchar(10) DEFAULT NULL, "
                                    + "egysegar int DEFAULT NULL, PRIMARY KEY (id), CONSTRAINT alap_id FOREIGN KEY (alap_id) REFERENCES alapanyag (id))");
                            createBeszerzes.executeUpdate();
                            super.message= "BESZERZES tábla létrehozva";
                            naplo.addLog(message, 1);
                            Messages.setMessageBoxSzoveg(message);
                        } catch (SQLException ex) {
                            super.message= "Valami baj van az BESZERZES tábla létrehozásakor.";
                            naplo.addLog(message + ex, 2);
                            Messages.setMessageBoxSzoveg(message);
                        }
                    }

                    // ELADAS tábla ellenőrzése / létrehozása
                    rs = dbmd.getTables(null, "APP", "ELADAS", null);  //Tábla adatok kiolvasása
                    if (!rs.next()) { // Ha nincs adat, tábla létrehozása
                        try {
                            PreparedStatement createEladas = conn.prepareStatement("CREATE TABLE eladas "
                                    + "(id int NOT NULL, etel_id int NOT NULL, adag int DEFAULT NULL, datum date DEFAULT NULL, PRIMARY KEY (id),"
                                    + "CONSTRAINT elad_etel_id FOREIGN KEY (etel_id) REFERENCES etel (id))");
                            createEladas.executeUpdate();
                            super.message= "ELADAS tábla létrehozva";
                            naplo.addLog(message, 1);
                            Messages.setMessageBoxSzoveg(message);
                        } catch (SQLException ex) {
                            super.message= "Valami baj van az ELADAS tábla létrehozásakor.";
                            naplo.addLog(message + ex, 2);
                            Messages.setMessageBoxSzoveg(message);
                        }
                    }

                    // KESZLET tábla ellenőrzése / létrehozása
                    rs = dbmd.getTables(null, "APP", "KESZLET", null);  //Tábla adatok kiolvasása
                    if (!rs.next()) { // Ha nincs adat, tábla létrehozása
                        try {
                            PreparedStatement createKeszlet = conn.prepareStatement("CREATE TABLE keszlet "
                                    + "(id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                                    + "beszerzes_id int NOT NULL, alap_id int NOT NULL, "
                                    + "mennyiseg double DEFAULT NULL, ar int DEFAULT NULL, elfogyott int DEFAULT 0, "
                                    + "elfogy_datum date DEFAULT NULL, me varchar(10) DEFAULT NULL, PRIMARY KEY (id), "
                                    + "CONSTRAINT beszerzes_id FOREIGN KEY (beszerzes_id) REFERENCES beszerzes (id), "
                                    + "CONSTRAINT keszlet_alap_id FOREIGN KEY (alap_id) REFERENCES alapanyag (id))");
                            createKeszlet.executeUpdate();
                            super.message= "KESZLET tábla létrehozva";
                            naplo.addLog(message, 1);
                            Messages.setMessageBoxSzoveg(message);
                        } catch (SQLException ex) {
                            super.message= "Valami baj van az KESZLET tábla létrehozásakor.";
                            naplo.addLog(message + ex, 2);
                            Messages.setMessageBoxSzoveg(message);
                        }
                    }

                    // RECEPT tábla ellenőrzése / létrehozása
                    rs = dbmd.getTables(null, "APP", "RECEPT", null);  //Tábla adatok kiolvasása
                    if (!rs.next()) { // Ha nincs adat, tábla létrehozása
                        try {
                            PreparedStatement createKeszlet = conn.prepareStatement("CREATE TABLE recept "
                                    + "(etel_id int NOT NULL, alap_id int NOT NULL, mennyiseg double DEFAULT NULL, me varchar(10) DEFAULT NULL)");
                            createKeszlet.executeUpdate();
                            super.message= "RECEPT tábla létrehozva";
                            naplo.addLog(message, 1);
                            Messages.setMessageBoxSzoveg(message);
                        } catch (SQLException ex) {
                            super.message= "Valami baj van az RECEPT tábla létrehozásakor.";
                            naplo.addLog(message + ex, 2);
                            Messages.setMessageBoxSzoveg(message);
                        }
                    }

                } catch (SQLException ex) { // Ha a ResultSet kiolvasása során hiba lépne fel 
                    super.message = "Valami baj van a tábla adatok lekérdezésekor.";
                    naplo.addLog(message + ex, 2);
                    Messages.setMessageBoxSzoveg(message);
                }

            }

        } else {

            //MySQL adatbázis kapcsolat létrehozása. Feltételezzük, hogy az adatbázis a megfelelő táblákkal már létre van hozva    
            if (conf.getDBType().equalsIgnoreCase("mysql")) {
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://" + conf.getHostname() + ":" + conf.getPort() + "/" + conf.getDbName()
                            + "?serverTimezone=" + conf.getServerTimezone() + "&useSSL=" + conf.getUseSSL(), conf.getUsername(), conf.getPassword());
                    super.message = "A híd a MySQL adatbáziskezelővel létrejött";
                    naplo.addLog(message, 1);
                    Messages.setMessageBoxSzoveg(message);

                } catch (SQLException ex) {
                    super.message= "Valami baj van a MySQL Connection (híd) létrehozásakor.";
                    naplo.addLog(message + ex, 2);
                    Messages.setMessageBoxSzoveg(message);

                }
            } else {
                super.message= "Nincs vagy rossz az adatbázis típus beállítása a config fájlban";
                naplo.addLog(message, 1);
                Messages.setMessageBoxSzoveg(message);
            }

        }

    }

    public String getMessage() {
        return message;
    }

    public Connection getConn() {
        return conn;
    }

//    public ArrayList<Alapanyag> getAllAlapanyag() {
//        String sql = "select * from alapanyag";
//        ArrayList<Alapanyag> alapanyagok = null;
//        try {
//            ResultSet rs = createStatement.executeQuery(sql);
//            alapanyagok = new ArrayList<>();
//
//            while (rs.next()) {
//                Alapanyag actualAlapanyag = new Alapanyag(rs.getString("nev"), rs.getString("me"), rs.getString("min_menny"));
//                alapanyagok.add(actualAlapanyag);
//            }
//        } catch (SQLException ex) {
//            System.out.println("Valami baj van a userek kiolvasásakor");
//            System.out.println("" + ex);
//        }
//        return alapanyagok;
//    }
//    public void addContact(Person person){
//      try {
//        String sql = "insert into contacts (lastname, firstname, email) values (?,?,?)";
//        PreparedStatement preparedStatement = conn.prepareStatement(sql);
//        preparedStatement.setString(1, person.getLastName());
//        preparedStatement.setString(2, person.getFirstName());
//        preparedStatement.setString(3, person.getEmail());
//        preparedStatement.execute();
//        } catch (SQLException ex) {
//            System.out.println("Valami baj van a contact hozzáadásakor");
//            System.out.println(""+ex);
//        }
//    }
//    
//    public void updateContact(Person person){
//      try {
//            String sql = "update contacts set lastname = ?, firstname = ? , email = ? where id = ?";
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, person.getLastName());
//            preparedStatement.setString(2, person.getFirstName());
//            preparedStatement.setString(3, person.getEmail());
//            preparedStatement.setInt(4, Integer.parseInt(person.getId()));
//            preparedStatement.execute();
//        } catch (SQLException ex) {
//            System.out.println("Valami baj van a contact hozzáadásakor");
//            System.out.println(""+ex);
//        }
//    }
//    
//     public void removeContact(Person person){
//      try {
//            String sql = "delete from contacts where id = ?";
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setInt(1, Integer.parseInt(person.getId()));
//            preparedStatement.execute();
//        } catch (SQLException ex) {
//            System.out.println("Valami baj van a contact törlésekor");
//            System.out.println(""+ex);
//        }
//    }
}
