package br.com.joincomercios.divulga.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.joincomercios.divulga.dao.DaoComercio;
import br.com.joincomercios.divulga.dao.DaoUsuario;
import br.com.joincomercios.divulga.entidade.EnComercio;
import br.com.joincomercios.divulga.entidade.EnUsuario;
import br.com.joincomercios.divulga.utils.UtilsFaces;

@Named
@ViewScoped
public class MbComercioGerencia implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<EnComercio> listaComercios;
	private EnComercio comercio;
	private EnUsuario usuarioLogado;

	@Inject
	private DaoComercio daoComercio;

	@Inject
	private DaoUsuario daoUsuario;

	@PostConstruct
	public void init() {
		usuarioLogado = (EnUsuario) UtilsFaces.getSessionMapValue("usuarioLogado");

		if (usuarioLogado != null) {
			listaComercios = daoUsuario.buscarPorId(usuarioLogado.getCodigo()).getListaComercios();

			if (UtilsFaces.getSessionMapValue("comercioEditar") != null) {
				comercio = (EnComercio) UtilsFaces.getSessionMapValue("comercioEditar");
				UtilsFaces.removeSessionMapValue("comercioEditar");
			} else {
				comercio = new EnComercio();
			}
		} else {
			UtilsFaces.adicionarMsgErroFatal("Usuário não logado.");
			UtilsFaces.redirecionar("/comercio/lista.xhtml?faces-redirect=true");
		}
	}

	public String salvar() {
		if (validaParaSalvar()) {

			EnUsuario usuarioLogado = (EnUsuario) UtilsFaces.getSessionMapValue("usuarioLogado");

			if (usuarioLogado != null) {
				comercio.setUsuario(usuarioLogado);
			} else {
				UtilsFaces.adicionarMsgErroFatal("Erro interno.");
				return null;
			}

			EnComercio objSalvo = daoComercio.salvaOuAtualiza(comercio);

			if (objSalvo != null && objSalvo.getCodigo() != null) {
				UtilsFaces.adicionarMsgInfo("Comércio salvo com sucesso.");
				return "listaGerencia.xhtml?faces-redirect=true";
			} else {
				UtilsFaces.adicionarMsgErro("Erro ao salvar comércio.");
			}

		}

		return null;
	}

	public String excluir(EnComercio comercio) {
		daoComercio.remover(comercio);
		UtilsFaces.adicionarMsgInfo("Comércio removido com sucesso!");
		return "listaGerencia.xhtml";
	}

	private boolean validaParaSalvar() {
		if (comercio.getCnpj() == null || comercio.getCnpj().isEmpty()) {
			UtilsFaces.adicionarMsgErro("CNPJ não preenchido.");
			return false;
		}

		if (comercio.getNome() == null || comercio.getNome().isEmpty()) {
			UtilsFaces.adicionarMsgErro("Nome não preenchido.");
			return false;
		}

		if (comercio.getTelefone() == null || comercio.getTelefone().isEmpty()) {
			UtilsFaces.adicionarMsgErro("Telefone não preenchido.");
			return false;
		}

		return true;
	}

	public String editar(EnComercio comercio) {
		UtilsFaces.setSessionMapValue("comercioEditar", comercio);
		return "form.xhtml?faces-redirect=true";
	}

	public List<EnComercio> getListaComercios() {
		return listaComercios;
	}

	public void setListaComercios(List<EnComercio> listaComercios) {
		this.listaComercios = listaComercios;
	}

	public EnComercio getComercio() {
		return comercio;
	}

	public void setComercio(EnComercio comercio) {
		this.comercio = comercio;
	}

}
