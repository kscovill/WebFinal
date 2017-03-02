package jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Score {

    @Id
    @GeneratedValue
    private Long id;

    private String user;
    
    
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

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}