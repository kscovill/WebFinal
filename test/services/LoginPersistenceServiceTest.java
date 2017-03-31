package services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import configs.AppConfig;
import configs.TestDataConfig;

import jpa.Login;
import org.junit.Test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

@ContextConfiguration(classes = { AppConfig.class, TestDataConfig.class })
public class LoginPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	 @Inject
	    private LoginPersistenceService LoginPersist;
	
	@Test
	public void setNormalUser() {
		Login user = new Login();
		user.setPassword("Password");
		user.setUsername("Username");
		LoginPersist.saveLogin(user);
		assertTrue("Username should be Username", LoginPersist.fetchUser("Username").getUsername().equals("Username"));
		assertTrue("Password should be Password", LoginPersist.fetchUser("Username").getPassword().equals("Password"));
	}
	
	@Test
	public void setNumberUser() {
		Login user = new Login();
		user.setPassword("112112");
		user.setUsername("1111");
		LoginPersist.saveLogin(user);
		assertTrue("Username should be 1111", LoginPersist.fetchUser("Username").getUsername().equals("1111"));
		assertTrue("Password should be 112112", LoginPersist.fetchUser("Username").getPassword().equals("112112"));
	}
	
	@Test
	public void setLongUser() {
		Login user = new Login();
		user.setUsername("qqqqqqqqqqqqqqqqqqqqqasdasdadsadsadsadsa");
		user.setPassword("112112");
		LoginPersist.saveLogin(user);
		assertTrue("Long Name", LoginPersist.fetchUser("Username").getUsername().equals("qqqqqqqqqqqqqqqqqqqqqasdasdadsadsadsadsa"));
		
	}

	@Test
	public void setLongPass() {
		Login user = new Login();
		user.setUsername("Username");
		user.setPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		LoginPersist.saveLogin(user);
		assertTrue("Long Password", LoginPersist.fetchUser("Username").getPassword().equals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	
	}
	
	@Test
	public void setSqlUser() {
		Login user = new Login();
		user.setUsername(";drop table user;");
		user.setPassword(";delete from user where username = \';drop table from user\'");
		LoginPersist.saveLogin(user);
		assertTrue("Should still be in database" ,LoginPersist.fetchUser("Username").getUsername().equals(";drop table from user"));
		
	}

}
