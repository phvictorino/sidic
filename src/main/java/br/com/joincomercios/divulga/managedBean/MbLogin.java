package br.com.joincomercios.divulga.managedBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.joincomercios.divulga.dao.DaoUsuario;
import br.com.joincomercios.divulga.entidade.EnUsuario;
import br.com.joincomercios.divulga.utils.UtilsFaces;

@Named
@SessionScoped
public class MbLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private EnUsuario usuario;
	private String login;
	private String senha;

	@Inject
	private DaoUsuario daoUsuario;

	@PostConstruct
	public void init() {

	}

	public String fazerLogin() {

		if (login != null && !login.isEmpty()) {
			if (senha != null && !senha.isEmpty()) {
				setUsuario(daoUsuario.fazerLogin(login, senha));

				if (usuario.getCodigo() != null)
					return "privado/listaComercios.xhtml";
				else {
					UtilsFaces.showErrorDialog("Usuário ou senha inválidos.");
					return null;
				}

			} else {
				UtilsFaces.showErrorDialog("Senha não informada.");
				return null;
			}
		} else {
			UtilsFaces.showErrorDialog("Login não informado.");
			return null;
		}

	}

	public EnUsuario getUsuario() {
		return usuario;
	}

	@Produces
	@Named("usuarioLogado")
	public void setUsuario(EnUsuario usuario) {
		this.usuario = usuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
