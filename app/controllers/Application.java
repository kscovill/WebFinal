package controllers;

import jpa.Score;
import models.ScoreForm;

import services.ScorePersistenceService;

import views.html.scoreScreen;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;


@Named
public class Application extends Controller {

	@Inject
	private ScorePersistenceService scorePersist;
	
	public Result scoreScreen() {
        return ok(scoreScreen.render("Time Tracker", Form.form(ScoreForm.class),""));
	}
    
	
    public Result addScore() {
        Form<ScoreForm> form = Form.form(ScoreForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(scoreScreen.render("Time Tracker", form, ""));
        }
        
        Score score = new Score();
        score.setUser(form.get().getUser());
        score.setTime(form.get().getTime());
        scorePersist.saveScore(score);
        return redirect(routes.Application.scoreScreen());
    }

    
    public Result getScores() {
        List<Score> scores = scorePersist.fetchAllScores();
        return ok(play.libs.Json.toJson(scores));
    }
}
