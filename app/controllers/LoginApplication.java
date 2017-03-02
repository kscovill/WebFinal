package controllers;

import jpa.Login;
import jpa.Score;

import models.ScoreForm;
import models.LoginForm;
import services.LoginPersistenceService;

import views.html.loginScreen;
import views.html.createUserScreen;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;


@Named
public class LoginApplication extends Controller {

	@Inject
	private LoginPersistenceService loginPersist;
	
	public Result loginScreen() {
        return ok(loginScreen.render("Time Tracker Login", Form.form(LoginForm.class)));
	}
    
	public Result createUserScreen(){
		return ok(createUserScreen.render("Time Tracker Create User", Form.form(LoginForm.class)));
	}
	
    public Result addUser() {
        Form<LoginForm> form = Form.form(LoginForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(loginScreen.render("Time Tracker Login", form));
        }
        
        Login login = new Login();
        login.setUsername(form.get().getUsername());
        login.setPassword(form.get().getPassword());
        loginPersist.saveLogin(login);
        return redirect(routes.LoginApplication.loginScreen());
    }
    
    public Result checkUserPass(){
    	
    	
    	//FIX THIS
    	
    	return ok(views.html.scoreScreen.render("Score Screen", Form.form(ScoreForm.class)));

    }
    
    public Result getUsers() {
        List<Login> logins = loginPersist.fetchAllUsers();
        return ok(play.libs.Json.toJson(logins));
    }
}
