package controllers;

import jpa.Score;
import models.ScoreForm;
import models.GameForm;

import services.ScorePersistenceService;

import play.api.data.*;

import views.html.scoreScreen;
import views.html.loginScreen;
import views.html.gameScreen;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

@Named
public class Application extends Controller {

	private static final Logger log = LoggerFactory.getLogger(LoginApplication.class);

	@Inject
	private ScorePersistenceService scorePersist;

	public Result scoreScreen() {
		String currentUser = session("username");
		if (currentUser == null) {
			log.info("Attempted Access to scoreScreen without authorization");
			return redirect(routes.LoginApplication.loginScreen());
		}
		
		String score = session("score");
		log.info("{} at Score Screen", currentUser);
		return ok(scoreScreen.render("Time Tracker", Form.form(ScoreForm.class), currentUser, score));
	}
	
	public Result moveToScore(){
		Form<GameForm> form = Form.form(GameForm.class).bindFromRequest();
		if (form.hasErrors()) {
			log.info("There are errors in game screen");
			return badRequest(gameScreen.render("Snake Game", form));
		}
		String score = form.get().getScore();
		session("score",score);
		return redirect(routes.Application.scoreScreen());
	}

	public Result startGame(){
		if(session("score")==null || session("score").equals("")){
			session("score","0");
			log.info("Score is set to 0");
		}
		return ok(gameScreen.render("Snake Game", Form.form(GameForm.class)));
	}
	public Result addScore() {
		log.info("Attempting to add score to database");
		Form<ScoreForm> form = Form.form(ScoreForm.class).bindFromRequest();
		if (form.hasErrors()) {
			log.info("Score Form has errors");
			return badRequest(scoreScreen.render("Time Tracker", form, session("username"), session("score")));
		}
		log.info("Score succesfully persisted");
		Score score = new Score();
		score.setUser(session("username"));
		score.setTime(Double.parseDouble(session("score")));
		scorePersist.saveScore(score);
		return redirect(routes.Application.scoreScreen());
	}

	public Result getScores() {
		List<Score> scores = scorePersist.fetchAllScores();
		return ok(play.libs.Json.toJson(scores));
	}
	
	public Result getYourScores(){
		String user = session("username");
		log.debug("SESSION USERNAME {} FOUND",user);
		List<Score> yourScores = scorePersist.fetchYourScores(user);
		return ok(play.libs.Json.toJson(yourScores));
	}
}
