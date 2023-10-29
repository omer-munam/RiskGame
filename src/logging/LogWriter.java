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

	private static LogWriter instance = new LogWriter();
	private String base_path = String.valueOf(System.getProperty("user.dir"))+"\\logs";
	public FileWriter fstream;
	public BufferedWriter info;
	
	/**
	* Make the constructor private so that this class cannot be instantiated
	*/
	private LogWriter(){
		try {
			File directory = new File(base_path);
		    if (! directory.exists()) directory.mkdir();
		    
			fstream = new FileWriter(base_path+"\\"+"WarZoneLog.txt");
			info = new BufferedWriter(fstream);
		}
		catch(Exception ex) {
			System.out.println("I/O exception in LogWriter");
		}
	}
	
	/**
	* If the instance was not previously created, create it. Then return the instance
	*/
	public static LogWriter getInstance(){
		if (instance == null)
		instance = new LogWriter();
		return instance;
	}
	
	/**
	 * update the log file after the Observer has been
	 * notified of a new log in Buffer.
	 *
	 * @param p_logString: String containing the log to be logged in file.
	 * @return none
	 */
	public void update(String p_logString) {
		try {
			info.write(p_logString);
			info.newLine();
		} catch (IOException e) {
			System.out.println("I/O Exception wile writing in log file");
			e.printStackTrace();
		}
	}
}
