package controllers;

import jpa.Score;
import models.ScoreForm;

import services.ScorePersistenceService;

import play.api.data.*;

import views.html.scoreScreen;
import views.html.loginScreen;

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
			log.debug("Attempted Access to scoreScreen without authorization");
			return redirect(routes.LoginApplication.loginScreen());
		}
		log.info("{} at Score Screen", currentUser);
		return ok(scoreScreen.render("Time Tracker", Form.form(ScoreForm.class), currentUser));
	}

	public Result addScore() {
		log.info("Attempting to add score to database");
		Form<ScoreForm> form = Form.form(ScoreForm.class).bindFromRequest();
		if (form.hasErrors()) {
			log.info("Score Form has errors");
			return badRequest(scoreScreen.render("Time Tracker", form, session("username")));
		}
		log.info("Score succesfully persisted");
		Score score = new Score();
		score.setUser(session("username"));
		score.setTime(form.get().getTime());
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
