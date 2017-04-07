package services;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controllers.LoginApplication;
/**
 * Actual code to the LoginPersistenceService to make them work
 */
import jpa.Login;

@Named
public class LoginPersistenceServiceImpl implements LoginPersistenceService {

	@PersistenceContext
	private EntityManager em;

	private static final Logger log = LoggerFactory.getLogger(LoginApplication.class);

	/**
	 * Persists the login info from create user to the database
	 */
	@Transactional
	@Override
	public void saveLogin(Login login) {
		if (login == null || login.getUsername() == null || login.getPassword() == null) {
			throw new IllegalArgumentException("Contents are null");
		} else {
			em.persist(login);
		}
	}

	/**
	 * Checks the database to see if a user is there
	 */
	@Override
	public boolean userExists(String user) {
		Login login = fetchUser(user);
		return (login != null);
	}

	// From theButton
	/**
	 * This will fetch all Login instances in the database with a given game
	 */
	@Override
	public Login fetchUser(String user) {
		List<Login> list = em.createQuery("SELECT a FROM Login a WHERE a.username = :user", Login.class)
				.setParameter("user", user).getResultList();

		if (list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

}
