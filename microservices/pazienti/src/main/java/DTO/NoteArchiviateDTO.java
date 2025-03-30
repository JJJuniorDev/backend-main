package DTO;

import java.util.Date;

import Model.NoteArchiviate;

public class NoteArchiviateDTO {
    private String id;
    private Date dataCreazione;
    private Date dataModifica;
    private String contenuto;
    private String dottoreId;
    private String pazienteId;
    private String appuntamentoId;
    private String tipoNota;
    private String priorita;
    private boolean visibilita;

    // Costruttore vuoto
    public NoteArchiviateDTO() {}

    // Costruttore per convertire una `NotaArchiviate` in `NotaArchiviateDTO`
    public NoteArchiviateDTO(NoteArchiviate nota) {
        this.id = nota.getId().toHexString();
        this.dataCreazione = nota.getDataCreazione();
        this.dataModifica = nota.getDataModifica();
        this.contenuto = nota.getContenuto();
        this.dottoreId = nota.getDottoreId() != null ? nota.getDottoreId().toHexString() : null;
        this.pazienteId = nota.getPazienteId() != null ? nota.getPazienteId().toHexString() : null;
        this.appuntamentoId = nota.getAppuntamentoId() != null ? nota.getAppuntamentoId().toHexString() : null;
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

