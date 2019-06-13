/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kamrafx;

/**
 *
 * @author hoffmanj
 */
public class Messages {
    private static String fejlecSzoveg = "";
    private static String messageBoxSzoveg = "Rendszerüzenetek:\n";

    public  Messages() {
    }

    public static String getFejlecSzoveg() {
        return fejlecSzoveg;
    }

    public static void setFejlecSzoveg(String fejlecSzoveg) {
        Messages.fejlecSzoveg = fejlecSzoveg;
    }

    public static String getMessageBoxSzoveg() {
        return messageBoxSzoveg;
    }

    public static void setMessageBoxSzoveg(String messageBoxSzoveg) {
        Messages.messageBoxSzoveg+= messageBoxSzoveg + "\n";
    }
     public static void clearMessageBoxSzoveg() {
         Messages.messageBoxSzoveg = "";
     }
    
}
