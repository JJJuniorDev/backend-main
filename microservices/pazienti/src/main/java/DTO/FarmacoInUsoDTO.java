package DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class FarmacoInUsoDTO {

	private String id;
	private String pazienteId;
	private String nomeFarmaco;
	private String dosaggio;
	private String frequenza;
	private Boolean attivo;
	private Date dataInizioTrattamento;
	private Date dataFineTrattamento;
	  private List<String> note;
	  private String farmacoId;
	  private String durataAttuale;
	  private String efficacia;
	  private String remissione;
	  
	public String getPazienteId() {
		return pazienteId;
	}
	public void setPazienteId(String pazienteId) {
		this.pazienteId = pazienteId;
	}
	public String getNomeFarmaco() {
		return nomeFarmaco;
	}
	public void setNomeFarmaco(String nomeFarmaco) {
		this.nomeFarmaco = nomeFarmaco;
	}
	public String getDosaggio() {
		return dosaggio;
	}
	public void setDosaggio(String dosaggio) {
		this.dosaggio = dosaggio;
	}
	public String getFrequenza() {
		return frequenza;
	}
	public void setFrequenza(String frequenza) {
		this.frequenza = frequenza;
	}
	public Boolean getAttivo() {
		return attivo;
	}
	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}
	public Date getDataInizioTrattamento() {
		return dataInizioTrattamento;
	}
	public void setDataInizioTrattamento(Date dataInizioTrattamento) {
		this.dataInizioTrattamento = dataInizioTrattamento;
	}
	public Date getDataFineTrattamento() {
		return dataFineTrattamento;
	}
	public void setDataFineTrattamento(Date dataFineTrattamento) {
		this.dataFineTrattamento = dataFineTrattamento;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<String> getNote() {
		return note;
	}
	public void setNote(List<String> note) {
		this.note = note;
	}
	public String getFarmacoId() {
		return farmacoId;
	}
	public void setFarmacoId(String farmacoId) {
		this.farmacoId = farmacoId;
	}
	
	   public String getDurataAttuale() {
	        return durataAttuale;
	    }

	    public void setDurataAttuale(String durataAttuale) {
	        this.durataAttuale = durataAttuale;
	    }
		public String getEfficacia() {
			return efficacia;
		}
		public void setEfficacia(String efficacia) {
			this.efficacia = efficacia;
		}
		public String getRemissione() {
			return remissione;
		}
		public void setRemissione(String remissione) {
			this.remissione = remissione;
		}
	    
	    
}
