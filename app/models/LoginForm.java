package models;

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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}