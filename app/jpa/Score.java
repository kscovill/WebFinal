package jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Setup for our Score persistence. includes get and set methods for id, user,
 * and score. These methods get or set each variable
 * 
 * @author Kyle
 *
 */

@Entity
public class Score {

	@Id
	@GeneratedValue
	private Long id;

	private String user;

	private double score;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}