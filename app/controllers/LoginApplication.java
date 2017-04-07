package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import views.html.scoreScreen;
import views.html.loginScreen;
import views.html.createUserScreen;
import views.html.gameScreen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the controller for all things related to the login. 
 * From creating the user, to logging in, to persisting and hashing the 
 * Logins.
 */
import jpa.Login;
import models.LoginForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.LoginPersistenceService;

@Named
public class LoginApplication extends Controller {

	private static final Logger log = LoggerFactory.getLogger(LoginApplication.class);
	private static final Random RANDOM = new SecureRandom();
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;

	@Inject
	private LoginPersistenceService loginPersist;

	/**
	 * Takes you to the login screen and clears all sessions
	 */
	public Result loginScreen() {
		log.info("Somebody is at the login screen");
		session().clear();
		log.debug("Session is cleared");
		return ok(loginScreen.render("Snake App Login", Form.form(LoginForm.class)));
	}

	/**
	 * takes you to the create user screen
	 * 
	 */
	public Result createUserScreen() {
		log.info("Someone is at the create user screen");
		return ok(createUserScreen.render("Snake App Create User", Form.form(LoginForm.class)));
	}

	/**
	 * This will take the data from the form and check to see if the user
	 * 
	 * already exists in the database, if not it will hash the password and
	 * persist the user to the database
	 */
	public Result addUser() {

		log.debug("Attempting to add a new user");

		Form<LoginForm> form = Form.form(LoginForm.class).bindFromRequest();

		if (form.hasErrors()) {
			log.info("There are errors");
			return badRequest(createUserScreen.render("Snake App Create User", form));
		}

		String username = form.get().getUsername();

		Login login = new Login();

		// From theButton
		if (loginPersist.userExists(username)) {
			log.info("username '{}' already exists, can't create account", username);
			form.reject("username", "That username already exists, please enter a different username");
			return badRequest(createUserScreen.render("Snake App Create User", form));
		}
		try {
			byte[] newSalt = getSalt();
			log.debug("Attempting to hash password");
			login.setPassword(securePassword(form.get().getPassword(), newSalt));
			log.debug("Password added");
			login.setUsername(username);
			login.setSalt(newSalt);
			loginPersist.saveLogin(login);
			log.info("User '{}' added", username);
			session("username",username);
			return redirect(routes.Application.startGame());
		} catch (NoSuchAlgorithmException e) {
			log.info("Password Hashing Failed");
			return redirect(routes.LoginApplication.createUserScreen());
		}
	}

	// From StackOverFlow
	/**
	 * This will hash the password using a SHA-512 encryption
	 * 
	 * @param passwordToHash
	 *            is the password which is to be hashed.
	 * @param salt
	 *            is the salt used to hash the passwordToHash
	 * @return returns the generated password
	 */
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

	/**
	 * Generate a random salt for each individual user.
	 * 
	 * @return will return the new random salt
	 * @throws NoSuchAlgorithmException
	 */

	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	/**
	 * This will fetch the user from the database and hash the password given by
	 * the user to match up with the database info.
	 */
	public Result checkUserPass() {

		Form<LoginForm> form = Form.form(LoginForm.class).bindFromRequest();
		if (form.hasErrors()) {
			log.info("There are errors");
			return badRequest(loginScreen.render("Snake App Login", form));
		}
		String username = form.get().getUsername();

		log.info("Checking if {} exists", username);
		if (loginPersist.userExists(form.get().getUsername())) {
			log.info("{} exists in the database!", username);
			session("username", username.toUpperCase());

			log.info("Checking Password for {}", username);
			if (securePassword(form.get().getPassword(), loginPersist.fetchUser(username).getSalt())
					.equals(loginPersist.fetchUser(username).getPassword())) {
				log.info("Password exists, access granted");
				return redirect(controllers.routes.Application.startGame());
			}
			log.info("Password for {} is incorrect");

		} else {
			log.info(" {} does not exist in the database.", username);
		}
		form.reject("username", "That Username and/or Password does not exist.");
		form.reject("password", "");
		return badRequest(loginScreen.render("Snake App Login", form));

	}
}
