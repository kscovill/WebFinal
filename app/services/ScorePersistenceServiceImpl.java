package services;

import jpa.Login;
import jpa.Score;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class ScorePersistenceServiceImpl implements ScorePersistenceService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void saveScore(Score score) {
		em.persist(score);
	}

	@Override
	public List<Score> fetchAllScores() {
		return em.createQuery("from Score order by time asc ", Score.class).getResultList();
	}
	
	@Override
	public List<Score> fetchYourScores(String user) {
		return em.createQuery("SELECT a FROM Score a WHERE a.user = :user ORDER BY time ASC", Score.class)
				.setParameter("user", user).getResultList();
	}
}
