package models;

/**
 * Form design for Login, requires both password and username to be required and have between 3 and 20 characters.
 * Get Methods will Return the given variable.
 */
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class LoginForm {

	@Required
	@MinLength(3)
	@MaxLength(20)
	private String username;

	@MinLength(3)
	@MaxLength(20)
	@Required
	private String password;

	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username in the instance.
	 * 
	 * @param username
	 *            is passed as the username String you wish to set the username
	 *            as.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password in the instance.
	 * 
	 * @param password
	 *            is passed as the password you wish to set the password as in
	 *            the instance.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}