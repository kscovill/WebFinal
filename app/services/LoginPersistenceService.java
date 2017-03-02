package services;

import jpa.Login;

import java.util.List;

public interface LoginPersistenceService {
    void saveLogin(Login t);

    List<Login> fetchAllUsers();
}