package controllers;

import jpa.Login;
import jpa.Score;

import models.ScoreForm;
import models.LoginForm;
import services.LoginPersistenceService;

import views.html.loginScreen;
import views.html.createUserScreen;
import views.html.scoreScreen;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
		session().remove("username");
		log.debug("Username Session ended");
		return ok(loginScreen.render("Time Tracker Login", Form.form(LoginForm.class)));
	}

	public Result createUserScreen() {
		log.info("Someone is at the create user screen");
		return ok(createUserScreen.render("Time Tracker Log In", Form.form(LoginForm.class)));
	}

	public Result addUser() {

		log.debug("Attempting to add a new user");

		Form<LoginForm> form = Form.form(LoginForm.class).bindFromRequest();

		if (form.hasErrors()) {
			log.info("There are errors");
			return badRequest(createUserScreen.render("Time Tracker Login", form));
		}

		String username = form.get().getUsername();

		Login login = new Login();

		// From theButton
		if (loginPersist.userExists(username)) {
			log.info("username '{}' already exists, can't create account", username);
			form.reject("username", "That username already exists, please enter a different username");
			return badRequest(createUserScreen.render("Time Tracker Log In", form));
		}
		try {
			byte[] newSalt = getSalt();
			log.debug("Attempting to hash password");
			login.setPassword(securePassword(form.get().getPassword(), newSalt));
			log.debug("Password added");
			log.info("User '{}' added", username);
			login.setUsername(username);
			login.setSalt(newSalt);
			loginPersist.saveLogin(login);
			return redirect(routes.LoginApplication.loginScreen());
		} catch (NoSuchAlgorithmException e) {
			log.info("Password Hashing Failed");
			return redirect(routes.LoginApplication.loginScreen());
		}

	}

	// From StackOverFlow 
	
	private static String securePassword(String passwordToHash, byte[] salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	// Add salt
	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	public Result checkUserPass() {

		Form<LoginForm> form = Form.form(LoginForm.class).bindFromRequest();
		if (form.hasErrors()) {
			log.info("There are errors");
			return badRequest(loginScreen.render("Time Tracker Login", form));
		}
		String username = form.get().getUsername();

		log.info("Checking if {} exists", username);
		if (loginPersist.userExists(form.get().getUsername())) {
			log.info("{} exists in the database!", username);
			session("username", username);

			log.info("Checking Password for {}", username);
			if (securePassword(form.get().getPassword(), loginPersist.fetchSalt(username))
					.equals(loginPersist.fetchPass(username))) {
				log.info("Password exists, access granted");
				return redirect(controllers.routes.Application.scoreScreen());
			}
			log.info("Password for {} is incorrect");

		} else {
			log.info(" {} does not exist in the database.", username);
		}
		form.reject("username", "That Username and/or Password does not exist.");
		form.reject("password", "");
		return badRequest(loginScreen.render("Time Tracker Login", form));

	}
}
