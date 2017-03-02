package models;

import play.data.validation.Constraints.Required;


public class ScoreForm {

    @Required
    private String user;
    
    @Required
    private double time;

    public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

}