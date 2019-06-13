package kamrafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Hoffmann József
 */
public class Hozzavalo {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nev;
    private SimpleStringProperty mennyiseg; //Double
    private SimpleStringProperty mennyisegEgyseg;

    public Hozzavalo(int id, String nev, double mennyiseg, String mennyisegEgyseg) {
        this.id = new SimpleIntegerProperty(id);
        this.nev = new SimpleStringProperty(nev);
        this.mennyiseg = new SimpleStringProperty("" + mennyiseg);
        this.mennyisegEgyseg = new SimpleStringProperty(mennyisegEgyseg);
    }

    public int getId() {
        return id.get();
    }

    public String getNev() {
        return nev.get();
    }

    public double getMennyiseg() {
        return Double.parseDouble(mennyiseg.get());
    }

    public String getMennyisegEgyseg() {
        return mennyisegEgyseg.get();
    }

    @Override
    public String toString() {
        return nev.get() + " " + mennyiseg.get() + " " + mennyisegEgyseg.get();
    }
    
    
    
}
