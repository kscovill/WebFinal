package services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import configs.AppConfig;
import configs.TestDataConfig;
import jpa.Login;

@ContextConfiguration(classes = { AppConfig.class, TestDataConfig.class })
public class LoginPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Inject
	private LoginPersistenceService LoginPersist;

	/**
	 * This class is for testing a normal user persisted to the DB
	 */
	@Test
	public void setNormalUser() {
		Login user = new Login();
		user.setPassword("Password");
		user.setUsername("Username");
		LoginPersist.saveLogin(user);
		assertTrue("Username should be Username", LoginPersist.fetchUser("Username").getUsername().equals("Username"));
		assertTrue("Password should be Password", LoginPersist.fetchUser("Username").getPassword().equals("Password"));
	}

	/**
	 * Tests a number user
	 */
	@Test
	public void setNumberUser() {
		Login user = new Login();
		user.setPassword("112112");
		user.setUsername("1111");
		LoginPersist.saveLogin(user);
		assertTrue("Username should be 1111", LoginPersist.fetchUser("1111").getUsername().equals("1111"));
		assertTrue("Password should be 112112", LoginPersist.fetchUser("1111").getPassword().equals("112112"));
	}

	/**
	 * test a long username
	 */
	@Test
	public void setLongUser() {
		Login user = new Login();
		user.setUsername("qqqqqqqqqqqqqqqqqqqqqasdasdadsadsadsadsa");
		user.setPassword("112112");
		try {
			LoginPersist.saveLogin(user);
			fail("Should fail to save null user name");
		} catch (IllegalArgumentException expected) {
		}
	}

	/**
	 * test a long Password
	 */
	@Test
	public void setLongPass() {

		Login user = new Login();
		user.setUsername("Username");
		user.setPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		try {
			LoginPersist.saveLogin(user);
			fail("Should not be passed");
		} catch (IllegalArgumentException expected) {

		}

	}

	/**
	 * test a username with SQL in it
	 */
	@Test
	public void setSqlUser() {
		Login user = new Login();
		user.setUsername("m;drop table Login;");
		user.setPassword("hello");
		LoginPersist.saveLogin(user);
		assertTrue("Username should be m;drop table Login",
				LoginPersist.fetchUser("m;drop table Login;").getUsername().equals("m;drop table Login;"));

	}

	/**
	 * Test a normal USrename exists
	 */
	@Test
	public void checkNormalUserEx() {
		Login user = new Login();
		user.setUsername("Username");
		user.setPassword("Password");
		LoginPersist.saveLogin(user);
		assertTrue(LoginPersist.userExists("Username") == true);

	}

	/**
	 * Tests whether a null password can be persisted
	 */
	@Test
	public void checkCrappyUser() {
		Login user = new Login();
		user.setUsername("Kyle");
		user.setPassword(null);
		try {
			LoginPersist.saveLogin(user);
			assertFalse(LoginPersist.userExists("Kyle") == true);
		} catch (IllegalArgumentException expected) {

		}

	}
	
	/**
	 * Test to make sure it doesn't always return true
	 * 
	 */
	@Test
	public void checkFakeUser() {
		Login user = new Login();
		user.setUsername("Kyle");
		user.setPassword(null);
		try {
			LoginPersist.saveLogin(user);
			assertFalse(LoginPersist.userExists("Robert") == false);
		} catch (IllegalArgumentException expected) {

		}

	}
	
	/**
	 * Tests whether userExists with SQL username
	 */
	@Test
	public void checkCrazyUser() {
		Login user = new Login();
		user.setUsername("m;drop table Login;");
		user.setPassword("Password");
		LoginPersist.saveLogin(user);
		assertTrue(LoginPersist.userExists("m;drop table Login;") == true);

	}

	/**
	 * tests to save a null login instance
	 */
	@Test
	public void saveNullUser() {
		try {
			LoginPersist.saveLogin(null);
			fail("Should not be passed");
		} catch (IllegalArgumentException expcted) {

		}

	}

	/**
	 * tries to save null user and pass
	 */
	@Test
	public void saveBlanklUser() {
		Login user = new Login();
		user.setUsername(null);
		user.setPassword(null);
		try {
			LoginPersist.saveLogin(null);
			fail("Should not be passed");
		} catch (IllegalArgumentException expected) {

		}

	}

	/**
	 * test to fetch a normal user
	 */
	@Test
	public void fetchNormalUserEx() {
		Login user = new Login();
		user.setUsername("Username");
		user.setPassword("Password");
		LoginPersist.saveLogin(user);
		assertFalse(LoginPersist.fetchUser("Username").getUsername().equals("Patrick"));
		assertTrue(LoginPersist.fetchUser("Username").getUsername().equals("Username"));

	}

	/**
	 * Tries to fetch a null password
	 */
	@Test
	public void fetchCrappyUser() {
		Login user = new Login();
		user.setUsername("Kyle");
		user.setPassword(null);
		try {
			LoginPersist.saveLogin(user);
			fail(LoginPersist.fetchUser("Kyle").getUsername());
		} catch (IllegalArgumentException expected) {

		}

	}

	/**
	 * test to fetch a null login
	 */
	@Test
	public void fetchCrazyUser() {
		try {
			LoginPersist.saveLogin(null);
			fail(LoginPersist.fetchUser(null).getUsername());
		} catch (IllegalArgumentException expected) {

		}
	}

}
