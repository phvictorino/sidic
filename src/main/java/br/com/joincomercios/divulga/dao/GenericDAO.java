package br.com.joincomercios.divulga.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;

@Transactional
public class GenericDAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "postgresql")
	protected EntityManager em;

	public Session getSession() {
		Session session = (Session) em.getDelegate();
		// Session session = (Session)
		// pegaEntityManagerEmUso().getEntityManagerFactory();
		return session;
	}

	public Criteria getCriteria() {
		Criteria criteria = getSession().createCriteria(getTypeClass());
		return criteria;
	}

	public T salvaOuAtualiza(T obj) {
		try {
			return em.merge(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public T buscarPorId(Integer id) {
		try {
			return em.find(getTypeClass(), id);
		} catch (NoResultException n) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> listarTodos() {
		Query query = em.createQuery("SELECT o FROM " + getTypeClass().getName() + " o ORDER BY o.codigo");

		try {
			return query.getResultList();
		} catch (NoResultException n) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void persistir(T obj) {
		try {
			em.persist(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remover(T obj) {
		try {
			T objSaved = em.merge(obj);
			em.remove(objSaved);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Class<T> getTypeClass() {
		Type type = getClass().getGenericSuperclass();

		while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != GenericDAO.class) {
			if (type instanceof ParameterizedType) {
				type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
			} else {
				type = ((Class<?>) type).getGenericSuperclass();
			}
		}

		return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
	}

}
