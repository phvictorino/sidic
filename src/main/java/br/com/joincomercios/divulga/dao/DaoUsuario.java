package br.com.joincomercios.divulga.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.joincomercios.divulga.entidade.EnUsuario;

public class DaoUsuario extends GenericDAO<EnUsuario> {

	private static final long serialVersionUID = 1L;

	public EnUsuario fazerLogin(String login, String senha) {

		Query query = em.createQuery("SELECT u FROM EnUsuario u WHERE u.login = :login AND u.senha = :senha");
		query.setParameter("login", login);
		query.setParameter("senha", senha);

		try {
			return (EnUsuario) query.getSingleResult();
		} catch (NoResultException n) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
