package models;

import play.data.validation.Constraints.Required;

public class ScoreForm {

	private String User;

	@Required
	private double Score;

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		this.User = user;
	}

	public double getTime() {
		return Score;
	}

	public void setTime(double score) {
		this.Score = score;
	}

}