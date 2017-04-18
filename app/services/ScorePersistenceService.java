package services;

import java.util.List;

/**
 * Super Class for the implementation class for Score
 * initializes all the methods but does not define them.
 */
import jpa.Score;

public interface ScorePersistenceService {
	/**
	 * This will persist the Score instance to the DB
	 * 
	 * @param t
	 *            is the instance of the Score class that you wish to persist
	 */
	void saveScore(Score t);

	/**
	 * Fetches all The score instances in the database and returns them in a
	 * list
	 * 
	 * @return a list of all score instances.
	 */
	List<Score> fetchAllScores();

	/**
	 * Fetches the score instance with a specific username
	 * 
	 * @param user
	 *            is a String of the username you wish to fetch the instances of
	 *            scores for
	 * @return a list of score instances
	 */
	List<Score> fetchUserScores(String user);

}