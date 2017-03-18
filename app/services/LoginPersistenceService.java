package services;

import jpa.Login;

import java.util.List;

public interface LoginPersistenceService {
	void saveLogin(Login t);

	boolean userExists(String user);

	Login fetchUser(String user);

	byte[] fetchSalt(String user);

	String fetchPass(String user);
}