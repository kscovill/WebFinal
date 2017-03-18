package services;

import jpa.Login;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class LoginPersistenceServiceImpl implements LoginPersistenceService {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void saveLogin(Login login) {
		em.persist(login);
	}

	@Override
	public boolean userExists(String user) {
		Login login = fetchUser(user);
		return (login != null);
	}

	// From theButton

	@Override
	public Login fetchUser(String user) {
		List<Login> list = em.createQuery("SELECT a FROM Login a WHERE a.username = :user", Login.class)
				.setParameter("user", user).getResultList();

		if (list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public byte[] fetchSalt(String user) {
		List<Login> list = em.createQuery("SELECT a FROM Login a WHERE a.username = :user", Login.class)
				.setParameter("user", user).getResultList();
		if (list.size() > 0) {

			return list.get(0).getSalt();
		}

		return null;
	}

	@Override
	public String fetchPass(String user) {
		List<Login> list = em.createQuery("SELECT a FROM Login a WHERE a.username = :user", Login.class)
				.setParameter("user", user).getResultList();
		if (list.size() > 0) {
			return list.get(0).getPassword();
		}

		return "";
	}
}
