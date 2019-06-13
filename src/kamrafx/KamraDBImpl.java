package kamrafx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Hoffmann József
 */
public class KamraDBImpl extends MainRepo implements KamraDBInterface {

    //private final Connection conn;
    private final PreparedStatement listAllAlapanyag;
    private final PreparedStatement addDBAlapanyag;
    private final PreparedStatement updateDBAlapanyag;
    private final PreparedStatement removeDBAlapanyag;
    
    private final PreparedStatement getAllEtel;
    private final PreparedStatement addDBEtel;

    public KamraDBImpl(DBConnect db) throws SQLException {

        this.listAllAlapanyag = db.getConn().prepareStatement("SELECT * FROM alapanyag");
        this.addDBAlapanyag = db.getConn().prepareStatement("INSERT INTO alapanyag(nev, me, minimum) VALUES (?, ?, ?)");
        this.updateDBAlapanyag = db.getConn().prepareStatement("UPDATE alapanyag SET nev = ?, me = ?, minimum = ? WHERE id = ?");
        this.removeDBAlapanyag = db.getConn().prepareStatement("DELETE FROM alapanyag WHERE id = ?");        
        
        this.getAllEtel = db.getConn().prepareStatement("SELECT * FROM etel");
        this.addDBEtel = db.getConn().prepareStatement("INSERT INTO etel(nev, elad_ar) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        
    }

    @Override
    public ArrayList<Alapanyag> getAllAlapanyag() {
        ResultSet all;
        ArrayList<Alapanyag> alapanyagLista = null;
        try {
            all = this.listAllAlapanyag.executeQuery();

            alapanyagLista = new ArrayList<>();
            while (all.next()) {
                Alapanyag apny = new Alapanyag(all.getInt("id"), all.getString("nev"), all.getString("me"), all.getDouble("minimum"));
                alapanyagLista.add(apny);
            }
            return alapanyagLista;

        } catch (SQLException ex) {
            super.message = "Hiba az Alapanyag tábla lekérdezésekor.";
            naplo.addLog(message + ex, 2);
            Messages.setMessageBoxSzoveg(message);
            //throw new KamraDAOException("Hiba történt az adatbáziskapcsolatban! Üzenet: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void addDBAlapanyag(Alapanyag a) {
        try {
            this.addDBAlapanyag.setString(1, a.getNev().toLowerCase());
            this.addDBAlapanyag.setString(2, a.getMe().toLowerCase());
            this.addDBAlapanyag.setDouble(3, a.getMinDouble());
            this.addDBAlapanyag.executeUpdate();
        } catch (SQLException ex) {
            super.message = "Hiba az Alapanyag táblába való hozzáadáskor.";
            naplo.addLog(message + ex, 2);
            Messages.setMessageBoxSzoveg(message);
            //throw new KamraDAOException("Hiba történt az adatbáziskapcsolatban! Üzenet: " + ex.getMessage());
        }
    }

    @Override
    public void updateDBAlapanyag(Alapanyag a) {
        try {
            this.updateDBAlapanyag.setString(1, a.getNev().toLowerCase());
            this.updateDBAlapanyag.setString(2, a.getMe().toLowerCase());
            this.updateDBAlapanyag.setDouble(3, a.getMinDouble());
            this.updateDBAlapanyag.setInt(4, a.getId());
            this.updateDBAlapanyag.executeUpdate();
        } catch (SQLException ex) {
            super.message = "Hiba az Alapanyag tábla módosításakor.";
            naplo.addLog(message + ex, 2);
            Messages.setMessageBoxSzoveg(message);
            //throw new KamraDAOException("Hiba történt az adatbáziskapcsolatban! Üzenet: " + ex.getMessage());
        }
    }
    @Override
     public boolean removeDBAlapanyag(Alapanyag a) {
         try {
         this.removeDBAlapanyag.setInt(1, a.getId());
         this.removeDBAlapanyag.executeUpdate();
         return true;
         } catch (SQLException ex) {
            Messages.clearMessageBoxSzoveg();
            super.message = "A kiválsztott tétel ("+ a.getNev() + ") nem törölhető, mert szerepel egy másik táblában is.";
            naplo.addLog(message + ex, 2);
            
            Messages.setMessageBoxSzoveg(message);
            return false;
            //throw new KamraDAOException("Hiba történt az adatbáziskapcsolatban! Üzenet: " + ex.getMessage());
        }
         
     }
     
     @Override
    public ArrayList<Etel> getAllEtel() {
        ResultSet all;
        ArrayList<Etel> etelLista = null;
        try {
            all = this.getAllEtel.executeQuery();

            etelLista = new ArrayList<>();
            while (all.next()) {
                Etel et = new Etel(all.getInt("id"), all.getString("nev"), all.getInt("elad_ar"));
                etelLista.add(et);
            }
            return etelLista;

        } catch (SQLException ex) {
            super.message = "Hiba az Etel tábla lekérdezésekor.";
            naplo.addLog(message + ex, 2);
            Messages.setMessageBoxSzoveg(message);
            //throw new KamraDAOException("Hiba történt az adatbáziskapcsolatban! Üzenet: " + ex.getMessage());
        }
        return null;
    }
    

}
