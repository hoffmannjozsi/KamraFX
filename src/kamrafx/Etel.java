package kamrafx;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Hoffmann József
 */
public class Etel extends CsakEtel {
    
    private List<Hozzavalo> hozzavalok = new ArrayList<>();;

    public Etel() {}
    
    public Etel(String nev, String elad_ar) {
        super(nev, elad_ar);
    }
    
    public Etel(String nev, int elad_ar, List<Hozzavalo> hozzavalok) {
        super(nev, elad_ar);
        this.hozzavalok = hozzavalok;
    }
    
    public Etel(int id, String nev, int elad_ar ) {
        super(id, nev, elad_ar);               
    }
    
    public Etel(int id, String nev, int elad_ar, List<Hozzavalo> hozzavalok) {
        super(id, nev, elad_ar);       
        this.hozzavalok = hozzavalok;
    }
    


    public List<Hozzavalo> getHozzavalok() {
        return hozzavalok;
    }

   

    public void setHozzavalok(List<Hozzavalo> hozzavalok) {
        this.hozzavalok = hozzavalok;
    }

   
    
    
}
