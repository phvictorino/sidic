package br.com.joincomercios.divulga.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.joincomercios.divulga.dao.DaoUsuario;
import br.com.joincomercios.divulga.entidade.EnUsuario;
import br.com.joincomercios.divulga.utils.UtilsFaces;

@Named
@ViewScoped
public class MbUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private EnUsuario usuario;
	private List<EnUsuario> listaUsuarios;
	private EnUsuario usuarioLogado;

	@Inject
	private DaoUsuario daoUsuario;

	@PostConstruct
	public void init() {
		usuarioLogado = (EnUsuario) UtilsFaces.getSessionMapValue("usuarioLogado");

		if (usuarioLogado != null && usuarioLogado.getLogin().equals("pedro")) {
			usuario = new EnUsuario();
			listaUsuarios = daoUsuario.listarTodos();
		} else {
			UtilsFaces.adicionarMsgErroFatal("Você não tem permissão para gerenciar usuários.");
			UtilsFaces.redirecionar("");
		}
	}

	public String salvar() {
		if (usuario != null) {
			if (validaUsuario()) {
				EnUsuario usuRetorno = daoUsuario.salvaOuAtualiza(usuario);

				if (usuRetorno != null) {
					UtilsFaces.adicionarMsgInfo("Usuário salvo com sucesso.");
					return "lista.xhtml?faces-redirect=true";
				} else {
					UtilsFaces.adicionarMsgErro("Erro ao salvar usuário.");
				}
			}
		}

		return null;
	}

	private boolean validaUsuario() {
		if (usuario.getLogin() == null || usuario.getLogin().isEmpty()) {
			UtilsFaces.adicionarMsgErro("Login não preenchido.");
			return false;
		}

		if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
			UtilsFaces.adicionarMsgErro("Senha não preenchida.");
			return false;
		}

		return true;
	}

	public String novo() {
		usuario = new EnUsuario();
		return "form.xhtml?faces-redirect=true";
	}

	public EnUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(EnUsuario usuario) {
		this.usuario = usuario;
	}

	public List<EnUsuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<EnUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public EnUsuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(EnUsuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
