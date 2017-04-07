package models;

/**
 * Form setup for our game form which only consists of score. You can get or Set the score in the methods.
 */
import play.data.validation.Constraints.Required;

public class GameForm {

	@Required
	private String Score;

	public String getScore() {
		return Score;
	}

	public void setScore(String score) {
		this.Score = score;
	}

}