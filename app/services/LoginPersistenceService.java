package services;

/**
 * Super Class for the implementation class
 * initializes all the methods but does not define them.
 */
import jpa.Login;

public interface LoginPersistenceService {
	/**
	 * Saves the Login
	 * 
	 * @param t
	 *            is the Login instance you wish to persist to the database
	 */
	void saveLogin(Login t);

	/**
	 * Checks the DB to see if the user exists.
	 * 
	 * @param user
	 *            is the String of the username you wish to search for.
	 * @return
	 * 			  if it exists, the boolean is true
	 */
	boolean userExists(String user);

	/**
	 * Fetches the Login instance with the username 'user'
	 * 
	 * @param user
	 *            is the String name of the username of the instance you wish to
	 *            find.
	 * @return
	 */
	Login fetchUser(String user);

}