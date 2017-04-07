package jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controllers.LoginApplication;

/**
 * Setup for our Login persistence. includes get and set methods for username,
 * password, and salt. These methods get or set each variable
 * 
 * @author Kyle
 *
 */
@Entity
public class Login {

	private static final Logger log = LoggerFactory.getLogger(LoginApplication.class);

	@Id
	private String username;

	private String password;

	private byte[] salt;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username==null){
			
		}else{
			if (username.length() > 2 && username.length() < 21) {
				this.username = username;
			} else {
				this.username = null;
			}
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password != null && (password.length() > 2 && password.length() < 21 || password.length() > 100)) {
			this.password = password;
		} else {
			log.error("PASSWORD IS NULL");
			this.password = null;
		}
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public byte[] getSalt() {
		return salt;
	}

}