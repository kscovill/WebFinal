package models;

import play.data.validation.Constraints.Required;

public class ScoreForm {

	private String User;

	@Required
	private double time;

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		this.User = user;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

}