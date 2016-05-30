package br.com.joincomercios.divulga.managedBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.joincomercios.divulga.dao.DaoUsuario;
import br.com.joincomercios.divulga.entidade.EnUsuario;
import br.com.joincomercios.divulga.utils.UtilsFaces;

@Named
@ViewScoped
public class MbLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private EnUsuario usuario;
	private String login;
	private String senha;

	@Inject
	private DaoUsuario daoUsuario;

	@PostConstruct
	public void init() {

		if (UtilsFaces.getSessionMapValue("usuarioLogado") == null) {
			redirecionaLogin();
		} else {
			redirecionaLista();
		}

	}

	public String redirecionaLogin() {
		return "login.xhtml?faces-redirect=true";
	}

	public String redirecionaLista() {
		return "/comercio/listaGerencia.xhtml?faces-redirect=true";
	}

	public String fazerLogin() {

		if (login != null && !login.isEmpty()) {
			if (senha != null && !senha.isEmpty()) {
				setUsuario(daoUsuario.fazerLogin(login, senha));
				if (usuario != null) {
					UtilsFaces.adicionarMsgInfo("Logado com sucesso.");
					UtilsFaces.setSessionMapValue("usuarioLogado", usuario);
					return "/privado/comercio/listaGerencia.xhtml?faces-redirect=true";
				} else {
					UtilsFaces.adicionarMsgErro("Usuário ou senha inválidos.");
				}

			} else {
				UtilsFaces.showErrorDialog("Senha não informada.");
			}
		} else {
			UtilsFaces.showErrorDialog("Login não informado.");
		}
		return null;

	}

	public EnUsuario getUsuario() {
		return usuario;
	}

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
