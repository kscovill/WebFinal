package models;

/**
 * Form Design for Score
 * includes get and set methods for user and Score. They are capitalized for issues that came up
 * elsewhere in the program. 
 * for set methods, you pass in the String for usre or Double for score that you wish to set them as
 */
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