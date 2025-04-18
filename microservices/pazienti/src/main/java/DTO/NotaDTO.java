package DTO;

import java.util.Date;

import org.bson.types.ObjectId;

import Model.Nota;

public class NotaDTO {

	private String id;
	private Date dataCreazione;
	private Date dataModifica;
	private String contenuto;
	private String dottoreId;
	private String pazienteId;
	private String appuntamentoId;
	private String utente;
	private String tipoNota;
	private String priorita;
	 private boolean visibilita;
	 
	 public NotaDTO() {
		 
	 }
	 
	 public NotaDTO(Nota nota) {
	        this.id = nota.getId() != null ? nota.getId().toString() : null;
	        this.dataCreazione = nota.getDataCreazione();
	        this.dataModifica = nota.getDataModifica();
	        this.contenuto = nota.getContenuto();
	        this.dottoreId = nota.getDottoreId() != null ? nota.getDottoreId().toString() : null;
	        this.pazienteId = nota.getPazienteId() != null ? nota.getPazienteId().toString() : null;
	        this.appuntamentoId = nota.getAppuntamentoId() != null ? nota.getAppuntamentoId().toString() : null;
	        this.tipoNota = nota.getTipoNota();
	        this.priorita = nota.getPriorita();
	        this.visibilita = nota.isVisibilita();
	    }
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Date getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getDottoreId() {
		return dottoreId;
	}
	public void setDottoreId(String dottoreId) {
		this.dottoreId = dottoreId;
	}
	public String getPazienteId() {
		return pazienteId;
	}
	public void setPazienteId(String pazienteId) {
		this.pazienteId = pazienteId;
	}
	public String getAppuntamentoId() {
		return appuntamentoId;
	}
	public void setAppuntamentoId(String appuntamentoId) {
		this.appuntamentoId = appuntamentoId;
	}
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	public String getTipoNota() {
		return tipoNota;
	}
	public void setTipoNota(String tipoNota) {
		this.tipoNota = tipoNota;
	}
	public String getPriorita() {
		return priorita;
	}
	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}
	public boolean isVisibilita() {
		return visibilita;
	}
	public void setVisibilita(boolean visibilita) {
		this.visibilita = visibilita;
	} 
	 
	 
}
