package br.com.joincomercios.divulga.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.joincomercios.divulga.dao.DaoComercio;
import br.com.joincomercios.divulga.entidade.EnComercio;

@Named
@ViewScoped
public class MbComercio implements Serializable {

	private static final long serialVersionUID = 1L;

	private EnComercio comercioParaBusca;
	private List<EnComercio> listaComercios;
	private EnComercio comercioInfo;

	@Inject
	private DaoComercio daoComercio;

	@PostConstruct
	public void init() {
		comercioParaBusca = new EnComercio();
		listaComercios = daoComercio.listarTodos();
		comercioInfo = new EnComercio();
	}

	public void abrirInfo(EnComercio comercio) {
		comercioInfo = comercio;
		RequestContext.getCurrentInstance().update("dialogInfo");
		RequestContext.getCurrentInstance().execute("PF('dialogInfo').show()");
	}

	public void aplicarFiltros() {
		listaComercios = daoComercio.listarComFiltros(comercioParaBusca);
	}

	public List<EnComercio> getListaComercios() {
		return listaComercios;
	}

	public void setListaComercios(List<EnComercio> listaComercios) {
		this.listaComercios = listaComercios;
	}

	public EnComercio getComercioParaBusca() {
		return comercioParaBusca;
	}

	public void setComercioParaBusca(EnComercio comercioParaBusca) {
		this.comercioParaBusca = comercioParaBusca;
	}

	public EnComercio getComercioInfo() {
		return comercioInfo;
	}

	public void setComercioInfo(EnComercio comercioInfo) {
		this.comercioInfo = comercioInfo;
	}

}
