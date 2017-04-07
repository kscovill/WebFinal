package services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import configs.AppConfig;
import configs.TestDataConfig;
import jpa.Score;

/**
 * Test Service that tests each method in the Score Persistence class
 * 
 * @author Kyle
 *
 */
@ContextConfiguration(classes = { AppConfig.class, TestDataConfig.class })
public class ScorePeristenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Inject
	private ScorePersistenceService ScorePer;

	/**
	 * test a normal score is persisted
	 */
	@Test
	public void saveScoreTest() {
		Score score = new Score();
		score.setScore(25);
		score.setUser("Patrick");
		ScorePer.saveScore(score);
		List<Score> allScores = ScorePer.fetchAllScores();
		assertTrue("Should only have 1 score",
				allScores.get(0).getScore() == 25 && allScores.get(0).getUser().equals("Patrick"));

	}

	/**
	 * Test whether get a specific users score works
	 */
	@Test
	public void getUserScores() {
		Score score = new Score();
		score.setScore(252);
		score.setUser("Patrick");
		ScorePer.saveScore(score);
		Score score2 = new Score();
		score2.setScore(253);
		score2.setUser("Patrick");
		ScorePer.saveScore(score2);
		Score score3 = new Score();
		score3.setScore(25);
		score3.setUser("Pat");
		ScorePer.saveScore(score3);
		List<Score> patrickScores = ScorePer.fetchYourScores("Patrick");
		assertTrue("Should be 2 scores", patrickScores.size() == 2);

	}

	/**
	 * Test to get all scores in the database
	 */
	@Test
	public void getAllScores() {
		Score score = new Score();
		score.setScore(25);
		score.setUser("Patrick");
		ScorePer.saveScore(score);
		Score score2 = new Score();
		score2.setScore(225);
		score2.setUser("Pat");
		ScorePer.saveScore(score2);
		List<Score> allScores = ScorePer.fetchAllScores();
		assertTrue("Should be 2 scores", allScores.size() == 2);
	}

}
