package models;

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