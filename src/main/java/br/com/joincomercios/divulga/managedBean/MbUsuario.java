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
public class MbUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private EnUsuario usuario;

	@Inject
	private DaoUsuario daoUsuario;

	@PostConstruct
	public void init() {
		usuario = new EnUsuario();
	}

	public void salvar() {
		if (usuario != null) {
			if (validaUsuario()) {
				EnUsuario usuRetorno = daoUsuario.salvaOuAtualiza(usuario);

				if (usuRetorno != null) {
					UtilsFaces.adicionarMsgInfo("Usuário salvo com sucesso.");
					UtilsFaces.redirecionar("privado/usuario/listar.xhtml");
				} else {
					UtilsFaces.adicionarMsgErro("Erro ao salvar usuário.");
				}
			}
		}
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

	public EnUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(EnUsuario usuario) {
		this.usuario = usuario;
	}

}
