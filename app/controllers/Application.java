package controllers;


import models.Score;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import views.html.scoreScreen;

public class Application extends Controller {

	
	public static Result scoreScreen() {
        return ok(scoreScreen.render("Time Tracker", Form.form(Score.class)));
	}
    
    @Transactional
    public static Result addScore() {
        Form<Score> form = Form.form(Score.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(scoreScreen.render("Time Tracker", form));
        }

        Score score = form.get();
        JPA.em().persist(score);
        return redirect(routes.Application.scoreScreen());
    }


    
    
    @Transactional
    public static Result getScores() {
        List<Score> scores = JPA.em().createQuery("from Score order by time asc", Score.class).getResultList();
        return ok(play.libs.Json.toJson(scores));
    }
}
