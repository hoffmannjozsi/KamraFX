/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kamrafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Hoffmann József
 */
public class CsakEtel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nev;
    private SimpleStringProperty elad_ar;

    public CsakEtel() {
        this.nev = new SimpleStringProperty("");
        this.elad_ar = new SimpleStringProperty("");
    }

    public CsakEtel(int id, String nev, int elad_ar) {
        this.id = new SimpleIntegerProperty(id);
        this.nev = new SimpleStringProperty(nev);
        this.elad_ar = new SimpleStringProperty("" + elad_ar);
    }

    public CsakEtel(String nev, int elad_ar) {
        this.nev = new SimpleStringProperty(nev);
        this.elad_ar = new SimpleStringProperty("" + elad_ar);
    }
    
     public CsakEtel(String nev, String elad_ar) {
        this.nev = new SimpleStringProperty(nev);
        this.elad_ar = new SimpleStringProperty(elad_ar);
    }
    
    public int getId() {
        return id.get();
    }

    public String getNev() {
        return nev.get();
    }

    public String getElad_ar() {
        return elad_ar.get();
    }
    public int getElad_arInt() {
        return Integer.parseInt(elad_ar.get());
    }

    public void setNev(String nev) {
        this.nev = new SimpleStringProperty(nev);
    }

    public void setElad_ar(String elad_ar) {
        this.elad_ar.set(elad_ar);
    }

    
    @Override
    public String toString() {
        return nev.get() + " " + elad_ar.get() + " Ft";
    }
        
}
