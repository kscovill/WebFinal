package controllers;

import models.Task;
import models.score;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import views.html.index;
import views.html.scoreScreen;

public class Application extends Controller {

	public static Result index() {
        return ok(index.render("Index", Form.form(Task.class)));
    }

	public static Result scoreScreen() {
        return ok(scoreScreen.render("High Score Tracker", Form.form(score.class)));
    }

    @Transactional
    public static Result addTask() {
        Form<Task> form = Form.form(Task.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(index.render("High Score Tracker", form));
        }

        Task task = form.get();
        JPA.em().persist(task);
        return redirect(routes.Application.index());
    }
    
    @Transactional
    public static Result addScore() {
        Form<score> form = Form.form(score.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(scoreScreen.render("High Score Tracker", form));
        }

        score score = form.get();
        JPA.em().persist(score);
        return redirect(routes.Application.scoreScreen());
    }


    @Transactional
    public static Result getTasks() {
        List<Task> tasks = JPA.em().createQuery("from Task", Task.class).getResultList();
        return ok(play.libs.Json.toJson(tasks));
    }
    
    @Transactional
    public static Result getUsername() {
        List<score> scores = JPA.em().createQuery("from score order by time asc", score.class).getResultList();
        return ok(play.libs.Json.toJson(scores));
    }
}
