package DTO;

import java.util.Date;

import org.bson.types.ObjectId;

public class NotaDTO {

	private String id;
	private Date dataCreazione;
	private Date dataModifica;
	private String contenuto;
	private String farmacoInUsoId;
	private String utente;
	private String tipoNota;
	private String priorita;
	 private boolean visibilita;
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
	public String getFarmacoInUsoId() {
		return farmacoInUsoId;
	}
	public void setFarmacoInUsoId(String farmacoInUsoId) {
		this.farmacoInUsoId = farmacoInUsoId;
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
