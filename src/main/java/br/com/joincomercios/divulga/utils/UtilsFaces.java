package br.com.joincomercios.divulga.utils;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

public class UtilsFaces {

	public static Object getObjectParamMap(String obj) {
		return UtilsFaces.obterExternalContext().getRequestParameterMap().get(obj);
	}

	public static void keepMessage() {
		obterExternalContext().getFlash().setKeepMessages(true);
	}

	public static HttpServletRequest obterRequest() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
		return req;
	}

	public static void redirecionar(String nomePagina) {
		try {
			obterExternalContext().redirect(obterRequest().getContextPath() + "/" + nomePagina);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Object> obterSessaoMap() {
		return FacesContext.getCurrentInstance() != null
				? FacesContext.getCurrentInstance().getExternalContext().getSessionMap() : null;
	}

	public static Object getSessionMapValue(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
	}

	public static RequestContext getCurrentInstance() {
		return RequestContext.getCurrentInstance();
	}

	public static void setSessionMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
	}

	public static void removeSessionMapValue(String key) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
	}

	public static ExternalContext obterExternalContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context != null ? context.getExternalContext() : null;
	}

	public static void adicionarMsg(String msgLonga, String msgCurta, FacesMessage.Severity severity) {
		FacesMessage msg = new FacesMessage(msgLonga, msgCurta);
		msg.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		keepMessage();
	}

	public static void adicionarMsgInfo(String msg) {
		FacesMessage facesMsg = new FacesMessage(null, msg);
		facesMsg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		keepMessage();
	}

	public static void adicionarMsgErro(String msg) {
		FacesMessage facesMsg = new FacesMessage(null, msg);
		facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		keepMessage();
	}

	public static void adicionarMsgErroFatal(String msg) {
		FacesMessage facesMsg = new FacesMessage(null, msg);
		facesMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		keepMessage();
	}

	public static void adicionarMsgAviso(String msg) {
		FacesMessage facesMsg = new FacesMessage(null, msg);
		facesMsg.setSeverity(FacesMessage.SEVERITY_WARN);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		keepMessage();
	}

	public static void showErrorDialog(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", msg);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
		keepMessage();
	}

	public static void showWarnDialog(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", msg);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
		keepMessage();
	}

	public static void showFatalErrorDialog(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro fatal", msg);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
		keepMessage();
	}

	public static void showInfoDialog(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
		keepMessage();
	}

}
