package controllers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import views.html.scoreScreen;
import views.html.loginScreen;
import views.html.createUserScreen;
import views.html.gameScreen;

import jpa.Score;
import models.GameForm;
import models.ScoreForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.ScorePersistenceService;

@Named
public class Application extends Controller {

	private static final Logger log = LoggerFactory.getLogger(LoginApplication.class);

	/**
	 * 
	 * Application class that controls score and game based actions such as
	 * moving pages, persisting data, grabbing data from the database, or
	 * resetting game variables.
	 * 
	 */

	@Inject
	private ScorePersistenceService scorePersist;

	/**
	 * Checks if user is logged in, yes take to highscore page, no take to login
	 * page.
	 */
	public Result scoreScreen() {
		String currentUser = session("username");
		if (currentUser == null) {
			log.info("Attempted Access to scoreScreen without authorization");
			return redirect(routes.LoginApplication.loginScreen());
		}

		String score = session("score");
		log.info("{} at Score Screen", currentUser);
		return ok(scoreScreen.render("Snake Scoreboard", Form.form(ScoreForm.class), currentUser, score));
	}

	/**
	 * takes score from the game screen and sends info and user to highscore
	 * page when called in routes. Checks for
	 * 
	 * @return
	 * 		returns a redirect to the scoreScreen if form has no errors
	 */

	public Result moveToScore() {
		Form<GameForm> form = Form.form(GameForm.class).bindFromRequest();
		if (form.hasErrors()) {
			log.info("There are errors in game screen");
			return badRequest(gameScreen.render("Snake Game", form));
		}
		String score = form.get().getScore();
		session("score", score);
		return redirect(routes.Application.scoreScreen());
	}

	/**
	 * Sets variables to start game every time it is called. Game used with
	 * credit to Copyright © Patrick Gillespie, http://patorjk.com
	 */

	public Result startGame() {
		if (session("score") == null || session("score").equals("")) {
			session("score", "0");
			log.info("Score is set to 0");
		}
		return ok(gameScreen.render("Snake Game", Form.form(GameForm.class)));
	}

	/**
	 * Adds the score from the textbox in html to the database This checks for
	 * errors in the form and will persist the score if no errors are found
	 */
	public Result addScore() {
		log.info("Attempting to add score to database");
		Form<ScoreForm> form = Form.form(ScoreForm.class).bindFromRequest();
		if (form.hasErrors()) {
			log.info("Score Form has errors");
			return badRequest(scoreScreen.render("Snake Scoreboard", form, session("username"), session("score")));
		}
		log.info("Score succesfully persisted");
		Score score = new Score();
		score.setUser(session("username"));
		score.setScore(Double.parseDouble(session("score")));
		scorePersist.saveScore(score);
		return redirect(routes.Application.scoreScreen());
	}

	/**
	 * Returns all scores in database with usernames attatched
	 * 
	 */
	public Result getScores() {
		List<Score> scores = scorePersist.fetchAllScores();
		return ok(play.libs.Json.toJson(scores));
	}

	/**
	 * Fetches all scores associated with a specific username.
	 * 
	 */
	public Result getUserScores() {
		String user = session("username");
		log.debug("SESSION USERNAME {} FOUND", user);
		List<Score> userScores = scorePersist.fetchUserScores(user);
		return ok(play.libs.Json.toJson(userScores));
	}
}
