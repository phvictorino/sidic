package br.com.joincomercios.divulga.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.joincomercios.divulga.entidade.EnComercio;

public class DaoComercio extends GenericDAO<EnComercio> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<EnComercio> listarComFiltros(EnComercio obj) {

		String jpql = "SELECT c FROM EnComercio c WHERE 1=1 ";

		if (obj.getCnpj() != null && !obj.getCnpj().isEmpty()) {
			jpql = jpql + "AND UPPER(c.cnpj) LIKE :cnpj ";
		}

		if (obj.getEndereco() != null && !obj.getEndereco().isEmpty()) {
			jpql = jpql + "AND UPPER(c.endereco) LIKE :endereco ";
		}

		if (obj.getTelefone() != null && !obj.getTelefone().isEmpty()) {
			jpql = jpql + "AND UPPER(c.telefone) LIKE :telefone ";
		}

		if (obj.getNome() != null && !obj.getNome().isEmpty()) {
			jpql = jpql + "AND UPPER(c.nome) LIKE :nome ";
		}

		jpql = jpql + "ORDER BY c.codigo ASC";

		Query query = em.createQuery(jpql.toString());

		if (obj.getCnpj() != null && !obj.getCnpj().isEmpty()) {
			query.setParameter("cnpj", "%" + obj.getCnpj().toUpperCase() + "%");
		}

		if (obj.getEndereco() != null && !obj.getEndereco().isEmpty()) {
			query.setParameter("endereco", "%" + obj.getEndereco().toUpperCase() + "%");
		}

		if (obj.getTelefone() != null && !obj.getTelefone().isEmpty()) {
			query.setParameter("telefone", "%" + obj.getTelefone().toUpperCase() + "%");
		}

		if (obj.getNome() != null && !obj.getNome().isEmpty()) {
			query.setParameter("nome", "%" + obj.getNome().toUpperCase() + "%");
		}

		try {
			return query.getResultList();
		} catch (NoResultException n) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
