package controllers;


import jpa.Login;
import jpa.Score;

import models.ScoreForm;
import models.LoginForm;
import services.LoginPersistenceService;

import views.html.loginScreen;
import views.html.createUserScreen;
import views.html.scoreScreen;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Named
public class LoginApplication extends Controller {

	private static final Logger log = LoggerFactory.getLogger(LoginApplication.class);
	private static final Random RANDOM = new SecureRandom();
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;
	
	@Inject
	private LoginPersistenceService loginPersist;
	
	
	public Result loginScreen() {
		log.info("Somebody is at the login screen");
        return ok(loginScreen.render("Time Tracker Login", Form.form(LoginForm.class)));
	}
    
	public Result createUserScreen(){
		log.info("Someone is at the create user screen");
		return ok(createUserScreen.render("Time Tracker Create User", Form.form(LoginForm.class)));
	}
	
    public Result addUser() {
    	
    	
    	log.debug("Attempting to add a new user");
    	
        Form<LoginForm> form = Form.form(LoginForm.class).bindFromRequest();
        
        if (form.hasErrors()) {
        	log.info("There are errors");
            return badRequest(createUserScreen.render("Time Tracker Create User", form));
        }
        
        
        String username = form.get().getUsername();
        
        Login login = new Login();
        
        // From theButton
        if (loginPersist.userExists(username)) {
            log.info("username '{}' already exists, can't create account", username);
            form.reject("username", "That username already exists, please enter a different username");
            return badRequest(createUserScreen.render("Time Tracker Create User", form));
        }
        
        
        log.info("User '{}' added",username);
        login.setUsername(username);
//        byte[] salt = getNextSalt();
//        login.setSalt(salt);
//        char[] pass = form.get().getPassword().toCharArray();
//        String finalPass =hash(pass, salt).toString();
//        login.setPassword(finalPass);
        loginPersist.saveLogin(login);
        return redirect(routes.LoginApplication.loginScreen());
    }
    
    // FROM StackOverFlow by assylias
    
    public static byte[] getNextSalt(){
    	byte[] salt = new byte[16];
    	RANDOM.nextBytes(salt);
    	return salt;
    }
    
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
          SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
          return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
          throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
          spec.clearPassword();
        }
      }

    public static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
          if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
      }
    
    public Result checkUserPass(){
    	Form<LoginForm> form = Form.form(LoginForm.class).bindFromRequest();
        if (form.hasErrors()) {
        	log.info("There are errors");
            return badRequest(loginScreen.render("Time Tracker Login", form));
        }
        String username = form.get().getUsername();
        log.info("Checking if {} exists", username);
        if(loginPersist.userExists(form.get().getUsername())){
        	log.info("{} exists in the database!", username);
        	session("username",username);
      
        	return redirect(controllers.routes.Application.scoreScreen());
        }
    	
        log.info(" {} does not exist in the database.", username);
        form.reject("username","That Username does not exist.");
        return badRequest(loginScreen.render("Time Tracker Login", form));
 //   	String pass = form.get().getPassword();
    	
//    	
//    	byte[] salt = loginPersist.fetchSalt().getBytes();
//    	System.out.println(salt);
//    	char[] password = pass.toCharArray();
//    	System.out.println(password);
//    	if(isExpectedPassword(password,salt,hash)){
//    		System.out.println("PASS");
//    		return ok(views.html.scoreScreen.render("Score Screen", Form.form(ScoreForm.class),user));
//    	}else{
//    		System.out.println("FAIL");
//    		return ok(views.html.scoreScreen.render("Score Screen", Form.form(ScoreForm.class),user));
//        	
//    		//return badRequest(loginScreen.render("Time Tracker Login", form));
//    	}
    	
    	
    }
}    

