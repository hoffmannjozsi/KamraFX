package kamrafx;

import java.util.ArrayList;


/**
 *
 * @author Hoffmann József
 */
public interface KamraDBInterface {
    
    public ArrayList<Alapanyag> getAllAlapanyag() throws KamraDAOException; 
    public void addDBAlapanyag(Alapanyag a) throws KamraDAOException; 
    public void updateDBAlapanyag (Alapanyag a);
    public boolean removeDBAlapanyag(Alapanyag a);
    
     public ArrayList<Etel> getAllEtel() throws KamraDAOException; 
     //public void addDBEtel(Etel et);
}
