package logging;

/**
 * Interface class for the Observer, which forces all views to implement the
 * update method.
 * 
 * @author Mohammad Uvas
 */
public interface Observer {
	
	/**
	 * method to be implemented that reacts to the notification generally by
	 * interrogating the model object and displaying its newly updated state.
	 * 
	 * @param p_logString: String that is passed by the subject (observable)
	 *  to log a new String in log file.
	 */
	public void update(String p_logString);
	
}
