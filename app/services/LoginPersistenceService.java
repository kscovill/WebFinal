package services;

import jpa.Login;

import java.util.List;

public interface LoginPersistenceService {
    void saveLogin(Login t);

    List<Login> fetchPass();
    
    List<Login> fetchSalt();
    
    boolean userExists(String user);

	Login fetchUser(String user);
}