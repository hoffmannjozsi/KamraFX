package kamrafx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Hoffmann József
 */
public class Logging {

    private final String LOGFILE = "log.txt";
    private final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
    private int level;
    private String message;

    public Logging() {
    }

    public Logging(int level) {
        this.level = level;
    }

    /**
     * Logolás: level 0: semmit, level 1: mindent, level 2: csak hibák. A
     * logolás szintjét a config fájlban lehet beállítani.
     *
     * @param message log bejegyzés
     * @param level logolás szintje
   *
     */
    public void addLog(String message, int level) {

        switch (this.level) {
            case 0:
                break;
            case 1:
                logWrite(message);
                break;
            case 2:
                if (level == 2) {
                    logWrite(message);
                }
                break;
            default:
                break;
        }
    }

    private void logWrite(String message) {
        //ideiglenesen a konzolra is kiírjuk az üzenetet
        System.out.println(message);
        this.message = message;
        // Majd a log fájlba
        try (PrintWriter output = new PrintWriter(new FileWriter(LOGFILE, true))) {
            LocalDateTime now = LocalDateTime.now();
            output.printf("%s\r\n", now.format(DTF) + " -> " + message);
        } catch (IOException ex) {
            System.out.println("Hiba a logfájl megnyitása közben");
        }
    }

    public String getMessage() {
        return message;
    }


}
