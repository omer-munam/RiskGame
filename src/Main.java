import java.io.IOException;

import Controller.GameEngine;
import logging.LogEntryBuffer;
import logging.LogWriter;


public class Main {
    public static void main(String[] args) {
    	try {
	    	LogEntryBuffer l_logentryBuffer = LogEntryBuffer.getInstance();
	    	l_logentryBuffer.attach(LogWriter.getInstance());
	    	
	        GameEngine new_game = GameEngine.getInstance();
	        new_game.start_game();
		} finally {
    		try {
    	        LogWriter.getInstance().info.close();
    	    } catch (IOException e) {
    	        System.out.println("I/O exception closing BufferedWriter");
    	    }
    	}

    }
}