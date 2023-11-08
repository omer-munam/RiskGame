package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class contains utility functions for creating and editing maps to be used in the game. It is also the entry point to be used from main to access the map editor.
 *
 * @author Mohammad Uvas
 */
public class LogWriter implements Observer {
    /**
     * The instance of the LogWriter
     */
    private static LogWriter instance = new LogWriter();
    /**
     * The filewriter of the LogWriter
     */
    public FileWriter d_fstream;
    /**
     * The buffered writer of the log writer.
     */
    public BufferedWriter d_info;
    /**
     * The file location of the logs
     */
    private final String d_base_path = System.getProperty("user.dir") + "\\logs";

    /**
     * Make the constructor private so that this class cannot be instantiated
     */
    private LogWriter() {
        try {
            File l_directory = new File(d_base_path);
            if (!l_directory.exists()) l_directory.mkdir();

            d_fstream = new FileWriter(d_base_path + "\\" + "WarZoneLog.txt");
            d_info = new BufferedWriter(d_fstream);
        } catch (Exception ex) {
            System.out.println("I/O exception in LogWriter");
        }
    }

    /**
     * If the instance was not previously created, create it. Then return the instance
     */
    public static LogWriter getInstance() {
        if (instance == null)
            instance = new LogWriter();
        return instance;
    }

    /**
     * update the log file after the Observer has been
     * notified of a new log in Buffer.
     *
     * @param p_logString: String containing the log to be logged in file.
     */
    public void update(String p_logString) {
        try {
            d_info.write(p_logString);
            d_info.newLine();
        } catch (IOException e) {
            System.out.println("I/O Exception wile writing in log file");
            e.printStackTrace();
        }
    }
}
