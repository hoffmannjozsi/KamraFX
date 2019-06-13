package kamrafx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Hoffmann József
 */
public class Alapanyag {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nev;
    private SimpleStringProperty me;
    private SimpleStringProperty min;

    public Alapanyag() {
        this.nev = new SimpleStringProperty("");
        this.me = new SimpleStringProperty("");
        this.min = new SimpleStringProperty("");        
    }
    
    public Alapanyag(String nev, String me, double min) {
        this.nev = new SimpleStringProperty(nev);
        this.me = new SimpleStringProperty(me);
        this.min = new SimpleStringProperty("" + min);        
    }
    
    public Alapanyag(String nev, String me, String min) {
        this.nev = new SimpleStringProperty(nev);
        this.me = new SimpleStringProperty(me);
        this.min = new SimpleStringProperty(min);        
    }
    
    public Alapanyag(int id, String nev, String me, double min) {
        this.id = new SimpleIntegerProperty(id); 
        this.nev = new SimpleStringProperty(nev);
        this.me = new SimpleStringProperty(me);
        this.min = new SimpleStringProperty("" + min);       
    }
    
    public int getId() {
        return id.get();
    }

    

    public String getNev() {
        return nev.get();
    }

    public void setNev(String nev) {
        this.nev.set(nev);
    }

    public String getMe() {
        return me.get();
    }

    public void setMe(String me) {
        this.me.set(me);
    }

    public String getMin() {
        return "" + min.get();
    }
    
    public void setMin(String min) {
        this.min.set(min);
       }

    public double getMinDouble() {
        return Double.parseDouble(min.get());
    }

    public void setMin(double min) {
        this.min.set("" + min);
    }

   
    
    
}

