package services;

/**
 * implementation method for ScorePersistence that actually makes the methods work.
 */
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import jpa.Score;

@Named
public class ScorePersistenceServiceImpl implements ScorePersistenceService {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Persists the given score to the database as long as nothing passed is
	 * null
	 */
	@Transactional
	@Override
	public void saveScore(Score score) {
		if (score == null || score.getUser() == null) {
			throw new IllegalArgumentException();
		} else {
			em.persist(score);
		}
	}

	/**
	 * Fetches the top 30 scores from the database
	 */
	@Override
	public List<Score> fetchAllScores() {
		return em.createQuery("SELECT a FROM Score a ORDER BY score DESC", Score.class).setMaxResults(30)
				.getResultList();
	}

	/**
	 * Fetches top 50 scores attatched to a username
	 */
	@Override
	public List<Score> fetchYourScores(String user) {
		return em.createQuery("SELECT a FROM Score a WHERE a.user = :user ORDER BY score DESC", Score.class)
				.setParameter("user", user).setMaxResults(50).getResultList();
	}
}
